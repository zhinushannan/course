package club.kwcoder.business.controller;

import club.kwcoder.server.dto.ResultBean;
import club.kwcoder.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zhinushannan
 * @create: 2021/11/21  15:55
 * @description:
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResultBean<Object> validatorExceptionHandler(ValidatorException e) {
        LOG.warn(e.getMessage());
        return ResultBean.getError("请求参数异常！", null);
    }

}
