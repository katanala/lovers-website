<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <link rel="shortcut" type="image/x-icon" href="favicon.ico" />
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="Keywords" content="${firstPage.keywords}"/>
    <meta name="description" content="${firstPage.description}"/>
    <title>我们的小窝</title>
    <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}"/>
    <link rel="stylesheet" type="text/css" href="css/commen.css?v=${firstPage.timeStampHtml}"/>
    <link rel="stylesheet" type="text/css" href="css/index.css?v=${firstPage.timeStampHtml}"/>
</head>
<body>

<#include "pages/header.ftl" >

<div class="main bg-pink">

    <!-- 标题和名称↓ -->
    <section class="jumbotron text-center">
        <div class="container">
            <h1>${firstPage.titlePrimary}</h1>
            <h3>${firstPage.titleSecondary}</h3>
            <div class="text-center mt-5" style="width: 100%;">
                <a href="javascript:loadChat()" class="btn btn-outline-danger mr-md-2">聊天室</a>
                <a href="/falls" class="btn btn-outline-danger mr-md-2">小相册</a>
                <a href="javascript:loadIps()" class="btn btn-outline-danger mr-md-2">访客</a>
            </div>
        </div>
    </section>
    <!-- 标题和名称↑ -->

    <!-- 相爱时间↓ -->
    <section class="jumbotron-days text-center">
        <div class="container">
            <h1>We have loved For</h1>
            <div class="time">
                <div>${firstPage.lovePeriodYears} 年</div>
                <div>${firstPage.lovePeriodMonths} 月</div>
                <div>${firstPage.lovePeriodDays} 日</div>
            </div>
        </div>
    </section>
    <!-- 相爱时间↑ -->

    <!-- 图片相册↓ -->
    <div id="picture" class="container">
        <h1 class="projects-header">我们的相册</h1>
        <div class="row">
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/photos/3d/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/1.jpg" alt="3D 相册">
                    </a>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <a href="//static.fengyunxiao.cn/nest/photos/3d/index.html" target="_blank"
                               class="btn btn-sm btn-link text-muted"> 3D 相册 </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/photos/ipresenter/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/2.jpg" alt="旋转相册">
                    </a>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <a href="//static.fengyunxiao.cn/nest/photos/ipresenter/index.html" target="_blank"
                               class="btn btn-sm btn-link text-muted"> 旋转相册 </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/photos/rotate/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/3.jpg" alt="魔方相册">
                    </a>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <a href="//static.fengyunxiao.cn/nest/photos/rotate/index.html" target="_blank"
                               class="btn btn-sm btn-link text-muted"> 魔方相册 </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/photos/wall/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/4.jpg" alt="九宫格相册">
                    </a>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <a href="//static.fengyunxiao.cn/nest/photos/wall/index.html" target="_blank"
                               class="btn btn-sm btn-link text-muted"> 九宫格相册 </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- 图片相册↑ -->

    <div id="story" class="container">
        <h1 class="projects-header">我们的故事</h1>
        <div class="row">
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/storys/record/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/5.jpg" alt="不灵不灵">
                    </a>
                    <div class="card-body">
                        <a href="//static.fengyunxiao.cn/nest/storys/record/index.html" target="_blank"
                           class="btn btn-sm btn-link text-muted">Time of Love</a>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/storys/code/170520.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/6.jpg" alt="不灵不灵">
                    </a>
                    <div class="card-body">
                        <a href="//static.fengyunxiao.cn/nest/storys/code/170520.html" target="_blank"
                           class="btn btn-sm btn-link text-muted">Word of Love</a>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/storys/newYear/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/7.jpg" alt="不灵不灵">
                    </a>
                    <div class="card-body">
                        <a href="//static.fengyunxiao.cn/nest/storys/newYear/index.html" target="_blank"
                           class="btn btn-sm btn-link text-muted">Years of Love</a>
                    </div>
                </div>
            </div>
            <div class="col-6 col-sm-6 col-lg-3 mb-3">
                <div class="card">
                    <a href="//static.fengyunxiao.cn/nest/storys/valentine/index.html" target="_blank">
                        <img class="img-thumbnail rounded card-img-top" data-original="image/index/8.jpg" alt="不灵不灵">
                    </a>
                    <div class="card-body">
                        <a href="//static.fengyunxiao.cn/nest/storys/valentine/index.html" target="_blank"
                           class="btn btn-sm btn-link text-muted">Lover of Love</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="visitor" class="container">
        <h1 class="projects-header">来访痕迹</h1>
        <div class="row" id="contentList"></div>
        <div class="btn-group btn-group-lg">
            <button type="button" class="btn btn-light" onclick="firstLetter()">最新</button>
            <button type="button" class="btn btn-light" onclick="getLetter()">下页</button>
            <button type="button" class="btn btn-light" onclick="randLetter()">随机</button>
        </div>
    </div>

    <div id="message" class="container">
        <h1 class="projects-header">我要留言</h1>
        <div class="row">
            <div class="col-xs-12 col-md-4 mb-3">
                <div id="tags">
                    <a class="tagc1">弱水三千</a>
                    <a class="tagc2">药石无医</a>
                    <a class="tagc3">在一起</a>
                    <a class="tagc4">一生一世一双人</a>
                    <a class="tagc1">祝福祝福</a>
                    <a class="tagc2">执子之手</a>
                    <a class="tagc3">与子偕老</a>
                    <a class="tagc4">么么哒</a>
                    <a class="tagc1">所有的运气</a>
                    <a class="tagc2">只取一瓢</a>
                    <a class="tagc3">这个妹妹我曾经见过的</a>
                    <a class="tagc4">喜你成疾</a>
                    <a class="tagc1">只为找到你</a>
                    <a class="tagc2">小宝宝</a>
                    <a class="tagc3">晨宝宝</a>
                </div>
            </div>
            <div class="col-xs-12 col-md-5 mb-3">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">内容</span>
                    </div>
                    <textarea name="content" id="content" rows="5" maxlength="255" class="form-control"></textarea>
                </div>
                <div class="input-group mail-and-button">
                    <div class="input-group-prepend">
                        <span class="input-group-text">邮箱</span>
                    </div>
                    <input name="email" id="email" maxlength="50" class="form-control" type="email" placeholder="选填，需要联系时填写"/>
                </div>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text">昵称</span>
                    </div>
                    <input name="nickname" id="nickname" maxlength="16" class="form-control" type="text"/>
                    <span class="input-group-append">
                        <button class="btn btn-success" onclick="subMessage()">踩一踩</button>
                    </span>
                </div>
            </div>
            <div class="col-xs-12 col-md-3 mb-3">
                <div class="text-center mt-2">
                    <h5 class="gzh-title">关注公众号联系我</h5>
                    <img class="gzh-img" src="https://static.fengyunxiao.cn/nest/photos/gongzhonghao.jpg" alt="公众号：aiaichaquan">
                </div>
            </div>
        </div>
    </div>

