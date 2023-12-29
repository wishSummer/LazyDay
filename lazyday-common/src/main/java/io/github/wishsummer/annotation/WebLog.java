package io.github.wishsummer.annotation;

import io.github.wishsummer.enums.BusinessTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {

    /**
     * 模块标题
     */
    String title() default "";

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求数据
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存相应数据
     */
    boolean isSaveResponseData() default true;

    /**
     * 保存日志排除指定的请求参数
     */
    String[] excludeParams() default {};

}
