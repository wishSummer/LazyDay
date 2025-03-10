package io.github.wishsummer.system.controller;

import io.github.wishsummer.api.domain.SysLogObject;
import io.github.wishsummer.common.core.domain.Result;
import io.github.wishsummer.system.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("log")
public class SysLogController {

    private SysLogService sysLogService;

    @PostMapping("/insert")
    public Result<String> insertLog(@RequestBody SysLogObject sysLogObject) {
        if (sysLogService.save(sysLogObject)) {
            return Result.success();
        }
        return Result.error("日志存储失败");
    }

    @Autowired
    public void setService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }
}
