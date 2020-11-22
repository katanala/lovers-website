<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>小窝-后台-博客</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
    <link rel="stylesheet" type="text/css" href="/css/admin_commen.css">
</head>
<body>

<#include "header.ftl" >

<div class="jumbotron jumbotron_linear mb-4">
    <div class="container">
        <form action="blogs" method="get" onsubmit="return checkSubmitLimit()">
            <div class="row">
                <div class="col-12">
                    <div class="input-group">
                        <input type="text" name="page" class="d-none" value="0"/>
                        <input type="text" name="key" id="search-key" class="form-control" placeholder="关键词"
                               value="${key!''}">
                        <div class="input-group-append">
                            <input type="submit" class="btn btn-primary" value="查找">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-12">
            <ul class="list-group">
                <#list blogs as blog>
                    <li class="list-group-item">
                        <div class="row-between">
                            <div>
                                <#if blog.rank == -1>
                                    <a style="text-decoration:line-through;" href="/adminLove/blog?bid=${blog.bid}" target="_blank"
                                       class="card-link text-dark"> ${blog.title} </a>
                                <#elseif blog.rank == 1>
                                    <a href="/adminLove/blog?bid=${blog.bid}" target="_blank"
                                       class="card-link text-dark"> ${blog.title} </a>
                                <#elseif blog.rank == 2>
                                    <a href="/adminLove/blog?bid=${blog.bid}" target="_blank"
                                       class="card-link text-primary"> ${blog.title} </a>
                                </#if>
                            </div>
                            <div>
                                <a target="_blank" href="/blog/${blog.bid}" class="btn btn-sm btn-success">预览</a>
                                <button onclick="deleteBlog(${blog.bid})" class="btn btn-sm btn-danger">删除</button>
                            </div>
                        </div>
                        <div class="row-between">
                            <h6 class="text-muted">${blog.keyword}</h6>
                            <h6 class="text-muted">${blog.modtime}</h6>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>

    <form action="blogs" method="get">
        <ul id="pagination" class="pagination mt-3">
            <li class="page-item head"><a class="page-link" href="#">首</a></li>
            <li class="page-item disabled morehead d-none d-sm-inline-block"><a class="page-link">...</a></li>
            <li class="page-item page-2 d-none d-sm-block"><a class="page-link">1</a></li>
            <li class="page-item page-1"><a class="page-link">2</a></li>
            <li class="page-item currentpage active"><a class="page-link">1</a></li>
            <li class="page-item page_1"><a class="page-link">3</a></li>
            <li class="page-item page_2 d-none d-sm-block"><a class="page-link">4</a></li>
            <li class="page-item disabled moretail d-none d-sm-inline-block"><a class="page-link">...</a></li>
            <li class="page-item tail"><a class="page-link" href="#">尾</a></li>
        </ul>

        <input type="text" name="page" id="pages-page" class="d-none" value="1">
        <input type="text" name="key" class="d-none" value="${key!''}">
        <button type="submit" class="d-none" id="pages-btn"></button>
    </form>
</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="/js/lib/pagetool.js?v=${firstPage.timeStampHtml}"></script>
<script type="text/javascript">
    setPage(${page}, ${allpage}, "getMsg");

    function getMsg(page) {
        $("#pages-page").val(page);
        $("#pages-btn").click();
    }

    // 搜索时检查搜索的key，一个字符没有搜索意义
    function checkSubmitLimit() {
        var key = $("#search-key").val().trim();
        if (key !== "" && key.length < 2) {
            alert("关键字最少 2 个字符：" + key);
            return false;
        }
        return true;
    }

    function setRank(bid, rank) {
        $.post("/adminLove/updateInstall", {bid:bid,rank:rank}, function (data) {
            alert(data.msg);
            location.reload();
        }, "json");
    }

    function deleteBlog(bid) {
        if (!confirm("是否删除？")) {
            return;
        }
        $.post("/adminLove/deleteBlog", {bid:bid}, function (data) {
            alert(data.msg);
            location.reload();
        }, "json");
    }

</script>
</body>
</html>