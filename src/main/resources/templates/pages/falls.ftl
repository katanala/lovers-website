<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="${firstPage.keywords}" />
  <meta name="description" content="${firstPage.description}" />
  <title>我们的小窝-小相册</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="css/commen.css" />
  <link rel="stylesheet" type="text/css" href="css/falls.css" />
  <style>
    .content .left {left: 0;cursor: url("image/falls/cur-left1.png"), auto;}
    .content .right {right: 0;cursor: url("image/falls/cur-right1.png"), auto;}
  </style>
</head>
<body>

<#include "header.ftl" >

<!--瀑布流  start-->
<div id="wrapper">
    <div id="container">
    </div>
</div>

<div class="clear"></div>
<div class="load_more">
    <button type="button" onclick="load()" class="btn btn-success mb-4">加载更多</button>
</div>

<!--大图弹出层 start-->
<div class="bigcontainer">
    <div class="close_div">
        <img src="image/falls/closelabel.gif" class="close_pop" title="关闭" alt="关闭" style="cursor:pointer">　
    </div>
    <div class="content" id="content">
        <span style="display:none"><img src="image/falls/loading.gif" /></span>
        <div class="left"></div>
        <div class="right"></div>
    </div>
    <div class="bottom">共 <span id="img_count">x</span> 张 / 第 <span id="xz">x</span> 张</div>
</div>

<#include "footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="js/lib/blocksit.min.js"></script>
<script>
  var prefix = "${prefix}";
  console.log("image server url : "+prefix);
</script>
<script type="text/javascript" src="js/falls.js" ></script>

</body>
</html>