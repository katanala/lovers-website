package cn.fengyunxiao.nest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerForJson {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerForJson.class);

    // 图片大小超过指定大小，抛异常
    @ResponseBody
    @ExceptionHandler(value = MultipartException.class)
    public Map<String, Object> resolveFileUploadException(MultipartException e) {
        logger.warn("MultipartMessage："+e.getMessage());
        logger.warn("MultipartgetCause："+e.getCause());
        Map<String, Object> map = new HashMap<>();
        map.put("success", 0);
        map.put("message", "上传错误：图片大小超过指定值。建议10M以内");
        return map;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> resolveException(Exception e) {
        logger.warn("ExceptionMessage：" + e.getMessage());
        logger.warn("ExceptionCause：" + e.getCause());
        JsonResult<String> result = new JsonResult<>();
        return result.error(-1, "thinkphp遇到错误，请查看日志");
    }

}
