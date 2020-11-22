package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.dao.ImageDao;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.entity.Image;
import cn.fengyunxiao.nest.util.ImageUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@Service
public class AdminService {
    @Autowired private ImageDao imageDao;
    @Autowired private BlogDao blogDao;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, String> getOssPolicy(final Object adminToken) {
        final Map<String, String> respMap = new LinkedHashMap<String, String>();
        if (!Config.adminLoginToken.equals(adminToken)) {
            return respMap;
        }
        final OSSClient client = new OSSClient(Config.ossEndpoint, Config.ossAccessKeyId, Config.ossAccessKeySecret);
        final String dir = Config.ossPhotoFolder;
        try {
            final long expireTime = 30L;
            final long expireEndTime = System.currentTimeMillis() + expireTime * 1000L;
            final Date expiration = new Date(expireEndTime);
            final PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem("content-length-range", 0L, 1048576000L);
            policyConds.addConditionItem(MatchMode.StartWith, "key", dir);
            final String postPolicy = client.generatePostPolicy(expiration, policyConds);
            final byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            final String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            final String postSignature = client.calculatePostSignature(postPolicy);
            respMap.put("accessid", Config.ossAccessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", Config.ossProtocol + Config.ossBucket + "." + Config.ossEndpoint);
            respMap.put("expire", String.valueOf(expireEndTime / 1000L));
            return respMap;
        } catch (Exception e) {
            AdminService.logger.error("get oss policy fail");
            return respMap;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> insertImage(Integer bid, String fileName, Object adminToken) {
        JsonResult<String> result = new JsonResult<>();
        // 用户鉴权
        if (!Config.adminLoginToken.equals(adminToken)) {
            return result.error(-1, "删除失败：未授权用户");
        }

        Image image = new Image();
        image.setName(fileName);
        image.setBid(bid);
        image.setPubtime(new Timestamp(System.currentTimeMillis()));
        int num = imageDao.insertImage(image);

        return result.ok("","数据库添加数量：" + num);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> deleteImage(Integer iid, Object adminToken) {
        JsonResult<String> result = new JsonResult<>();
        // 用户鉴权
        if (!Config.adminLoginToken.equals(adminToken)) {
            return result.error(-1, "删除失败：未授权用户");
        }

        Image image = imageDao.queryImage(iid);
        if (image == null || image.getName() == null) {
            return result.error(-1, "删除失败：数据库中不存在");
        }

        String ossResult;
        if (ImageUtil.aliOssExist(image.getName())) {
            ImageUtil.aliOssDelete(image.getName());
            ossResult = "文件已删除，";
        } else {
            ossResult = "文件不存在，";
        }

        int deletenum = imageDao.deleteImage(iid);
        return result.ok("",ossResult + "数据库删除数量：" + deletenum);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> uploadImage(MultipartFile file, Integer bid, Object adminToken) {
        Map<String, Object> map = new HashMap<>();
        // success=0 表示失败，=1表示成功
        map.put("success", 0);

        if (bid == null) {
            logger.error("上传错误：bid 错误");
            map.put("message", "上传错误：bid 错误");
            return map;
        }

        // 分开博客和相册照片 在阿里云oss的存放位置
        String folder = Config.ossBlogFolder;
        if (bid < 0) {
            folder = Config.ossPhotoFolder;
        }

        // 用户鉴权
        if (!Config.adminLoginToken.equals(adminToken)) {
            logger.error("上传错误：管理员token错误");
            map.put("message", "上传错误：管理员token错误");
            return map;
        }

        // 是否有有效文件
        if (file == null || file.isEmpty()) {
            logger.error("上传错误：文件为空");
            map.put("message", "上传错误：文件为空");
            return map;
        }

        if (file.getSize() > 1024 * 1024 * 10) {
            map.put("message", "上传错误：上传错误：大小超过10M");
            return map;
        }

        // 检测后缀
        String extension = ImageUtil.getSuffix(file.getOriginalFilename());
        if (extension == null) {
            map.put("message", "上传错误：该图片无后缀");
            return map;
        }
        extension = extension.toLowerCase();
        if (!Config.imageSuffix.contains(extension)) {
            map.put("message", "上传错误：文件后缀只支持：" + Config.imageSuffix);
            return map;
        }

        // 建立目录（存放压缩的图片）
        File localFile = new File(Config.imageLocalPath);
        if (!localFile.exists()) {
            boolean res = localFile.mkdirs();
            if (!res) {
                logger.error("上传错误：上传目录创建失败");
                map.put("message", "上传错误：上传目录创建失败");
                return map;
            }
        }

        // 获取新的文件名，无后缀
        String fileName = ImageUtil.getNameByTime();

        // 如果是动图，直接上传，否则压缩成jpg后上传
        if (extension.equalsIgnoreCase(".gif")) {
            fileName = folder + fileName + ".gif";

            try {
                ImageUtil.uploadAliOss(fileName, file.getInputStream());
            } catch (Exception e) {
                logger.error("上传错误：gif上传阿里云OOS错误");
                map.put("message", "上传错误：gif上传阿里云OOS错误");
                return map;
            }
        } else {
            fileName += ".jpg";
            localFile = new File(Config.imageLocalPath + fileName);
            try {
                ImageUtil.zipImage(file.getInputStream(), localFile, Config.imageSizeMax, Config.imageQuality);
            } catch (Exception e) {
                logger.error("上传错误：图片压缩成jpg失败");
                map.put("message", "上传错误：图片压缩成jpg失败");
                return map;
            }

            fileName = folder + fileName;
            try {
                InputStream inputStream = new FileInputStream(localFile);
                ImageUtil.uploadAliOss(fileName, inputStream);
            } catch (Exception e) {
                logger.error("上传错误：jpg上传阿里云OOS错误");
                map.put("message", "上传错误：jpg上传阿里云OOS错误");
                return map;
            }
        }

        Image image = new Image();
        image.setName(fileName);
        image.setBid(bid);
        image.setPubtime(new Timestamp(System.currentTimeMillis()));
        imageDao.insertImage(image);

        map.put("success", 1);
        map.put("message", "上传成功");
        map.put("url", ImageUtil.getAliOssUrl(fileName));
        return map;
    }

    /**
     * 返回bid
     * bid < 1，并且没有草稿博客，新建草稿博客
     * bid > 0 ，返回该 bid
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int gainBlogId(Integer bid) {
        // 如果没有传参
        if (bid == null || bid < 1) {
            // 获取一个草稿
            bid = blogDao.getLastDraft();
            // 如果没有草稿，创建一个草稿
            if (bid == null || bid < 1) {
                Blog blog = new Blog();
                blog.setTitle("无标题");
                blog.setKeyword("");
                blog.setContent("无内容");
                blog.setUrl("");
                blog.setModtime(new Timestamp(System.currentTimeMillis()));
                blog.setRank((byte) -1);
                if (blogDao.insert(blog) > 0) {
                    return blog.getBid();
                }
                return 0;
            }
        }

        return bid;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int countImage() {
        return imageDao.getTotal();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Image> listImage(int page, int number) {
        return imageDao.listImageByPage((page - 1) * number, number);
    }

}
