<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="${firstPage.keywords}" />
  <meta name="description" content="${firstPage.description}" />
  <title>博客-编辑</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="/editormd/css/editormd.min.css" />
  <script async src="//lookimg.com/sdk/pup.js" data-url="https://lookimg.com/upload" data-auto-insert="markdown-embed-medium"></script>
</head>
<body>

<#include "header.ftl" >

<div class="container">

  <div class="input-group mt-3 mb-2">
    <div class="input-group-prepend">
      <span class="input-group-text">标题</span>
    </div>
    <input type="text" id="title" class="form-control">
  </div>

  <div class="input-group mb-2">
    <div class="input-group-prepend">
      <span class="input-group-text">关键字</span>
    </div>
    <input type="text" id="keyword" class="form-control">
  </div>

  <div class="input-group mb-2">
    <div class="input-group-prepend">
      <span class="input-group-text">引用链接</span>
    </div>
    <input type="text" id="url" class="form-control">
  </div>

  <div class="input-group mb-2">
    <div class="input-group-prepend">
      <span class="input-group-text">状态</span>
    </div>
    <select id="rank" class="form-control">
      <option value="-1">草稿</option>
      <option value="1">普通</option>
      <option value="2">推荐</option>
    </select>
  </div>

  <div id="editormd" class="editormd">
    <textarea style="display: none;"></textarea>
  </div>

  <input class="btn btn-primary" type="button" onclick="update()" value="提交">

</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="/editormd/editormd.min.js"></script>
<script>
  var bid = ${bid};
  var editor;

  $.post("/blog/get",{bid:bid},function (data) {
    if (!data) {alert("没有返回数据");return;}
    if (data.code !==0 ) {alert(data.msg);return;}

    $("#title").val(data.data.title);
    $("#keyword").val(data.data.keyword);
    $("#rank").val(data.data.rank);
    $("#url").val(data.data.url);
    initEditor(data.data.content);
  },"json");

  function initEditor(markdown) {
    editor = editormd("editormd", {
      height:"600",
      markdown: markdown,
      path : "../editormd/lib/",
      placeholder : "请输入内容...",
      imageUpload : true,
      imageUploadURL : "/adminLove/uploadImage?bid="+bid,
      toolbarIcons : function() { return ["undo", "redo", "clear",
        "|", "quote", "hr", "list-ul", "list-ol", "table",
        "|", "h1", "h2", "h3", "h4", "bold", "del", "italic",
        "|", "link", "image", "code-block",
        "|", "help",  "||", "watch", "preview","fullscreen" ] }
    });
  }

  function update() {
    var title = $("#title").val();
    var keyword = $("#keyword").val();
    var rank = $("#rank").val();
    var url = $("#url").val();
    var content = editor.getValue();

    $.post("/blog/update",{bid:bid,title:title,rank:rank,keyword:keyword,content:content,url:url},function (data) {
      alert(data.msg);
    },"json");
  }

</script>
</body>
</html>