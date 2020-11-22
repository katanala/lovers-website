<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="Keywords" content="${firstPage.keywords}"/>
    <meta name="description" content="${firstPage.description}"/>
    <title>小窝后台-友链</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
</head>
<body>

<#include "header.ftl" >

<div class="container mt-3">

    <div class="form-inline mb-3">
        <label for="href">链接：</label>
        <input type="text" maxlength="500" class="form-control" id="href">
        <label for="title">标题：</label>
        <input style="max-width: 12rem;" type="text" maxlength="100" class="form-control" id="title">
        <label for="rank">顺序：</label>
        <input style="max-width: 5rem;" type="number" class="form-control" id="rank">
        <button onclick="updateLink()" type="button" class="btn btn-primary">更新或添加</button>
    </div>

    <table class="table table-backgroud table-hover table-bordered table-striped table-pink">
        <thead>
        <tr>
            <th>排序</th>
            <th>链接</th>
            <th>修改</th>
        </tr>
        </thead>
        <tbody>
        <#list links as link>
            <tr>
                <td>
                    <button onclick="changeRank(this,-1)" href="${link.href}" class="btn btn-sm btn-primary"> - </button>
                    <span class="ml-1 mr-1">${link.rank}</span>
                    <button onclick="changeRank(this,1)" href="${link.href}" class="btn btn-sm btn-primary"> + </button>
                </td>
                <td>${link.title}<br><a target="_blank" href="${link.href}">${link.href}</a></td>
                <td>
                    <button onclick="deleteHref(this)" href="${link.href}"  class="btn btn-sm btn-warning">删除</button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script>
    var key, value;
    function showModal(obj) {
        key = obj.getAttribute("key");
        value = $("#value_" + key);
        $("#btn-show-modal").click();

        $("#label-key").text(key);
        $("#input-value").val(value.text());
    }

    function deleteHref(obj) {
        var href = obj.getAttribute("href");

        if (confirm("是否删除？")) {
            $.post("/adminLove/deleteLink", {href:href}, function (data) {
                alert(data.msg);
                location.reload();
            }, "json")
        }
    }

    function changeRank(obj, rankadd) {
        var href = obj.getAttribute("href");
        $.post("/adminLove/updateLink", {href:href, rank: rankadd, title: null}, function () {
            location.reload();
        }, "json")
    }

    function updateLink() {
        var href = $("#href").val().trim();
        var title = $("#title").val().trim();
        var rank = $("#rank").val();

        if (href === "" || title === "") { return; }

        $.post("/adminLove/updateLink", {href:href,title:title,rank:rank}, function (data) {
            alert(data.msg);
            location.reload();
        }, "json")
    }
</script>
</body>
</html>