</div>

<!-- 按钮：用于打开模态框 -->
<button id="btn-open-modal-reply" type="button" style="display: none;" data-toggle="modal" data-target="#replyModal"></button>
<div class="modal fade" id="replyModal">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
    <h4 class="modal-title">回复：</h4>
    <button id="btn-close-modal" type="button" class="close" data-dismiss="modal">&times;</button>
</div>
<div class="modal-body">
    <div class="input-group">
        <div class="input-group-prepend">
            <span class="input-group-text">内容</span>
        </div>
        <textarea id="replycontent" rows="5" maxlength="255" class="form-control"></textarea>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <span class="input-group-text">昵称</span>
        </div>
        <input id="replynickname" maxlength="16" class="form-control" type="text"/>
        <span class="input-group-append">
            <button class="btn btn-primary" onclick="subReply()">回复</button>
        </span>
    </div>
</div>
</div>
</div>
</div>

<!-- 按钮：用于打开模态框 -->
<button id="btn-open-modal-chart" type="button" style="display: none;" data-toggle="modal" data-target="#chartModal"></button>
<div class="modal fade" id="chartModal">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
    <h6 class="modal-title">已访问 ${ipCount} 次</h6>
    <button id="btn-close-modal" type="button" class="close" data-dismiss="modal">&times;</button>
