package io.github.wishsummer.system.domain.vo;

import io.github.wishsummer.api.model.LoginUser;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: UserInfoVo.java, 2025/2/21 下午3:55 $
 */
@Data
public class UserInfoVo {

    /**
     * 菜单权限
     */
    private List<String> menuList;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;
    /**
     * 头像地址
     */
    private String avatar;

    public UserInfoVo build(LoginUser loginUser) {
        // 填充基本用户信息
        this.setUserId(loginUser.getSysUserObject().getUserId());
        this.setUsername(loginUser.getSysUserObject().getUsername());
        this.setNickName(loginUser.getSysUserObject().getNickName());
        this.setEmail(loginUser.getSysUserObject().getEmail());
        this.setPhoneNumber(loginUser.getSysUserObject().getPhoneNumber());
        this.setSex(loginUser.getSysUserObject().getSex());
        this.setAvatar(loginUser.getSysUserObject().getAvatar());

        // 默认菜单权限为空 TODO 按钮权限前端如何判定、前端路由都需要那些菜单参数；
        this.setMenuList(null);
        return this;
    }

}
