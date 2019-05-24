// 找letter的id，0代表long.maxValue，找比它小的 number 个 id
var lid = 0;
var contentList = $('#contentList');

function zanLetter(lid) {
	var name = "#like"+lid;
	$(name).text(Number($(name).text())+1);
	$.post("letter/zanLetter",{"lid":lid},function(data){});
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
} getLetter();

function subMessage() {
	$.post("letter/insertLetter",{"nickname":$("#nickname").val(),"content":$("#content").val()},function(result){
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

// 访问首页时，记录当前用户的ip
(function(){$.post("ip/addIp",{},function(data){});})();

/* 以下为滚动标签操作 */
var radius = 90;
var d = 200;
var dtr = Math.PI / 180;
var mcList = [];
var distr = true;
var tspeed = 11;
var size = 200;
var mouseX = 0;
var mouseY = 8;
var howElliptical = 1;
var aA = null;
var oDiv = null;

window.onload=function () {
    var i=0;
    var oTag=null;
    oDiv=document.getElementById('tags');
    aA=oDiv.getElementsByTagName('a');
    for(i=0;i<aA.length;i++) {
        oTag={};
        aA[i].onmouseover = (function (obj) {
            return function () {
                obj.on = true;
                this.style.zIndex = 9999;
                this.style.color = '#fff';
                this.style.padding = '5px 5px';
                this.style.filter = "alpha(opacity=100)";
                this.style.opacity = 1;
            }
        })(oTag)
        aA[i].onclick = function (obj) {
            var content = document.getElementById('content');
            content.value = this.innerHTML;
        }
        aA[i].onmouseout = (function (obj) {
            return function () {
                obj.on = false;
                this.style.zIndex = obj.zIndex;
                this.style.color = '#fff';
                this.style.padding = '5px';
                this.style.filter = "alpha(opacity=" + 100 * obj.alpha + ")";
                this.style.opacity = obj.alpha;
                this.style.zIndex = obj.zIndex;
            }
        })(oTag)
        oTag.offsetWidth = aA[i].offsetWidth;
        oTag.offsetHeight = aA[i].offsetHeight;
        mcList.push(oTag);
    }
    sineCosine( 0,0,0 );
    positionAll();

    (function() {update();setTimeout(arguments.callee, 45);})();
};
function update()
{
    var a, b, c = 0;
    a = (Math.min(Math.max(-mouseY, -size), size) / radius) * tspeed;
    b = (-Math.min(Math.max(-mouseX, -size), size) / radius) * tspeed;
    if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
        return;
    }
    sineCosine(a, b, c);
    for (var i = 0; i < mcList.length; i++) {
        if (mcList[i].on) {
            continue;
        }
        var rx1 = mcList[i].cx;
        var ry1 = mcList[i].cy * ca + mcList[i].cz * (-sa);
        var rz1 = mcList[i].cy * sa + mcList[i].cz * ca;

        var rx2 = rx1 * cb + rz1 * sb;
        var ry2 = ry1;
        var rz2 = rx1 * (-sb) + rz1 * cb;

        var rx3 = rx2 * cc + ry2 * (-sc);
        var ry3 = rx2 * sc + ry2 * cc;
        var rz3 = rz2;

        mcList[i].cx = rx3;
        mcList[i].cy = ry3;
        mcList[i].cz = rz3;

        per = d / (d + rz3);

        mcList[i].x = (howElliptical * rx3 * per) - (howElliptical * 2);
        mcList[i].y = ry3 * per;
        mcList[i].scale = per;
        var alpha = per;
        alpha = (alpha - 0.6) * (10 / 6);
        mcList[i].alpha = alpha * alpha * alpha - 0.2;
        mcList[i].zIndex = Math.ceil(100 - Math.floor(mcList[i].cz));
    }
    doPosition();
}
function positionAll()
{
    var phi = 0;
    var theta = 0;
    var max = mcList.length;
    for (var i = 0; i < max; i++) {
        if (distr) {
            phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
            theta = Math.sqrt(max * Math.PI) * phi;
        } else {
            phi = Math.random() * (Math.PI);
            theta = Math.random() * (2 * Math.PI);
        }
        // 坐标变换
        mcList[i].cx = radius * Math.cos(theta) * Math.sin(phi);
        mcList[i].cy = radius * Math.sin(theta) * Math.sin(phi);
        mcList[i].cz = radius * Math.cos(phi);

        aA[i].style.left = mcList[i].cx + oDiv.offsetWidth / 2 - mcList[i].offsetWidth / 2 + 'px';
        aA[i].style.top = mcList[i].cy + oDiv.offsetHeight / 2 - mcList[i].offsetHeight / 2 + 'px';
    }
}
function doPosition() {
    var l = oDiv.offsetWidth / 2;
    var t = oDiv.offsetHeight / 2;
    for (var i = 0; i < mcList.length; i++) {
        if (mcList[i].on) {
            continue;
        }
        var aAs = aA[i].style;
        if (mcList[i].alpha > 0.1) {
            if (aAs.display != '') aAs.display = '';
        } else {
            if (aAs.display != 'none') aAs.display = 'none';
            continue;
        }
        aAs.left = mcList[i].cx + l - mcList[i].offsetWidth / 2 + 'px';
        aAs.top = mcList[i].cy + t - mcList[i].offsetHeight / 2 + 'px';
        aAs.filter = "alpha(opacity=" + 100 * mcList[i].alpha + ")";
        aAs.zIndex = mcList[i].zIndex;
        aAs.opacity = mcList[i].alpha;
    }
}
function sineCosine( a, b, c) {
    sa = Math.sin(a * dtr);
    ca = Math.cos(a * dtr);
    sb = Math.sin(b * dtr);
    cb = Math.cos(b * dtr);
    sc = Math.sin(c * dtr);
    cc = Math.cos(c * dtr);
}

