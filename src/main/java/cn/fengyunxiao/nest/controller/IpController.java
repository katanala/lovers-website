package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.entity.Ip;
import cn.fengyunxiao.nest.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ip")
public class IpController {
    @Autowired private IpService ipService;

    @PostMapping("/chartStatic")
    @ResponseBody
    public Map<String, Object> chartStatic() {
        return ipService.getCharts();
    }

    @PostMapping("/listIp")
    @ResponseBody
    public List<Ip> listIp(Integer page, Integer per) {
        return ipService.listIp(page, per);
    }
}
