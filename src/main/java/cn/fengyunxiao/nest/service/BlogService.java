package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.util.XssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BlogService {
    @Autowired private BlogDao blogDao;
    @Autowired private IpService ipService;
    private static final Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public JsonResult<Blog> get(final Integer bid, HttpServletRequest request) {
        final JsonResult<Blog> result = new JsonResult<Blog>();
        this.ipService.addIp(request);

        if (bid == null || bid < 1) {
            return result.error(-1, "bid error");
        }
        final Blog blog = this.blogDao.get(bid);
        if (blog == null) {
            return result.error(-1, "blog not exist");
        }
        return result.ok(blog, "ok");
    }

    @CacheEvict(value = { "blog" }, key = "#blog.bid")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> update(final Blog blog, String adminToken) {
        final JsonResult<String> result = new JsonResult<String>();
        if (blog == null || blog.getBid() == null) {
            return result.error(-1, "not exist");
        }
        if (!Config.adminLoginToken.equals(adminToken)) {
            return result.error(-1, "not admin token");
        }
        if (blog.getRank() == null) {
            return result.error(-1, "rank not exist");
        }
        if (blog.getTitle() == null) {
            return result.error(-1, "title not exist");
        }
        blog.setTitle(blog.getTitle().trim());
        if (blog.getTitle().length() > 200) {
            return result.error(-1, "title length > 200");
        }
        if (blog.getKeyword() == null) {
            return result.error(-1, "keyword not exist");
        }
        blog.setKeyword(blog.getKeyword().trim());
        blog.setKeyword(blog.getKeyword().replaceAll("\\s+", ","));
        blog.setKeyword(blog.getKeyword().replace("，", ","));
        if (blog.getKeyword().length() > 200) {
            return result.error(-1, "keyword length>200");
        }
        if (blog.getContent() == null || blog.getContent().length() > 15000) {
            return result.error(-1, "content length>15000");
        }
        blog.setModtime(new Timestamp(System.currentTimeMillis()));
        this.blogDao.update(blog);
        return result.ok("ok", "ok");
    }

    @CacheEvict(value = { "blog" }, key = "#bid")
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> delete(Integer bid, String adminToken) {
        JsonResult<String> result = new JsonResult<String>();
        if (!Config.adminLoginToken.equals(adminToken)) {
            return result.error(-1, "not admin token");
        }
        if (bid == null || bid < 1) {
            return result.error(-1, "bid error");
        }
        blogDao.delete(bid);
        return result.ok("ok", "ok");
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int count(final String key, final int size) {
        if (key == null || key.length() < 2) {
            return this.blogDao.countAll();
        }
        if (size >= Config.pageNumber) {
            return Config.pageNumber * 5;
        }
        return Config.pageNumber;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Blog> search(final String key, final Integer page) {
        final int per = Config.pageNumber;
        List<Blog> blogs;
        if (key == null || key.length() < 2) {
            blogs = this.blogDao.listByPage((page - 1) * per, per);
        } else {
            blogs = this.blogDao.searchByKey2(key, (page - 1) * per, per);
        }
        return this.replaceTitleEsc(blogs);
    }

    @Cacheable(value = { "blog" }, key = "#bid")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Blog getBlogEsc(final Integer bid) {
        BlogService.logger.info("sql_bid:{}", bid);
        Blog blog = this.blogDao.get(bid);
        if (blog == null) {
            blog = new Blog();
            blog.setBid(0);
            blog.setTitle("");
            blog.setContent("");
            blog.setKeyword("");
            blog.setUrl("");
            blog.setRank((byte) 0);
            blog.setModtime(new Timestamp(System.currentTimeMillis()));
            return blog;
        }
        blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
        blog.setKeyword(XssUtil.replaceHtmlToEsc(blog.getKeyword()));
        blog.setContent(XssUtil.replaceHtmlToEsc(blog.getContent()));
        return blog;
    }

    public List<Blog> replaceTitleEsc(final List<Blog> blogs) {
        for (final Blog blog : blogs) {
            blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
            blog.setKeyword(XssUtil.replaceHtmlToEsc(blog.getKeyword()));
        }
        return blogs;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Blog> rand(final int number) {
        final List<Blog> rands = this.blogDao.listRand(number);
        for (final Blog blog : rands) {
            blog.setTitle(XssUtil.replaceHtmlToEsc(blog.getTitle()));
        }
        return rands;
    }
}
