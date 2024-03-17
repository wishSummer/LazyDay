package io.github.wishsummer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 *
 * @TableName sys_log
 */
@TableName(value = "sys_log")
@Data
public class SysLogObject implements Serializable {
    /**
     * 日志主键
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 模块标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 业务类型（BusinessTypeEnum）
     */
    @TableField(value = "business_type")
    private Integer businessType;

    /**
     * 方法名称
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @TableField(value = "log_type")
    private Integer logType;

    /**
     * 操作人员
     */
    @TableField(value = "log_name")
    private String logName;

    /**
     * 请求URL
     */
    @TableField(value = "log_url")
    private String logUrl;

    /**
     * 主机地址
     */
    @TableField(value = "log_ip")
    private String logIp;

    /**
     * 操作地点
     */
    @TableField(value = "log_location")
    private String logLocation;

    /**
     * 请求参数
     */
    @TableField(value = "log_param")
    private String logParam;

    /**
     * 返回参数
     */
    @TableField(value = "json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(value = "log_time")
    private LocalDateTime logTime;

    /**
     * 消耗时间
     */
    @TableField(value = "cost_time")
    private Long costTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}