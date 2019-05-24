// 选择身份
function chooseMale() {mui.confirm('', '选择身份', [ '芸霄', '晨哥', '游客'], function(e) {localStorage.male = e.index;}, 'div');}

if (!localStorage.male) {chooseMale();}

mui.init({gestureConfig:{tap:true,doubletap:true,longtap:true,swipe:true,drag:true,hold:true,release:true}});

var ui = {
	footer : document.querySelector('footer'),
	footerRight : document.querySelector('.footer-right'),
	footerLeft : document.querySelector('.footer-left'),
	btnMsgType : document.querySelector('#msg-type'),
	boxMsgText : document.querySelector('#msg-text'),
	areaMsgList : document.querySelector('#msg-list'),
	h : document.querySelector('#h'),
	content : document.querySelector('.mui-content')
};
ui.h.style.width = ui.boxMsgText.offsetWidth + 'px';

// 向前添加多条记录（通常是查看历史记录）
var bindMsgList = function(messages) {//istop 是否滚动到顶部
	// 删除当前的 获取更多按钮
	var moreBtn = document.getElementById("getMoreBtn");
	if (moreBtn) {
        moreBtn.removeAttribute("id");
        var button = moreBtn.querySelector("button");
        button.setAttribute("disabled", "disabled");
        button.innerHTML = "加载完毕";
    }

    // 添加最顶层 获取更多按钮
    var htmls = "<div id='getMoreBtn' class='msg-item'><div class='msg-item msg-item-other'><button onclick='getMoreMessage()'>点击加载更多</button></div> <div class='mui-item-clear'></div> </div>";

    var beforeHeight = ui.areaMsgList.scrollHeight;
	for (var i = 0; i < messages.length; i++) {
        htmls += chatModel(messages[i].male,messages[i].content.replace(new RegExp('\n', 'gm'),'<br/>'));
    }
	ui.areaMsgList.innerHTML = htmls + ui.areaMsgList.innerHTML;
	var afterHeight = ui.areaMsgList.scrollHeight;

    ui.areaMsgList.scrollTop = afterHeight-beforeHeight;
    console.log("clientHeight:"+ ui.areaMsgList.clientHeight + "，scrollHeight:"+ui.areaMsgList.scrollHeight);
};

window.addEventListener('resize', function() {
	ui.areaMsgList.scrollTop = ui.areaMsgList.scrollHeight + ui.areaMsgList.offsetHeight;
}, false);

function msgTextFocus() {
	ui.boxMsgText.focus();
	setTimeout(function() {
		ui.boxMsgText.focus();
	}, 150);
}

// 解决长按“发送”按钮，导致键盘关闭的问题；
ui.footerRight.addEventListener('touchstart', function(event) {
	if (ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
		msgTextFocus();
		event.preventDefault();
	}
});
// 解决长按“发送”按钮，导致键盘关闭的问题；
ui.footerRight.addEventListener('touchmove', function(event) {
	if (ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
		msgTextFocus();
		event.preventDefault();
	}
});
ui.footerRight.addEventListener('release', function(event) {
	if (ui.btnMsgType.classList.contains('mui-icon-paperplane')) {
		// showKeyboard();
		ui.boxMsgText.focus();
		setTimeout(function() {
			ui.boxMsgText.focus();
		}, 150);

		userSend(localStorage.male,message.value);

		ui.boxMsgText.value = '';
		mui.trigger(ui.boxMsgText, 'input', null);
	}
}, false);
//选择身份
ui.footerLeft.addEventListener('tap', function(event) {chooseMale();}, false);

