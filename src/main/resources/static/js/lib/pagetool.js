var pagi = $("#pagination");

function setPage(pageNum, totalPage, pageFun) {
	pagi.find(".head a").attr("href", "javascript:" + pageFun + "(1);");
	pagi.find(".currentpage a").text(pageNum);
	pagi.find(".tail a").attr("href", "javascript:" + pageFun + "(" + totalPage + ")");

	pagi.find(".head").removeClass("disabled");
	pagi.find(".tail").removeClass("disabled");

	if (pageNum - 3 > 0) {
		pagi.find(".morehead").show();
	} else {
		pagi.find(".morehead").hide();
	}
	if (pageNum - 2 > 0) {
		pagi.find(".page-2").show();
		pagi.find(".page-2 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 2) + ")");
		pagi.find(".page-2 a").text(pageNum - 2);
		pagi.find(".page-2").addClass("d-none").addClass("d-sm-block");
	} else {
		pagi.find(".page-2").hide();
		pagi.find(".page-2").removeClass("d-none").removeClass("d-sm-block");
	}
	if (pageNum - 1 > 0) {
		pagi.find(".page-1").show();
		pagi.find(".page-1 a").attr("href", "javascript:" + pageFun + "(" + (pageNum - 1) + ")");
		pagi.find(".page-1 a").text(pageNum - 1);
	} else {
		pagi.find(".page-1").hide();
	}

	if ((pageNum + 1) <= totalPage) {
		pagi.find(".page_1").show();
		pagi.find(".page_1 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 1) + ")");
		pagi.find(".page_1 a").text(pageNum + 1);
	} else {
		pagi.find(".page_1").hide();
	}

	if (pageNum + 2 <= totalPage) {
		pagi.find(".page_2").show();
		pagi.find(".page_2 a").attr("href", "javascript:" + pageFun + "(" + (pageNum + 2) + ")");
		pagi.find(".page_2 a").text(pageNum + 2);
		pagi.find(".page_2").addClass("d-none").addClass("d-sm-block");
	} else {
		pagi.find(".page_2").hide();
		pagi.find(".page_2").removeClass("d-none").removeClass("d-sm-block");
	}

	if (pageNum + 3 <= totalPage) {
		pagi.find(".moretail").show();
	} else {
		pagi.find(".moretail").hide();
	}
	
	if (pageNum <= 1) {
		pagi.find(".head").addClass("disabled");
	}
	if (pageNum >= totalPage) {
		pagi.find(".tail").addClass("disabled");
	}
	return pageNum;
}