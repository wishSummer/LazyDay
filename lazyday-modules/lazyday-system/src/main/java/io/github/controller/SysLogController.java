package io.github.controller;

import io.github.domain.Result;
import io.github.domain.SysLogObject;
import io.github.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("log")
public class SysLogController {

    private SysLogService service;

    @PostMapping("/insert")
    public Result insertLog(SysLogObject sysLogObject) {
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
