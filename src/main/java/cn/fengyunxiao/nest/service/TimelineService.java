package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.TimelineDao;
import cn.fengyunxiao.nest.entity.Timeline;
import cn.fengyunxiao.nest.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TimelineService
{
    @Autowired
    private TimelineDao timelineDao;
    private static final Logger logger = LoggerFactory.getLogger(TimelineService.class);;

    @CacheEvict(value = { "timelines" }, condition = "#result.code == 0", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> insertOrUpdate(final Timeline timeline, final String sign) {
        final JsonResult<String> result = new JsonResult<String>();
        if (timeline == null) {
            return result.error(-1, "timeline empty");
        }
        if (timeline.getYear() == null || timeline.getYear() < 0) {
            return result.error(-1, "year error");
        }
        if (timeline.getTitle() == null) {
            timeline.setTitle("");
        }
        else if (timeline.getTitle().length() > 20) {
            return result.error(-1, "title length 0-20");
        }
        if (timeline.getContent1() == null) {
            timeline.setContent1("");
        }
        else if (timeline.getContent1().length() > 50) {
            return result.error(-1, "content1 length 0-50");
        }
        if (timeline.getContent2() == null) {
            timeline.setContent2("");
        }
        else if (timeline.getContent2().length() > 50) {
            return result.error(-1, "content2 length 0-50");
        }
        if (timeline.getContent3() == null) {
            timeline.setContent3("");
        }
        else if (timeline.getContent3().length() > 50) {
            return result.error(-1, "content3 length 0-50");
        }
        if (sign == null || !TokenUtil.isRightSign(sign)) {
            return result.error(-1, "sign error");
        }
        int res;
        if (timeline.getLineid() == null || timeline.getLineid() < 1) {
            res = this.timelineDao.insertTimeline(timeline);
        }
        else {
            res = this.timelineDao.updateTimeline(timeline);
        }
        if (res < 1) {
            return result.error(-1, "sql error");
        }
        return result.ok("", "ok");
    }

    @Cacheable({ "timelines" })
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Timeline> listTimeline() {
        return this.timelineDao.listTimeline();
    }

}

