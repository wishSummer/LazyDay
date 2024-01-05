package io.github.wishsummer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: ServiceException.java, 2023/12/26 15:46 $
 */

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;
}
