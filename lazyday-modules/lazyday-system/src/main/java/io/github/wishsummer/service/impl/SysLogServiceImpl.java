package io.github.wishsummer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.domain.SysLogObject;
import io.github.wishsummer.mapper.SysLogMapper;
import io.github.wishsummer.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * @author w7562
 * @description 针对表【sys_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2023-12-23 20:06:41
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogObject>
        implements SysLogService {

    @Override
    public boolean save(SysLogObject entity) {
        return super.save(entity);
    }
}




