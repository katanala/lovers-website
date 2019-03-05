// 找ip的id，0代表long.maxValue，找比它小的 number 个 id
var perpage = 10;
// var allpage = 1; 在jsp里用el获取
var iplist = $('#ip-list');

setPage(1, allpage, "getMsg");
getIp(1);

function getIp(p) {
	$.post("ip/listIp",{"page":p,"per":perpage},function(data){
		if (!data || data.length<1) {return;}
		
		iplist.empty();
		for (var i=0; i<data.length; i++) {
			var content = getContent(data[i].curtime, data[i].region, data[i].city, data[i].isp, data[i].ip);
			iplist.append(content);
		}
	},"json");
} 

function getMsg(p) {
	getIp(p);
	//$('html, body').animate({scrollTop: $("#visitor").offset().top}, 0);
	setPage(p, allpage, "getMsg");
}

function getContent(curtime, region, city, isp, ip) {
	return '<tr><td>'+curtime+'</td><td class="d-none d-sm-table-cell">'+region+'</td><td>'+city+'</td><td>'+isp+'</td><td class="d-none d-sm-table-cell">'+ip+'</td></tr>';
}
