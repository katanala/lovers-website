<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="${blog.keyword},${blog.title}" />
  <meta name="description" content="${blog.title},${blog.keyword}" />
  <title>${blog.title}</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="/css/commen.css?v=${firstPage.timeStampHtml}" />
  <link rel="stylesheet" type="text/css" href="/editormd/css/editormd.preview.css" />
</head>
<body>

<#include "header.ftl" >

<div class="bg-pink mt-0 mb-0 pt-3 pb-3">
<div class="container">

  <div class="row mb-3">
    <div class="col-md-12 col-lg-8 mt-3">
      <div class="card">
        <div class="card-body">
          <h3 class="card-title text-center" id="title">${blog.title}</h3>
          <p class="text-muted text-center"> <small id="modtime">${blog.modtime}</small> </p>
          <div id="editormd-view">
            <textarea style="display: none;">${blog.content}</textarea>
          </div>
        </div>
        <div class="card-footer">关键字：${blog.keyword}</div>
      </div>
    </div>
    <div class="col-md-12 col-lg-4 mt-3">
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
</div>
</div>

<#include "footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="/editormd/lib/marked.min.js"></script>
<script type="text/javascript" src="/editormd/lib/prettify.min.js"></script>
<script type="text/javascript" src="/editormd/editormd.js"></script>
<script>
  editormd.markdownToHTML("editormd-view", {
      htmlDecode : false
  });
</script>
</body>
</html>