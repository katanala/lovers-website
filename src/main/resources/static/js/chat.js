
$("input[name='chat-user']").on("click", function () {
    localStorage.male = $(this).val();
    closeChooseUser();
    sendBtn();
});

var isPreseChoose = false;
function chooseUser() {
    if (!isPreseChoose) {
        msgtable.css({"display": "table"});
        userInput.css({"display": "none"});
        isPreseChoose = true;
    } else {
        closeChooseUser();
    }
}

function closeChooseUser() {
    msgtable.css({"display": "none"});
    userInput.css({"display": "block"});
    isPreseChoose = false;
}

var websocket = null,
    online = "在线人数：0",
    maxCid = 0,
    interval = null,
    tip = $('#tip'),
    winDom = $(window),
    msgList = $("#msg-list"),
    chatFootDom = $("#chatModalFoot"),
    userInput = $("#msg-text"),
    hhh = $('#hhh'),
    msgtable = $("#msg-table");

function initChatModal() {
    if (websocket == null) {
        // 浏览器高度
        var totalHeight = winDom.height();
        // head + foot
        var chatFootHeight = chatFootDom.height() * 2;
        // margin_top + margin_bottom
        var bottomHeight = 28 * 2;
        var msglistHeight = totalHeight - chatFootHeight - bottomHeight;
        if (msglistHeight > 500) {
            msglistHeight = 500;
        }
        msgList.height(msglistHeight);

        hhh.width(userInput.width());
        initWebSocket();
    }
}

// 向前添加多条记录（通常是查看历史记录）
function bindMsgList(messages) {
    // 删除当前的 获取更多按钮
    var moreBtn = document.getElementById("getMoreBtn");
    if (moreBtn) {
        moreBtn.removeAttribute("id");
        var button = moreBtn.querySelector("button");
        button.setAttribute("disabled", "disabled");
        button.innerHTML = "加载完毕，请下拉";
    }

    tip.text("加载历史数据2...");
    interval = setInterval(function () {
        if (msgList[0].scrollHeight > 0) {
            if (interval) {clearInterval(interval);}
            addData(messages);
        }
    }, 50);
}

function addData(messages) {
    tip.text("加载历史数据3...");
    var beforeHeight = msgList[0].scrollHeight;
    // 添加最顶层 获取更多按钮
    var htmls = "<div id='getMoreBtn' class='msg-item'><div class='msg-item msg-item-other'><button class='btn btn-light'" +
        " onclick='getMoreMessage()'>点击加载更多</button></div> <div class='mui-item-clear'></div> </div>";
    for (var i = 0; i < messages.length; i++) {
        htmls += chatModel(messages[i].male, messages[i].content.replace(new RegExp('\n', 'gm'), '<br/>'));
    }
    msgList.prepend( htmls );

    var afterHeight = msgList[0].scrollHeight;
    msgList[0].scrollTop = afterHeight - beforeHeight;
    tip.text(online);
}

// 聊天内容向后添加单条信息（通常是实时的单条聊天数据）
var bindMsg = function (male, content) {
    var html = chatModel(male, content);
    msgList.append( html );
    msgList[0].scrollTop = msgList[0].scrollHeight;
};

function sendBtn() {
    // 如果处于选中用户中
    if (isPreseChoose) {
        localStorage.male = 2;
        closeChooseUser();
    } else if (localStorage.male == null) {
        console.log("需要选择角色。");
        chooseUser();
        return;
    }
    userSend(localStorage.male, userInput.val());
    userInput.val('');
}

function userSend(male, text) {
    // 0 连接尚未建立，1链接已经建立，2连接正在关闭，3连接已经关闭
    if (!websocket || websocket.readyState !== 1) {
        initWebSocket();
    }
    if (!text || text==="") {return;}
    var json = {};
    json.male = male;
    json.content = text;
    json.pubtime = null;
    json.cid = null;
    websocket.send(JSON.stringify(json));
}

function getMoreMessage() {
    websocket.send(maxCid);
}

function initWebSocket() {
    var protocolStr = document.location.protocol;
    var host = window.location.host;

    if (protocolStr.indexOf("https") !== -1) {
        websocket = new WebSocket("wss://" + host + "/chatSocket");
    } else {
        websocket = new WebSocket("ws://" + host + "/chatSocket");
    }

    if (!websocket) {
        tip.text("连接失败！");
        setTimeout(initWebSocket, 300);
        return;
    }

    websocket.onerror = onerror;
    websocket.onopen = onopen;
    websocket.onmessage = onmessage;
    websocket.onclose = onclose;
}

// 连接发生错误
var onerror = function () {
    tip.text("连接失败！");
    setTimeout(initWebSocket, 300);
};

// 连接成功
var onopen = function (event) {
    tip.text("获取数据中...");
    getMoreMessage();
};

// 接收到消息
var onmessage = function (event) {
    var data = event.data;
    if (!isNaN(data)) { // 如果是在线人数
        online = "在线人数：" + event.data;
        tip.text(online);
        return;
    }

    if (data.charAt(0) === "{") { // 有人发消息
        var message = JSON.parse(data);
        bindMsg(message.male, message.content.replace(new RegExp('\n', 'gm'), '<br/>'));
    } else if (data.charAt(0) === "[") { // 加载多条历史消息时
        var messages = JSON.parse(data);

        if (messages.length > 0) {
            console.log("本页第一个cid：" + messages[0].cid);
            maxCid = messages[0].cid;
            tip.text("加载历史数据1...");
            bindMsgList(messages);
        }
    }
};

// 连接关闭
var onclose = function () {
    tip.text("连接关闭！");
};

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    tip.text("连接关闭！");
    if (websocket) {websocket.close();}
};

function chatModel(male, content) {
    content = filterXSS(content);

    var maleDiv = "";
    var maleImg = "";
    if (male === 0) {
        maleDiv = "<div class='msg-item'>";
        maleImg = "<img class='msg-user-img' src='../image/chat/avatar_xiao.jpg'/>";
    } else if (male === 1) {
        maleDiv = "<div class='msg-item msg-item-self'>";
        maleImg = "<img class='msg-user' src='../image/chat/avatar_chen.jpg'/>";
    } else if (male === 2) {
        maleDiv = "<div class='msg-item msg-item-other'>"
    } else {
        maleDiv = "<div class='msg-item msg-item-other msg-item-robot'>"
    }

    var maleContent = "<div class='msg-content'><div class='msg-content-inner'>" + content + "</div><div class='msg-content-arrow'></div></div>";
    var maleEnd = "<div class='mui-item-clear'></div></div>";
    return maleDiv + maleImg + maleContent + maleEnd;
}
