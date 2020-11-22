var sa,ca,sb,cb,sc,cc,aA,oDiv,mcList = [],radius = 90;

function update() {
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

        var per = 200 / (200 + rz3);

        mcList[i].x = (rx3 * per) - 2;
        mcList[i].y = ry3 * per;
        mcList[i].scale = per;
        per = (per - 0.6) * (10 / 6);
        mcList[i].alpha = per * per * per - 0.2;
        mcList[i].zIndex = Math.ceil(100 - Math.floor(mcList[i].cz));
    }
    doPosition();
}

function positionAll() {
    var phi = 0;
    var theta = 0;
    var max = mcList.length;
    for (var i = 0; i < max; i++) {

        phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
        theta = Math.sqrt(max * Math.PI) * phi;

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

function sineCosine() {
    var a = (Math.min(Math.max(-8, -200), 200) / radius) * 11,
        b = (-Math.min(Math.max(-0, -200), 200) / radius) * 11,
        c=0,dtr=Math.PI / 180;

    sa = Math.sin(a * dtr);
    ca = Math.cos(a * dtr);
    sb = Math.sin(b * dtr);
    cb = Math.cos(b * dtr);
    sc = Math.sin(c * dtr);
    cc = Math.cos(c * dtr);
}

(function () {
    var i = 0;
    var oTag = null;
    oDiv = document.getElementById('tags');
    aA = oDiv.getElementsByTagName('a');
    for (i = 0; i < aA.length; i++) {
        oTag = {};
        aA[i].onmouseover = (function (obj) {
            return function () {
                obj.on = true;
                this.style.zIndex = 9999;
                this.style.color = '#fff';
                this.style.padding = '5px 5px';
                this.style.filter = "alpha(opacity=100)";
                this.style.opacity = 1;
            }
        })(oTag);
        aA[i].onclick = function () {
            var content = document.getElementById('content');
            content.value = this.innerHTML;
        };
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
        })(oTag);
        oTag.offsetWidth = aA[i].offsetWidth;
        oTag.offsetHeight = aA[i].offsetHeight;
        mcList.push(oTag);
    }
    positionAll();
    sineCosine();
    setInterval(update, 40);
})();
