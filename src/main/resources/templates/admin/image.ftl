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
    <link rel="stylesheet" type="text/css" href="//cdn.bootcss.com/bootstrap/4.1.1/css/bootstrap.min.css" />
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
    <p>当前共：{$totalImage} 张照片</p>
    <#--<table class="table table-backgroud table-hover table-bordered table-striped">
        <thead>
        <tr>
            <th>编号</th>
            <th>图片</th>
            <th>状态</th>
            <th>地址</th>
            <th>设置</th>
        </tr>
        </thead>
        {volist name="images" id="image"}
        <tr>
            <td>{$image.iid}</td>
            <td> <img height="120px" src="{$qiniupath}{$image.name}"> </td>
            {eq name="$image.isshow" value="1"}
            <td> <span class="text-success">已显示</span> </td>
            {else/}
            <td> <span class="text-danger">未显示</span> </td>
            {/eq}
            <td>{$qiniupath}{$image.name}</td>
            {eq name="$image.isshow" value="1"}
            <td>
                <a class="btn btn-secondary" href="/index.php/admin/Image/setShow?isshow=0&iid={$image.iid}">设为隐藏</a>
                <a class="btn btn-danger" href="/index.php/admin/Image/delete?iid={$image.iid}">删除</a>
            </td>
            {else/}

            <td>
                <a class="btn btn-secondary" href="/index.php/admin/Image/setShow?isshow=1&iid={$image.iid}">设为显示</a>
                <a class="btn btn-danger" href="/index.php/admin/Image/delete?iid={$image.iid}">删除</a>
            </td>
            {/eq}
            </tr>
        {/volist}
    </table>-->

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

<script type="text/javascript" src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.bootcss.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="__JS__/lib/pagetool.js"></script>
<script type="text/javascript">
    setPage(Number('{$curpage}'), Number('{$allpage}'), "getMsg");
    function getMsg(p) {
        location.href="/index.php/admin/Index/image?page="+p;
    }
</script>
</body>
</html>