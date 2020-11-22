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
    <title>我们的小窝-时间线</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
    <link rel="stylesheet" type="text/css" href="css/commen.css?v=${firstPage.timeStampHtml}"/>
    <link rel="stylesheet" type="text/css" href="css/timeline.css?v=${firstPage.timeStampHtml}"/>
</head>
<body>

<#include "header.ftl" >

<div class="container">
<div class="row">
<div class="col-md-12">
<div class="main-timeline" id="timelines">

</div>
</div>
</div>
</div>

<#include "footer.ftl" >

<script src="${firstPage.cdnJqueryJs}"></script>
<script src="${firstPage.cdnBootstrapJs}"></script>
<script>
var timelineDom = $("#timelines");

function initTimeline() {
    $.post("timeline/list", {}, function (data) {
        if (!data || data.length < 1) {
            return;
        }

        for (var i = 0; i < data.length; i++) {
            timelineDom.append(getContent(data[i]));
        }
    }, "json");
} initTimeline();

function getContent(timeline) {
    return '<div class="timeline"><div class="timeline-icon">'+
    '<span class="year">'+timeline.year+'</span></div>'+
    '<div class="timeline-content"><h3 class="title">'+timeline.title+'</h3>'+
    '<p class="description">'+timeline.content1+'<br/>'+timeline.content2+
    '<br/>'+timeline.content3+'<br/></p></div></div>';
}

</script>
</body>
</html>