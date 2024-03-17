package io.github.wishsummer.controller;

import io.github.wishsummer.annotation.WebLog;
import io.github.wishsummer.domain.Result;
import io.github.wishsummer.enums.BusinessTypeEnum;
import io.github.wishsummer.service.SysLogService;
import io.github.wishsummer.domain.SysLogObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("log")
public class SysLogController {

    private SysLogService service;

    @GetMapping("/test")
    @WebLog(title = "测试", businessType = BusinessTypeEnum.OTHER)
    public Result test(String a) {
        return Result.success();
    }

    @PostMapping("/insert")
    public Result insertLog(@RequestBody SysLogObject sysLogObject) {
        if (service.save(sysLogObject)) {
            return Result.success();
        }
        return Result.error("日志存储失败");
    }

    @Autowired
    public void setService(SysLogService service) {
        this.service = service;
    }
}
