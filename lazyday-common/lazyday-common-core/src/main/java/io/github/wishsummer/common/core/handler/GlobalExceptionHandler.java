package io.github.wishsummer.common.core.handler;

import io.github.wishsummer.common.core.constant.HttpStatus;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.common.core.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: GlobalExceptionHandler.java, 2024/1/5 16:08 $
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 业务异常
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.FORBIDDEN.getCode(), e.getMessage());
    }
}
