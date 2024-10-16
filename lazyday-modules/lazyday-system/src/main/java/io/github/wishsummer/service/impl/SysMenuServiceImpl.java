package io.github.wishsummer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wishsummer.domain.SysMenuObject;
import io.github.wishsummer.service.SysMenuService;
import io.github.wishsummer.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author wishSummer
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2024-10-09 20:57:48
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuObject>
    implements SysMenuService{

}




