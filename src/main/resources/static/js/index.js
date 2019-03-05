// 找letter的id，0代表long.maxValue，找比它小的 number 个 id
var lid = 0;
var contentList = $('#contentList');

getLetter();

function zanLetter(lid) {
	var name = "#like"+lid;
	var old = $(name).text();
	$(name).text(Number(old)+1);

	$.post("letter/zanLetter",{"lid":lid},function(data){});
}

function firstLetter() {
	lid = 0;
	$('html, body').animate({scrollTop: $("#visitor").offset().top}, 0);
	getLetter();
}

function randLetter() {
	$('html, body').animate({scrollTop: $("#visitor").offset().top}, 0);
	getLetter(true);
}

// 参数：0默认，1随机
function getLetter(isRand) {
	isRand = isRand || false;
	$.post("letter/listLetter",{"lid":lid,"number":"4","isRand":isRand},function(data) {
		if (!data || data.length<1) {return;}
		if (lid !== 0) { // 第一次不滚动
			$('html, body').animate({ scrollTop: $("#visitor").offset().top }, 0);
		}
		lid = data[data.length-1].lid;

        contentList.empty();
		for (var i=0; i<data.length; i++) {
			var content = getContent(data[i].lid, data[i].nickname, data[i].pubtime, data[i].content, data[i].zan);
            contentList.append(content);
		}
	},"json");
}

function subMessage() {
	var nickname = $("#nickname").val();
	var content = $("#content").val();
	
	if (nickname==null || nickname.trim()==="") {
		alert("昵称丢了");
		return false;
	}
	
	if (nickname.length > 16) {
		alert("昵称太长了，主人家记不住哦，1-16位");
		return false;
	}
	
	if (content==null || content.trim()==="") {
		alert("脚印丢了");
		return false;
	}
	
	if (content.length > 255) {
		alert("脚印太长了哦，1-255位");
		return false;
	}
	
	$.post("letter/insertLetter",{"nickname":nickname,"content":content},function(result){
		alert(result.msg);
		if (result.code === 0) {
			$("#nickname").val("");
			$("#content").val("");
			firstLetter();
		} 
	},"json");
}

function getContent(lid, nickname, pubtime, content, zan) {
	return '<div class="col-md-6"><div class="card card-letter">'+
	'<div class="card-body"><table style="width:100%;"><tr><td class="title" width="50%">'+nickname+'<br /><span class="time">'+pubtime+'</span></td><td width="50%" class="zan" align="right" onclick="zanLetter(\''+lid+'\')">👍 <span id="like'+lid+'">'+zan+'</span></td></tr></table>'+
	'<hr /><div class="style-old">'+content+'</div>'+
	'<div></div></div></div></div>';
}

(function(){$.post("ip/addIp",{},function(data){});})();
