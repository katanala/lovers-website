<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="Keywords" content="${firstPage.keywords}"/>
    <meta name="description" content="${firstPage.description}"/>
    <title>小窝-后台-图片</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
    <style>
        img {width: 100%;}
    </style>
</head>
<body>

<#include "header.ftl" >

<div class="container">
<h4 class="text-center mt-4 mb-4">3D 相册</h4>
<div class="row">
    <#list threes as three>
        <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-2 text-center">
            <img src="${ossUrl}${three}" alt="地址错误">
            <button onclick="showModal('${three}',700)" class="btn btn-sm btn-primary mt-1">更换照片</button>
        </div>
    </#list>
</div>

<h4 class="text-center mt-4 mb-4">旋转相册</h4>
<div class="row">
    <#list ipresenters as ipresenter>
        <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-2">
            <img src="${ossUrl}${ipresenter}" alt="地址错误">
            <button onclick="showModal('${ipresenter}',600)" class="btn btn-sm btn-primary mt-1">更换照片</button>
        </div>
    </#list>
</div>

    <h4 class="text-center mt-4 mb-4">旋转相册（略缩图）</h4>
    <div class="row">
        <#list ipresenterThumbs as thumb>
            <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-2">
                <img src="${ossUrl}${thumb}" alt="地址错误">
                <button onclick="showModal('${thumb}',80)" class="btn btn-sm btn-primary mt-1">更换照片</button>
            </div>
        </#list>
    </div>

<h4 class="text-center mt-4 mb-4">魔方相册</h4>
<div class="row">
    <#list rotates as rotate>
        <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-2">
            <img src="${ossUrl}${rotate}" alt="地址错误">
            <button onclick="showModal('${rotate}',120)" class="btn btn-sm btn-primary mt-1">更换照片</button>
        </div>
    </#list>
</div>

<h4 class="text-center mt-4 mb-4">九宫格相册</h4>
<div class="row">
    <#list walls as wall>
        <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-2">
            <img src="${ossUrl}${wall}" alt="地址错误">
            <button onclick="showModal('${wall}',200)" class="btn btn-sm btn-primary mt-1">更换照片</button>
        </div>
    </#list>
</div>


<button id="btn-modal" style="display: none;" data-toggle="modal" data-target="#myModal"></button>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">更换照片</h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input onchange="changeImage()" id="image" type="file" name="editormd-image-file" accept="image/*" class="form-control"/>
                    <div class="text-center" style="width: 100%;height: 10rem;line-height: 10rem;">
                        <img style="max-width: 10rem;max-height: 10rem;" id="show-img" src="" alt="预览图片">
                    </div>
                    <p><small class="text-muted" id="p-tip">尺寸：</small></p>
                    <div>
                        <label><small class="text-muted">旋转：</small></label>
                        <button onclick="rotateImg(1)" class="btn btn-sm btn-warning">左旋转</button>
                        <button onclick="rotateImg(0)" class="btn btn-sm btn-warning">复位</button>
                        <button onclick="rotateImg(2)" class="btn btn-sm btn-warning">右旋转</button>
                    </div>
                    <div style="display: flex;flex-direction: row;">
                        <label><small class="text-muted mr-2" id="p-range">品质：0.4</small></label>
                        <input style="flex: 1;" onchange="changeQuality()" id="input-range" type="range" min="1" max="10" value="4"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <input type="button" onclick="uploadFile()" class="btn btn-primary" value="开始上传"/>
            </div>
        </div>
    </div>
</div>

</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/gh/WangYuLue/image-conversion/build/conversion.js"></script>

<script type="text/javascript">
    function refresh() {
        location.reload();
    }

    var realfile,orientation,scale,url,max=600,quality,
        tip = $("#p-tip"),
        rangeTip = document.getElementById("p-range"),
        inputRange = document.getElementById("input-range"),
        showImg = document.getElementById("show-img"),
        btnModal = document.getElementById("btn-modal"),
        fileinput = document.getElementById('image');

    function showModal(_url, _max) {
        btnModal.click();
        url = _url;
        max = _max;
        console.log("url:" + url);
    }

    function changeQuality() {
        quality = (parseInt($(inputRange).val()) / 10).toFixed(1);
        $(rangeTip).html("品质：" + quality);
    }

    function rotateImg(kind) {
        if (kind === 0) {
            orientation = 0;
            $(showImg).css("transform", "rotate(0deg)");
        }else if (kind === 1) {
            orientation = 8; // 左旋转 90°
            $(showImg).css("transform", "rotate(-90deg)");
        } else if (kind === 2) {
            orientation = 6; // 右旋转 90°
            $(showImg).css("transform", "rotate(90deg)");
        }
    }

    function changeImage() {
        realfile = fileinput.files[0];
        scale = 1;
        quality = 0.4;
        orientation = 0;
        $(inputRange).val(4);
        $(showImg).css("transform", "rotate(0deg)");
        if (!realfile) {
            tip.text("请选择文件");
            showImg.src = "";
            return;
        }

        //读取图片数据
        var reader = new FileReader();
        reader.readAsDataURL(realfile);
        reader.onload = function (e) {
            var image = new Image();
            image.src = e.target.result;
            showImg.src = e.target.result;
            image.onload = function () {
                var width = image.width;
                var height = image.height;

                if (width > max || height > max) {
                    if (width > height) {
                        scale = (max / width).toFixed(2);
                    } else if (height > max) {
                        scale = (max / height).toFixed(2);
                    }
                }

                tip.text("尺寸：" + width + "*" + height + "，压缩后："
                    + parseInt(width*scale) + "*" + parseInt(height*scale));
            };
        }
    }

    function uploadFile() {
        if (!realfile) {
            tip.text("上传终止：请选择文件");return;
        }

        imageConversion.compress(realfile, {
            quality: quality,
            orientation: orientation,
            type: "image/jpeg",
            scale: scale,
        }).then(compressImgData=>{
            $.post("/adminLove/getOssPolicy", {}, function (ossData) {
                var formData = new FormData();
                formData.append("OSSAccessKeyId", ossData.accessid);
                formData.append("policy", ossData.policy);
                formData.append("signature", ossData.signature);
                formData.append("key", url);
                formData.append("success_action_status", '200');
                formData.append("file", compressImgData);
                $.ajax({
                    url: ossData.host,
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    beforeSend: function(){
                        tip.text("文件上传中...");
                    },
                    success: function(e2) {
                        tip.text("文件上传成功：" + JSON.stringify(e2));
                    },
                    error: function(e1) {
                        tip.text("文件上传失败：" + JSON.stringify(e1));
                    }
                });
            }, "json");
        })
    }
</script>
</body>
</html>