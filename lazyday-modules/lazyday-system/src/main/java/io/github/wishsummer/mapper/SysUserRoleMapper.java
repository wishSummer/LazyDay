package io.github.wishsummer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.wishsummer.domain.SysRoleObject;
import io.github.wishsummer.domain.SysUserRoleObject;

import java.util.List;

/**
* @author wishSummer
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2024-10-09 20:51:46
* @Entity io.github.wishsummer.domain.SysUserRoleObject
*/
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleObject> {

    List<SysRoleObject> selectSysUserRoleObject(Integer userId);

}




