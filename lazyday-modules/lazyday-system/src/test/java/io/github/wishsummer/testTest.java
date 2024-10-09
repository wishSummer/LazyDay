package io.github.wishsummer;

import io.github.wishsummer.domain.SysUserObject;
import io.github.wishsummer.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class testTest {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Test
    void test() {

        SysUserObject sysUserObject = sysUserMapper.selectByUsername("wangfc");

        System.out.println(sysUserObject);

    }


}