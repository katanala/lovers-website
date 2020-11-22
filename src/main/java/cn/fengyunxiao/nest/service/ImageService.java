package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.dao.ImageDao;
import cn.fengyunxiao.nest.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageDao imageDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Image> list(Integer iid, Integer per) {
        if (iid == null || iid <= 0) {
            iid = Integer.MAX_VALUE;
        }
        if (per == null || per > 16) {
            per = 16;
        }
        final List<Image> images = this.imageDao.listUploadImage(iid, per);
        for (final Image image : images) {
            image.setName(Config.ossUrlPrefix + image.getName());
        }
        return images;
    }
}
