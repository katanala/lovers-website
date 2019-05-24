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
  <title>我们的小窝-访问</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" href="css/commen.css?v=${firstPage.timeStampHtml}" />
  <link rel="stylesheet" href="css/ip.css?v=${firstPage.timeStampHtml}" />
</head>
<body>

<#include "header.ftl" >

<div class="jumbotron">
  <div class="container">
    <h3>小窝 : ${ipCount}次访问</h3>
  </div>
  <span id="visitor"></span>
</div>

<div class="main bg-pink">
  <div class="container">
    <table class="table table-backgroud table-hover table-bordered table-striped table-pink">
      <thead>
      <tr>
        <th scope="row">访问时间</th>
        <th scope="row" class="d-none d-sm-table-cell">省份</th>
        <th scope="row">城市</th>
        <th scope="row">网络</th>
        <th scope="row" class="d-none d-sm-table-cell">ip地址</th>
      </tr>
      </thead>
      <tbody id="ip-list">
      <tr></tr>
      </tbody>
    </table>

    <ul id="pagination" class="pagination">
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
  </div>
</div>

<#include "footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="js/lib/pagetool.js"></script>
<script type="text/javascript">
  var allpage = ${allpage};
</script>
<script type="text/javascript" src="js/ip.js?v=${firstPage.timeStampHtml}" charset="UTF-8"></script>
</body>
</html>