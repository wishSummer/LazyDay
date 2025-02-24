package io.github.wishsummer.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.wishsummer.api.domain.SysMenuObject;
import io.github.wishsummer.api.domain.SysRoleMenuObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wishSummer
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Mapper
 * @createDate 2024-10-09 20:57:48
 * @Entity io.github.wishsummer.domain.SysRoleMenuObject
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuObject> {

    List<SysMenuObject> selectMenusByRoleId(@Param("roleIds")List<Integer> roleIds);
}




