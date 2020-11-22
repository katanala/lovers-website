// 找ip的id，0代表long.maxValue，找比它小的 number 个 id
var iplist;

function initIp() {
    iplist = $('#ip-list');getMsg(1);
}

function getIp(p) {
    $.post("ip/listIp", {"page": p, "per": perpage}, function (data) {
        if (!data || data.length < 1) {return;}
        iplist.empty();
        for (var i = 0; i < data.length; i++) {
            var content = getCards(data[i]);
            iplist.append(content);
        }
    }, "json");
}

function getMsg(p) {
    getIp(p);
    setPage(p, allpage, "getMsg");
}

function getCards(ip) {
    return '<tr><td>'+ip.region+'-'+ip.city+'<br/>'+ip.isp+'</td><td>'+ip.ip+'<br/>'+ip.curtime+'</td></tr>';
}