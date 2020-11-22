package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.dao.LinkDao;
import cn.fengyunxiao.nest.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LinkService {
    @Autowired
    private LinkDao linkDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Link> getLink() {
        return this.linkDao.listLink();
    }
}
