<header>
    <div class="collapse bg-pink" id="navbarHeader">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-md-7 py-3">
                    <h4>关于：我们的小窝</h4>
                    <p class="text-muted">This nest blongs to 张圣晨 and 冯芸霄, <br/>Loved for ${firstPage.loveTotalDays}
                        days, Thanks for your visit. </p>
                </div>
                <div class="col-sm-4 offset-md-1 py-3">
                    <h4>联系我们</h4>
                    <ul class="list-unstyled">
                        <li><a class="text-muted" target="_blank"
                               href="http://wpa.qq.com/msgrd?v=3&uin=${firstPage.contactQQ}&site=qq&menu=yes">QQ:${firstPage.contactQQ}</a>
                        </li>
                        <li><a href="http://weibo.com/u/2151990245?is_all=1" target="_blank" class="text-muted">晨微博</a>
                            <a href="http://weibo.com/u/5345027252?is_all=1" target="_blank" class="text-muted">霄微博</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="/" class="navbar-brand d-flex align-items-center"><strong>我们的小窝</strong></a>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a href="/timeline" class="nav-link">时间线</a>
                </li>
            </ul>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader"
                    aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
</header>