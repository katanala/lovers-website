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
  <title>小窝-后台-图片</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
</head>
<body>

<#include "header.ftl" >

<div class="container">

    <h2 class="text-center">上传图片</h2>
    <form action="/adminLove/uploadImage" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="image">上传图片：每次限一张，20M以内，格式：jpg,png,gif,bmp,jpeg</label>
            <input required id="image" type="file" name="editormd-image-file" accept="image/*" class="form-control" />
        </div>
        <input type="hidden" name="bid" value="-1">
        <input type="submit" class="btn btn-primary" value="开始上传" />
    </form>

    <br /><br />
    <hr />

    <h2 class="text-center">修改图片</h2>
    <p>当前共：${total} 张照片</p>
    <table class="table table-backgroud table-hover table-bordered table-striped">
        <thead>
        <tr>
          <th>类型</th>
          <th>图片</th>
          <th>地址</th>
          <th>设置</th>
        </tr>
        </thead>
        <#list images as image>
        <tr>
          <#if image.bid<0>
            <td>相册</td>
          <#else>
            <td>博客：${image.bid}</td>
          </#if>

          <td> <img height="120px" src="${ossUrl}${image.name}"> </td>
          <td>${ossUrl}${image.name}</td>
          <td>
            <a href="/adminLove/deleteImage?iid=${image.iid}">删除</a>
          </td>
        </tr>
        </#list>
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

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="/js/lib/pagetool.js"></script>
<script type="text/javascript">
    setPage(Number(${curpage}), Number(${allpage}), "getMsg");
    function getMsg(p) {
        location.href="/adminLove/image?page="+p;
    }
</script>
</body>
</html>