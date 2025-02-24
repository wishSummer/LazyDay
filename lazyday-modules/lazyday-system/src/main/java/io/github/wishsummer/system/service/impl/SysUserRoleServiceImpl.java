package io.github.wishsummer.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.api.domain.SysUserRoleObject;
import io.github.wishsummer.system.mapper.SysUserRoleMapper;
import io.github.wishsummer.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author wishSummer
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
 * @createDate 2024-10-09 20:51:46
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleObject>
        implements IService<SysUserRoleObject>, SysUserRoleService {

}




