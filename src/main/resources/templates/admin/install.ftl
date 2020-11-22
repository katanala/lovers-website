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
    <title>小窝后台-配置</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
</head>
<body>

<#include "header.ftl" >

<div class="container mt-3">
    <table class="table table-backgroud table-hover table-bordered table-striped table-pink">
        <thead>
        <tr>
            <th scope="row">名称</th>
            <th scope="row">值</th>
            <th>修改</th>
        </tr>
        </thead>
        <tbody>
        <#list installs as install>
            <tr>
                <td>${install.key}<br>${install.intro}</td>
                <td id="value_${install.key}">${install.value}</td>
                <td><button onclick="showModal(this)" key="${install.key}" class="btn btn-sm btn-primary">修改</button></td>
            </tr>
        </#list>
        </tbody>
    </table>

    <button id="btn-show-modal" style="display: none;" data-toggle="modal" data-target="#myModal"></button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">修改设置</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label id="label-key" for="input-value"></label>
                        <br>
                        <input class="form-control" id="input-value" type="text" />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <input onclick="updateInstall()" type="button" class="btn btn-primary" value="确认修改"/>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script>
    var key, value;
    function showModal(obj) {
        key = obj.getAttribute("key");
        value = $("#value_" + key);
        $("#btn-show-modal").click();

        $("#label-key").text(key);
        $("#input-value").val(value.text());
    }

    function updateInstall() {
        value = $("#input-value").val();
        $.post("/adminLove/updateInstall", {key:key,value:value,intro:""}, function (data) {
            alert(data.msg);
            location.reload();
        }, "json");
    }
</script>
</body>
</html>