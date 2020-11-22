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
    <title>我们的小窝-小相册</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
    <link rel="stylesheet" type="text/css" href="css/commen.css"/>
</head>
<body>

<#include "header.ftl" >

<div class="container">
    <div class="photos" id="photos">

    </div>
</div>

<#include "footer.ftl" >

<script src="${firstPage.cdnJqueryJs}"></script>
<script src="https://cdn.jsdelivr.net/npm/xgallerify@0.1.5/dist/jquery.xgallerify.min.js"></script>
<script>
    var iid = 0;
    var per = 6;
    var photosDom = $("#photos");

    photosDom.gallerify({
        margin:8,
        mode:'default',
        lastRow:'adjust',
    });

    $.post("image/list", {iid: iid, per: per}, function (data) {
        if (!data || data.length === 0) {
            alert("没有更多照片");return;
        }

        for (var i=0; i<data.length; i++) {
            photosDom.append(getcontent(data[i]));
            photosDom.gallerify.renderAsyncImages();
        }
    }, "json");

    function getcontent(photo) {
        return '<img src="'+photo.name+'">';
    }
</script>
</body>
</html>