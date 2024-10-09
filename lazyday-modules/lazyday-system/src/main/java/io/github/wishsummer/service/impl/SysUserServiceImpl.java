package io.github.wishsummer.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.mapper.SysUserMapper;
import io.github.wishsummer.model.LoginUser;
import io.github.wishsummer.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserObject>
        implements IService<SysUserObject>, SysUserService {

    @Override
    public LoginUser getUserInfo(String username) {
        SysUserObject sysUserObject = this.getBaseMapper().selectByUsername(username);
        return null;
    }
}
