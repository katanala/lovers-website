package cn.fengyunxiao.nest.controller;

import org.springframework.stereotype.*;
import cn.fengyunxiao.nest.service.*;
import org.springframework.beans.factory.annotation.*;
import cn.fengyunxiao.nest.entity.*;
import cn.fengyunxiao.nest.config.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping({ "/timeline" })
public class TimelineController {
    @Autowired private TimelineService timelineService;

    @RequestMapping({ "/insertOrUpdate" })
    @ResponseBody
    public JsonResult<String> insertOrUpdate(final Timeline timeline, final String sign) {
        return this.timelineService.insertOrUpdate(timeline, sign);
    }

    @RequestMapping({ "/list" })
    @ResponseBody
    public List<Timeline> listTimeline() {
        return this.timelineService.listTimeline();
    }
}

