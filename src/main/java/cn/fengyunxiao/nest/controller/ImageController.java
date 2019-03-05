package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.entity.Image;
import cn.fengyunxiao.nest.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Image> list(Integer iid, Integer per) {
        return imageService.list(iid, per);
    }

}
