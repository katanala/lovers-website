package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.util.XssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BlogService {
    private BlogDao blogDao;

    @Autowired
    public void setBlogDao(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public JsonResult<Blog> get(Integer bid) {
        JsonResult<Blog> result = new JsonResult<>();
        if (bid == null || bid < 1) {
            return result.error(-1, "bid 错误");
        }

        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return result.error(-1, "blog 不存在");
        }

        return result.ok(blog, "ok");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> update(Blog blog, Object adminToken) {
        JsonResult<String> result = new JsonResult<>();

        if (blog == null || blog.getBid() == null) {
            return result.error(-1, "内容不存在");
        }

        // 用户鉴权
        if (!Config.TOKEN_DO_LOGIN.equals(adminToken)) {
            return result.error(-1, "没有管理员权限，不能修改");
        }

        if (blog.getRank() == null) {
            return result.error(-1, "rank 不存在");
        }
        if (blog.getTitle() == null) {
            return result.error(-1, "title 不存在");
        }
        blog.setTitle(blog.getTitle().trim());
        if (blog.getTitle().length() > 200) {
            return result.error(-1, "title 长度>200");
        }
        if (blog.getKeyword() == null) {
            return result.error(-1, "keyword 不存在");
        }

        blog.setKeyword(blog.getKeyword().trim());
        // 多个连续空白，替换成一个 ,
        blog.setKeyword(blog.getKeyword().replaceAll("\\s+", ","));
        // 中文，替换成英文,
        blog.setKeyword(blog.getKeyword().replace("，", ","));
        if (blog.getKeyword().length() > 200) {
            return result.error(-1, "keyword 长度>200");
        }
        if (blog.getContent() == null || blog.getContent().length() > 15000) {
            return result.error(-1, "content 不存在或长度>15000");
        }
        blog.setModtime(new Timestamp(System.currentTimeMillis()));

        blogDao.update(blog);
        return result.ok("ok", "ok");
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int count(String key, int size) {
        if (key == null || key.length() < 2) {
            return blogDao.countAll();
        } else if (size >= Config.PAGE_NUMBER) {
            return Config.PAGE_NUMBER * 5;
        } else {
            return Config.PAGE_NUMBER;
        }
    }

    // 按条件查找，同时转义 title 的 html
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Blog> search(String key, Integer page) {
        int per = Config.PAGE_NUMBER;
        List<Blog> blogs;
        if (key == null || key.length() < 2) {
            blogs = blogDao.listByPage((page - 1) * per, per);
        } else {
            blogs = blogDao.searchByKey2(key, (page - 1) * per, per);
        }

        return replaceTitleEsc(blogs);
    }

    // 获取 blog 并将显示的内容的html 转义
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Blog getBlogEsc(Integer bid) {
        if (bid == null || bid <= 0) {
            return null;
        }
        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return null;
        }

        blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
        blog.setKeyword(XssUtil.replaceHtmlToEsc(blog.getKeyword()));
        blog.setContent(XssUtil.replaceHtmlToEsc(blog.getContent()));
        return blog;
    }

    public List<Blog> replaceTitleEsc(List<Blog> blogs) {
        for (Blog blog : blogs) {
            blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
            blog.setKeyword(XssUtil.replaceHtmlToEsc(blog.getKeyword()));
        }
        return blogs;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Blog> rand(int number) {
        List<Blog> rands = blogDao.listRand(number);
        for (Blog blog : rands) {
            blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
        }
        return rands;
    }
}
