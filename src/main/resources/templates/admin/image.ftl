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
    <link rel="stylesheet" type="text/css" href="/css/admin_commen.css">
</head>
<body>

<#include "header.ftl" >

<div class="container">
    <div class="mt-3 mb-3" class="row-between">
        <div>
            <button class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">上传照片</button>
            <button class="btn btn-sm btn-primary" onclick="refresh()">刷新</button>
        </div>
        <span>${total} 张照片</span>
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">上传照片</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input onchange="changeImage()" id="image" type="file" name="editormd-image-file" accept="image/*" class="form-control"/>
                        <div class="text-center" style="width: 100%;height: 8rem;line-height: 8rem;">
                            <img style="max-width: 8rem;max-height: 8rem;" id="show-img" src="" alt="预览图片">
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

    <div class="row">
        <#list images as image>
        <div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 mb-1">
            <img style="max-width: 100%;" src="${ossUrl}${image.name}" />
            <div class="mt-1">
                <button onclick="copyUrl(this)" url="${ossUrl}${image.name}" class="btn btn-sm btn-secondary">复制</button>
                <button onclick="deleteByIid(${image.iid})" class="btn btn-sm btn-warning">删除</button>
            </div>
        </div>
        </#list>
    </div>

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
<script type="text/javascript" src="https://cdn.jsdelivr.net/gh/WangYuLue/image-conversion/build/conversion.js"></script>

<script type="text/javascript">
    setPage(Number(${curpage}), Number(${allpage}), "getMsg");

    function getMsg(p) {
        location.href = "/adminLove/image?page=" + p;
    }

    function refresh() {
        location.reload();
    }

    function deleteByIid(iid) {
        if (confirm("确认删除？")) {
            $.post("/adminLove/deleteImage", {iid:iid}, function (data) {
                alert(data.msg);
                location.reload();
            }, "json");
        }
    }

    function copyUrl(obj) {
        var url = obj.getAttribute("url");
        copyText(url, function () {
            console.log(url);
        });
    }

    var realfile,orientation,scale,tip = $("#p-tip"),max=720,quality,
        rangeTip = document.getElementById("p-range"),
        inputRange = document.getElementById("input-range"),
        showImg = document.getElementById("show-img"),
        fileinput = document.getElementById('image');

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
        orientation = 0;
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
            quality: 0.4,
            orientation: orientation,
            type: "image/jpeg",
            scale: scale,
        }).then(compressImgData=>{
            $.post("/adminLove/getOssPolicy", {}, function (ossData) {
                var key = ossData.dir + "falls/" + getNameByDate() + ".jpg";

                var formData = new FormData();
                formData.append("OSSAccessKeyId", ossData.accessid);
                formData.append("policy", ossData.policy);
                formData.append("signature", ossData.signature);
                formData.append("key", key);
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
                        $.post("/adminLove/insertImage", {bid:-1,fileName:key}, function (sqlres) {
                            tip.text("文件上传成功，" + sqlres.msg);
                        }, "json");
                    },
                    error: function(e1) {
                        tip.text("文件上传失败：" + JSON.stringify(e1));
                    }
                });
            }, "json");
        })
    }

    // 复制的方法
    function copyText(text, callback){ // text: 要复制的内容， callback: 回调
        var tag = document.createElement('input');
        tag.setAttribute('id', 'cp_hgz_input');
        tag.value = text;
        document.getElementsByTagName('body')[0].appendChild(tag);
        document.getElementById('cp_hgz_input').select();
        document.execCommand('copy');
        document.getElementById('cp_hgz_input').remove();
        if(callback) {callback(text)}
    }

    // 根据时间，获取当前文件名
    function getNameByDate() {
        var time = new Date();

        var month = time.getMonth() + 1;
        var date = time.getDate();
        var hours = time.getHours();
        var minutes = time.getMinutes();
        var seconds = time.getSeconds();

        if(Number(month) < 10) {
            month = '0' + month;
        }
        if(Number(date) < 10) {
            date = '0' + date;
        }
        if(Number(hours) < 10) {
            hours = '0' + hours;
        }
        if(Number(minutes) < 10) {
            minutes = '0' + minutes;
        }
        if(Number(seconds) < 10) {
            seconds = '0' + seconds;
        }

        var rand = Math.round(Math.random()*1000);
        return time.getFullYear()+month+date+"_"+hours+minutes+seconds+"_"+rand;
    }
</script>
</body>
</html>