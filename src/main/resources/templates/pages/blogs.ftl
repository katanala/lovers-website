<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="博客,blog" />
  <meta name="description" content="${firstPage.description},博客,blog" />
  <title>博客</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="css/commen.css?v=${firstPage.timeStampHtml}" />
</head>
<body>

<#include "header.ftl" >

<div class="jumbotron jumbotron_linear mb-0">
  <div class="container">
    <form action="blogs" method="get" onsubmit="return checkSubmitLimit()">
      <div class="row">
        <div class="col-md-12 col-lg-8">
          <div class="input-group">
            <input type="text" name="page" class="d-none" value="0" />
            <input type="text" name="key" id="search-key" class="form-control" placeholder="关键词" value="${key!''}">
            <div class="input-group-append">
              <input type="submit" class="btn btn-primary" value="查找">
            </div>
          </div>
        </div>
        <div class="col-md-12 col-lg-4"></div>
      </div>
    </form>
  </div>
</div>

<div class="main bg-pink">
  <div class="container">
    <div class="row">
      <div class="col-md-12 col-lg-8 mt-4">
        <ul class="list-group">
        <#list blogs as blog>
          <li class="list-group-item">
            <a href="/blog/${blog.bid}" target="_blank" class="card-link text-dark"> ${blog.title} </a>
            <small class="float-right text-muted"> ${blog.modtime} </small>
            <br />
            <div style="word-wrap:break-word" class="text-muted">${blog.keyword}</div>
          </li>
        </#list>
        </ul>
      </div>

      <div class="col-md-12 col-lg-4 mt-4">
        <ul class="list-group">
          <li class="list-group-item bg-secondary text-light">猜你喜欢</li>
          <#list rands as blog>
          <li class="list-group-item">
            <a target="_blank" href="/blog/${blog.bid}" class="card-link text-dark"> ${blog.title} </a>
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
</div>

<#include "footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="js/lib/pagetool.js?v=${firstPage.timeStampHtml}"></script>
<script type="text/javascript">
    setPage(${page}, ${allpage}, "getMsg");
    function getMsg(page) {
      $("#pages-page").val(page);
      $("#pages-btn").click();
    }

    // 搜索时检查搜索的key，一个字符没有搜索意义
    function checkSubmitLimit() {
      var key = $("#search-key").val().trim();
      if (key!=="" && key.length < 2) {
        alert("关键字最少 2 个字符："+key);
        return false;
      }
      return true;
    }
</script>
</body>
</html>