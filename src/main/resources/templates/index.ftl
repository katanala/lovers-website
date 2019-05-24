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
  <title>我们的小窝</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="css/commen.css?v=${firstPage.timeStampHtml}" />
  <link rel="stylesheet" type="text/css" href="css/index.css?v=${firstPage.timeStampHtml}" />
</head>
<body>

<#include "pages/header.ftl" >

<div class="main bg-pink">

  <!-- 标题和名称↓ -->
  <section class="jumbotron text-center">
    <div class="container ">
      <h1>${firstPage.titlePrimary}</h1>
      <h3>${firstPage.titleSecondary}</h3>
      <div style="text-align: center;width: 100%;margin-top: 2.2rem;">
        <a href="/chat" class="btn btn-outline-danger mr-md-2">聊天室</a>
        <a href="/falls" class="btn btn-outline-danger mr-md-2">小相册</a>
        <a class="btn btn-outline-danger" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${firstPage.contactQQ}&site=qq&menu=yes">在线QQ</a>
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
      <div class="col-6 col-sm-6 col-lg-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/photos/3d/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/1.jpg" alt="3D 相册">
          </a>
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
              <a href="//static.fengyunxiao.cn/nest/photos/3d/index.html" target="_blank" class="btn btn-sm btn-link text-muted"> 3D 相册 </a>
            </div>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/photos/ipresenter/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/2.jpg" alt="旋转相册">
          </a>
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
              <a href="//static.fengyunxiao.cn/nest/photos/ipresenter/index.html" target="_blank" class="btn btn-sm btn-link text-muted"> 旋转相册 </a>
            </div>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/photos/rotate/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/3.jpg" alt="魔方相册">
          </a>
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
              <a href="//static.fengyunxiao.cn/nest/photos/rotate/index.html" target="_blank" class="btn btn-sm btn-link text-muted"> 魔方相册 </a>
            </div>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/photos/wall/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/4.jpg" alt="九宫格相册">
          </a>
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
              <a href="//static.fengyunxiao.cn/nest/photos/wall/index.html" target="_blank" class="btn btn-sm btn-link text-muted"> 九宫格相册 </a>
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
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/storys/record/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/5.jpg" alt="不灵不灵">
          </a>
          <div class="card-body">
            <a href="//static.fengyunxiao.cn/nest/storys/record/index.html" target="_blank" class="btn btn-sm btn-link text-muted">Time of Love</a>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/storys/code/170520.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/6.jpg" alt="不灵不灵">
          </a>
          <div class="card-body">
            <a href="//static.fengyunxiao.cn/nest/storys/code/170520.html" target="_blank" class="btn btn-sm btn-link text-muted">Word of Love</a>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/storys/newYear/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/7.jpg" alt="不灵不灵">
          </a>
          <div class="card-body">
            <a href="//static.fengyunxiao.cn/nest/storys/newYear/index.html" target="_blank" class="btn btn-sm btn-link text-muted">Years of Love</a>
          </div>
        </div>
      </div>
      <div class="col-6 col-sm-6 col-md-6 col-lg-3 col-xl-3">
        <div class="card">
          <a href="//static.fengyunxiao.cn/nest/storys/valentine/index.html" target="_blank">
            <img class="img-thumbnail rounded card-img-top" src="image/index/8.jpg" alt="不灵不灵">
          </a>
          <div class="card-body">
            <a href="//static.fengyunxiao.cn/nest/storys/valentine/index.html" target="_blank" class="btn btn-sm btn-link text-muted">Lover of Love</a>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div id="visitor" class="container">
    <h1 class="projects-header">来访痕迹</h1>
    <div class="row" id="contentList">

    </div>
    <div class="btn-group btn-group-lg" role="group">
      <button type="button" class="btn btn-light" onclick="firstLetter()">最新</button>
      <button type="button" class="btn btn-light" onclick="randLetter()">随机</button>
      <button type="button" class="btn btn-light" onclick="getLetter()">下页</button>
    </div>
  </div>

  <div id="message" class="container">
    <h1 class="projects-header">我要留言</h1>
    <div class="row">
      <div class="col-xs-12 col-md-4">
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
      <div class="col-xs-12 col-md-5">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="input-group-text">内容</span>
          </div>
          <textarea name="content" id="content" rows="6" maxlength="255" class="form-control"></textarea>
        </div>
        <div class="input-group nickname-and-button">
          <div class="input-group-prepend">
            <span class="input-group-text">昵称</span>
          </div>
          <input name="nickname" id="nickname" maxlength="16" class="form-control" type="text"/>
          <span class="input-group-append">
            <button class="btn btn-success" onclick="subMessage();">踩一踩</button>
          </span>
        </div>
      </div>
      <div class="col-xs-12 col-md-3">
        <div class="text-center pt-3">
          <video autoplay loop width="180px">
            <source src="image/heart.webm" type="video/mp4">
            <img src="image/heart.jpg" width="180px" title="你的浏览器不支持播放视频">
          </video>
        </div>
      </div>
    </div>
  </div>

</div>

<#include "pages/footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="js/index.js?v=${firstPage.timeStampHtml}" charset="UTF-8"></script>

</body>
</html>