</div>
<div class="modal-body">
    <table class="table table-backgroud table-hover table-bordered table-striped table-pink">
        <thead>
        <tr>
            <th scope="row">城市 | 网络</th>
            <th scope="row">ip | 时间</th>
        </tr>
        </thead>
        <tbody id="ip-list"></tbody>
    </table>

    <div style="width: 100%;display: flex;justify-content: center;">
    <ul id="pagination" class="pagination pagination-sm">
        <li class="page-item head"><a class="page-link" href="#">＜</a></li>
        <li class="page-item disabled morehead d-none d-sm-inline-block"><a class="page-link">...</a></li>
        <li class="page-item page-2 d-none d-sm-block"><a class="page-link">1</a></li>
        <li class="page-item page-1"><a class="page-link">2</a></li>
        <li class="page-item currentpage active"><a class="page-link">1</a></li>
        <li class="page-item page_1"><a class="page-link">3</a></li>
        <li class="page-item page_2 d-none d-sm-block"><a class="page-link">4</a></li>
        <li class="page-item disabled moretail d-none d-sm-inline-block"><a class="page-link">...</a></li>
        <li class="page-item tail"><a class="page-link" href="#">＞</a></li>
    </ul>
    </div>
</div>
</div>
</div>
</div>

<!-- 按钮：用于打开模态框 -->
<button id="btn-open-modal-chat" type="button" style="display: none;" data-toggle="modal" data-target="#chatModal"></button>
<div class="modal fade" id="chatModal">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
    <h6 class="modal-title" id="tip">在线人数：</h6>
    <button id="btn-close-modal" type="button" class="close" data-dismiss="modal">&times;</button>
</div>
<div class="modal-body p-0">
    <pre style="visibility: hidden;" id='hhh'></pre>
    <div id="msg-list">
    </div>
</div>
<div class="modal-footer-chat" id="chatModalFoot">
    <textarea id="msg-text" class="p-1"></textarea>
    <table id="msg-table" class="text-center">
        <tr>
            <th colspan="3">请选择您的角色：</th>
        </tr>
        <tr>
            <td><label class="mb-0"><input value="2" type="radio" name="chat-user"> 游客 </label></td>
            <td><label class="mb-0"><input value="1" type="radio" name="chat-user"> 晨哥 </label></td>
            <td><label class="mb-0"><input value="0" type="radio" name="chat-user"> 芸霄 </label></td>
        </tr>
    </table>
    <button onclick="chooseUser()" class="btn btn-light btn-user">角色</button>
    <button onclick="sendBtn()" class="btn btn-primary btn-send">发送</button>
</div>
</div>
</div>
</div>

<#include "pages/footer.ftl" >

<script src="${firstPage.cdnJqueryJs}"></script>
<script src="${firstPage.cdnXssJs}"></script>
<script src="${firstPage.cdnLazyloadJs}"></script>
<script src="js/index.js?v=${firstPage.timeStampHtml}"></script>
<script>
    var ip_js = [false, "js/lib/pagetool.js", "js/ip.js"];
    var chat_js = [false, "js/chat.js"];
    var allpage = ${allpage};
    var perpage = ${perpage};
</script>
<script src="js/lib/scrolltag.min.js?v=${firstPage.timeStampHtml}"></script>
<script src="${firstPage.cdnBootstrapJs}"></script>
</body>
</html>