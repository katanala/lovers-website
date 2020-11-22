// 找letter的id，0代表long.maxValue，找比它小的 number 个 id
var lid = 0;
var replylid = 0;
var contentList = $('#contentList');

getLetter();

$("img.img-thumbnail").lazyload({effect: "fadeIn"});

function zanLetter(lid) {
    var name = "#like" + lid;
    $(name).text(Number($(name).text()) + 1);
    $.post("letter/zanLetter", {"lid": lid}, function (data) {});
}

function firstLetter() {
    lid = 0;
    $('html, body').animate({scrollTop: $("#visitor").offset().top}, 100);
    getLetter();
}

function randLetter() {
    $('html, body').animate({scrollTop: $("#visitor").offset().top}, 100);
    getLetter(true);
}

// 参数：0默认，1随机
function getLetter(isRand) {
    isRand = isRand || false;
    $.post("letter/listLetterAndReply", {"lid": lid, "number": "4", "isRand": isRand}, function (data) {
        if (!data || data.length < 1) {
            return;
        }
        if (lid !== 0) { // 第一次不滚动
            $('html, body').animate({scrollTop: $("#visitor").offset().top}, 0);
        }
        lid = data[data.length - 1].letter.lid;

        contentList.empty();
        for (var i = 0; i < data.length; i++) {
            var content = getContent(data[i].letter,data[i].replies);
            contentList.append(content);
        }
    }, "json");
}

function subMessage() {
    $.post("letter/insertLetter", {
        "nickname": $("#nickname").val(), "content": $("#content").val(), "email": $("#email").val()
    }, function (result) {
        alert(result.msg);
        if (result.code === 0) {
            $("#nickname").val("");
            $("#content").val("");
            $("#email").val("");
            firstLetter();
        }
    }, "json");
}

function getContent(letter,replies) {
    var content = filterXSS(letter.content);
    var nickname = filterXSS(letter.nickname);

    return '<div class="col-md-6"><div class="card card-body card-letter">' +
        '<table style="width:100%;"><tr><td class="title">' + nickname +
        '<br /><span class="time">' + letter.pubtime + '</span></td><td align="right" ><button onclick="zanLetter(' +
        letter.lid + ')" class="btn btn-sm btn-outline-pink mr-1">👍 <span id="like' + letter.lid + '">' +
        letter.zan + '</span></button><button onclick="openmodal(' +
        letter.lid + ')" class="btn btn-sm btn-outline-pink">回复</button></td></tr></table>' +
        '<hr /><div class="style-old">' + content + '</div>' + getReply(replies) +
        '</div></div>';
}

function getReply(replies) {
    var con = '';
    if (replies && replies.length>0) {

        con += '<table class="table">';
        for (var i=0; i<replies.length; i++) {
            var nickname = filterXSS(replies[i].nickname);
            var content = filterXSS(replies[i].content);
            con += '<tr><td>' + nickname + '：' + content + '</td></tr>';
        }
        con += '</table>';
    }
    return con;
}

function openmodal(rlid) {
    replylid = rlid;
    document.getElementById("btn-open-modal-reply").click();
}

function subReply() {
    $.post("letter/insertReply", {
        lid: replylid, nickname: $("#replynickname").val(), content: $("#replycontent").val()
    }, function (result) {
        document.getElementById("btn-close-modal").click();
        alert(result.msg);
        if (result.code === 0) {
            $("#replynickname").val("");
            $("#replycontent").val("");
            firstLetter();
        }
    }, "json");
}

function loadIps() {
    loadJs(ip_js, function () {
        document.getElementById("btn-open-modal-chart").click();
        initIp();
    });
}

function loadChat() {
    loadJs(chat_js, function () {
        document.getElementById("btn-open-modal-chat").click();
        initChatModal();
    });
}

// 加载完毕 n 个文件后，
var _loadFiles = 0;
function loadJs(arr, callback) {
    if (arr[0]) { // arr[0]：是否加载过
        callback();return;
    }
    _loadFiles = 0;
    for (var i=1; i<arr.length; i++) {
        var script = document.createElement('script');
        script.src = arr[i];
        script.onload = function() {
            _loadFiles++;
            if (_loadFiles >= arr.length-1){
                arr[0] = true;
                callback();
            }
        };
        document.body.appendChild(script);
    }
}
