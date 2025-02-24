package io.github.wishsummer.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.api.domain.SysLogObject;
import io.github.wishsummer.system.service.SysLogService;
import io.github.wishsummer.system.mapper.SysLogMapper;
import org.springframework.stereotype.Service;

/**
 * @author w7562
 * @description 针对表【sys_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2023-12-23 20:06:41
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogObject>
        implements SysLogService {

}




