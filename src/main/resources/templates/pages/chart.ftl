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
  <title>我们的小窝-统计</title>
  <link rel="stylesheet" type="text/css" href="${firstPage.cdnBootstrapCss}" />
  <link rel="stylesheet" type="text/css" href="css/commen.css?v=${firstPage.timeStampHtml}" />
</head>
<body>

<#include "header.ftl" >

<div class="container mt-3 mb-3">

    <div class="row">
        <div class="col-lg-6">
            <canvas id="myChart1"></canvas>
        </div>
        <div class="col-lg-6">
            <canvas id="myChart2"></canvas>
        </div>
        <div class="col-lg-6">
            <canvas id="myChart3"></canvas>
        </div>
        <div class="col-lg-6">
            <canvas id="myChart4"></canvas>
        </div>
    </div>
</div>

<#include "footer.ftl" >

<script type="text/javascript" src="${firstPage.cdnJqueryJs}"></script>
<script type="text/javascript" src="${firstPage.cdnBootstrapJs}"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>
<script>

  var backgroundColor = [
    'rgba(255, 99, 132, 0.5)',
    'rgba(54, 162, 235, 0.5)',
    'rgba(75, 192, 192, 0.5)',
    'rgba(153, 102, 255, 0.5)',
    'rgba(255, 159, 64, 0.5)'
  ];
  var borderColor = [
    'rgba(255,99,132,1)',
    'rgba(54, 162, 235, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)'
  ];
  var layout = {
    padding: {
      left: 0,
      right: 0,
      top: 20,
      bottom: 20
    }
  };

  var ctx1 = document.getElementById("myChart1");
  var ctx2 = document.getElementById("myChart2");
  var ctx3 = document.getElementById("myChart3");
  var ctx4 = document.getElementById("myChart4");

  (function(){$.post("ip/chartStatic",{},function(data){
    init(data);
  });})();

  function init(static) {
    new Chart(ctx1, {
      type: 'bar',
      data: {
        labels: static.cityName,
        datasets: [{
          label: '城市访问排行榜',
          data: static.cityNum,
          backgroundColor: backgroundColor,
          borderColor: borderColor,
          borderWidth: 1
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 18,
          text: '城市访问排行榜'
        },
        layout: layout
      }
    });

    new Chart(ctx2, {
      type: 'bar',
      data: {
        labels: static.ispName,
        datasets: [{
          label: '运营商访问排行榜',
          data: static.ispNum,
          backgroundColor: backgroundColor,
          borderColor: borderColor,
          borderWidth: 1
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 18,
          text: '运营商访问排行榜'
        },
        layout: layout
      }
    });

    new Chart(ctx3, {
      type: 'line',
      data: {
        labels: static.monthName,
        datasets: [{
          label: '近5个月访问趋势',
          data: static.monthNum,
          backgroundColor: backgroundColor,
          borderColor: borderColor,
          borderWidth: 1
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 18,
          text: '近5个月访问趋势'
        },
        layout: layout
      }
    });

    new Chart(ctx4, {
      type: 'line',
      data: {
        labels: static.daysName,
        datasets: [{
          label: '近5天访问趋势',
          data: static.daysNum,
          backgroundColor: backgroundColor,
          borderColor: borderColor,
          borderWidth: 1
        }]
      },
      options: {
        title: {
          display: true,
          fontSize: 18,
          text: '近5天访问趋势'
        },
        layout: layout
      }
    });
  }

</script>
</body>
</html>