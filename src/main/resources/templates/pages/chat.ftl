<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="${firstPage.keywords}" />
  <meta name="description" content="${firstPage.description}" />
  <title>我们的小窝-聊天室</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnMuiCss}" />
  <link rel="stylesheet" type="text/css" href="css/chat.css?v=${firstPage.timeStampHtml}" />
</head>

<body contextmenu="return false;">
<header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title" id="tip">chat (聊天窗口)</h1>
  <a id="getMoreMessage" class="mui-icon mui-icon-more-filled mui-pull-right"></a>
</header>
<pre style="visibility: hidden;" id='h'></pre>

<div class="mui-content">
  <div id="msg-list"></div>
</div>

<footer>
  <div class="footer-left">
    <i id='msg-image' class="mui-icon mui-icon-person-filled" style="font-size: 28px;"></i>
  </div>
  <div class="footer-center">
    <textarea id="msg-text" class="input-text"></textarea>
  </div>
  <label class="footer-right"> <i id='msg-type' class="mui-icon"></i>
  </label>
</footer>
<script type="text/javascript" src="${firstPage.cdnMuiJs}"></script>
<script type="text/javascript" src="js/chat.js?v=${firstPage.timeStampHtml}" charset="UTF-8"></script>
</body>

</html>