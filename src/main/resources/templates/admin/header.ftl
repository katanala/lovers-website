<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/adminLove">小窝后台</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if pageKind?? && pageKind == "image">
                <li class="nav-item active"><a class="nav-link" href="/adminLove/image">照片</a></li>
            <#else>
                <li class="nav-item"><a class="nav-link" href="/adminLove/image">照片</a></li>
            </#if>

            <#if pageKind?? && pageKind == "image2">
                <li class="nav-item active"><a class="nav-link" href="/adminLove/image2">相册</a></li>
            <#else>
                <li class="nav-item"><a class="nav-link" href="/adminLove/image2">相册</a></li>
            </#if>

            <#if pageKind?? && pageKind == "blogs">
                <li class="nav-item active"><a class="nav-link" href="/adminLove/blogs">博客</a></li>
            <#else>
                <li class="nav-item"><a class="nav-link" href="/adminLove/blogs">博客</a></li>
            </#if>

            <#if pageKind?? && pageKind == "install">
                <li class="nav-item active"><a class="nav-link" href="/adminLove/install">配置</a></li>
            <#else>
                <li class="nav-item"><a class="nav-link" href="/adminLove/install">配置</a></li>
            </#if>

            <#if pageKind?? && pageKind == "link">
                <li class="nav-item active"><a class="nav-link" href="/adminLove/link">友链</a></li>
            <#else>
                <li class="nav-item"><a class="nav-link" href="/adminLove/link">友链</a></li>
            </#if>
        </ul>
    </div>
</nav>