ui.boxMsgText.addEventListener('input', function(event) {
	ui.btnMsgType.classList[ui.boxMsgText.value == '' ? 'remove' : 'add']
			('mui-icon-paperplane');
	ui.btnMsgType.setAttribute("for", ui.boxMsgText.value == '' ? ''
			: 'msg-text');
	ui.h.innerText = ui.boxMsgText.value.replace(new RegExp('\n', 'gm'), '\n-')
			|| '-';
	ui.footer.style.height = (ui.h.offsetHeight + ui.footer.offsetHeight - ui.boxMsgText.offsetHeight) + 'px';
	ui.content.style.paddingBottom = ui.footer.style.height;
});
var focus = false;
ui.boxMsgText.addEventListener('tap', function(event) {
	ui.boxMsgText.focus();
	setTimeout(function() {
		ui.boxMsgText.focus();
	}, 0);
	focus = true;
	setTimeout(function() {
		focus = false;
	}, 1000);
	event.detail.gesture.preventDefault();
}, false);
// 点击消息列表，关闭键盘
ui.areaMsgList.addEventListener('click', function(event) {
	if (!focus) { ui.boxMsgText.blur(); }
});

// 右上角获取更多聊天记录
document.getElementById('getMoreMessage').addEventListener('tap',function(event) {getMoreMessage();});

var websocket = null;
var tip = document.getElementById('tip');
var message = document.getElementById('msg-text');
var maxCid = '0';

function userSend(male,text) {
	// 0 连接尚未建立，1链接已经建立，2连接正在关闭，3连接已经关闭
	if (!websocket || websocket.readyState !== 1) { initWebSocket(); }

	var json = {};
	json.male = male;
	json.content = text;
	json.pubtime = null;
	json.cid = null;
	websocket.send(JSON.stringify(json));
}

function getMoreMessage() {
	websocket.send(maxCid);
	console.log("加载聊天历史记录，maxCid:"+maxCid);
}

function initWebSocket() {
	var protocolStr = document.location.protocol;
	var host = window.location.host;

    console.log(protocolStr+"//"+host);

    if (protocolStr.indexOf("https") !== -1) { websocket = new WebSocket("wss://"+host+"/chatSocket"); }
    else { websocket = new WebSocket("ws://"+host+"/chatSocket"); }
	
	if (!websocket) { tip.innerHTML = "连接失败！"; } 
	else { tip.innerHTML = "连接中..."; }
} initWebSocket();

// 连接发生错误
websocket.onerror = function() {
	tip.innerHTML = "链接错误！";
};

// 连接成功
websocket.onopen = function(event) {
	tip.innerHTML = "连接中...";
	getMoreMessage();
};

// 接收到消息
websocket.onmessage = function(event) {
	var data = event.data;
	if (!isNaN(data)) { // 如果是在线人数
		tip.innerHTML = "在线人数：" + event.data;
		return;
	}
	
	if (data.charAt(0) === "{") { // 有人发消息
		var message = JSON.parse(data);
        bindMsg(message.male,message.content.replace(new RegExp('\n', 'gm'), '<br/>'));
	} else if (data.charAt(0) === "[") { // 加载多条历史消息时
		var messages = JSON.parse(data);

		if (messages.length > 0) {
		    console.log("本页第一个cid："+messages[0].cid);
			maxCid = messages[0].cid;
            bindMsgList(messages);
		}
	}
}

// 连接关闭
websocket.onclose = function() {
	tip.innerHTML = "关闭连接！";
};

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	websocket.close();
};

function chatModel(male, content) {
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
	
	var maleContent = "<div class='msg-content'><div class='msg-content-inner'>"+content+"</div><div class='msg-content-arrow'></div></div>";
	var maleEnd = "<div class='mui-item-clear'></div></div>";
	return maleDiv+maleImg+maleContent+maleEnd;
}

// 聊天内容向后添加单条信息（通常是实时的单条聊天数据）
var bindMsg = function(male,content) {
    var html = chatModel(male,content);
    ui.areaMsgList.insertAdjacentHTML('beforeend', html);
    ui.areaMsgList.scrollTop = ui.areaMsgList.scrollHeight+ ui.areaMsgList.offsetHeight;
};
