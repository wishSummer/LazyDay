package io.github.wishsummer;

import io.github.wishsummer.api.domain.SysUserObject;
import io.github.wishsummer.mapper.SysRoleMenuMapper;
import io.github.wishsummer.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class testTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;


    @Test
    void test() {

        SysUserObject sysUserObject = sysUserMapper.selectByUsername("wangfc");

        System.out.println(sysUserObject);

//        System.out.println(sysRoleMenuMapper.selectMenusByRoleId());

    }
}