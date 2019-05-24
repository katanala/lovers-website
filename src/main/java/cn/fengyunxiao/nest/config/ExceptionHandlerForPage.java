package cn.fengyunxiao.nest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerForPage {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerForJson.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> resolveException(Exception e) {
        logger.warn("ExceptionMessage：" + e.getMessage());
        logger.warn("ExceptionCause：" + e.getCause());
        JsonResult<String> result = new JsonResult<>();
        return result.error(-1, "thinkphp遇到错误，请查看日志");
    }

}
