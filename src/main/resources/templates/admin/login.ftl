<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8" />
  <meta name="renderer" content="webkit" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="Keywords" content="${firstPage.keywords}" />
  <meta name="description" content="${firstPage.description}" />
  <title>小窝-后台-登录</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
</head>

<body>

<div class="container mt-3">
  <form action="doLogin" method="post">
    <input type="hidden" name="tokenLogin" id="tokenLogin" value="${tokenLogin}">
    <div class="form-group">
      <label for="phone">手机号：</label>
      <input type="password" class="form-control" id="phone" name="phone" placeholder="手机号">
    </div>
    <div class="form-group">
      <label for="google">谷歌验证：</label>
      <input type="text" class="form-control" id="google" name="google" placeholder="谷歌验证">
    </div>
    <div id="yanzhengma-parent" class="form-group">
      <label for="veri">验证码: <img onclick="refreshimage(this)" src="veri" /> </label>
      <input type="text" class="form-control" id="veri" name="veri" placeholder="验证码">
    </div>
    <button type="submit" class="btn btn-primary">登录</button>
  </form>
</div>

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script>
  function refreshimage(obj) {
    obj.src = "veri?r=" + Math.random();
  }
</script>
</body>
</html>
