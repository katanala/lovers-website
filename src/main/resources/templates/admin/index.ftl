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
  <title>小窝后台-首页</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
</head>
<body>

<#include "header.ftl" >

<div class="container mt-3">
  <button class="btn btn-primary" onclick="crawler('csdn')">爬取博客</button>
  <button class="btn btn-primary" onclick="crawler('dytt')">爬取电影</button>
  <a class="btn btn-warning" href="adminLove/logout">安全退出</a>

  <hr />

  <h4>开发者文档</h4>
  <a target="_blank" href="http://www.runoob.com/bootstrap4/bootstrap4-tutorial.html">bootstrap4 文档</a>
  <br />
  <a target="_blank" href="http://www.runoob.com/jquery/jquery-tutorial.html">jquery3.2.1 文档</a>
  <br />
  <a target="_blank" href="https://help.aliyun.com/document_detail/32008.html">阿里云 oss 文档</a>

</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script>
  function crawler(kind) {
      alert("已开始爬取，勿重复点击，请查看日志");
    $.get("/adminLove/crawler",{kind:kind},function (data) {alert(data);},"json");
  }
</script>
</body>
</html>