package io.github.wishsummer.mapper;

import io.github.wishsummer.domain.SysUserObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author wishSummer
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2024-10-09 20:39:10
* @Entity io.github.wishsummer.domain.SysUserObject
*/
public interface SysUserMapper extends BaseMapper<SysUserObject> {

    SysUserObject selectByUsername(String username);

}




