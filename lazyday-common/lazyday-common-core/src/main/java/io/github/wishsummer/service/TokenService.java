package io.github.wishsummer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: TokenService.java, 2024/1/5 16:58 $
 */
@Component
public class TokenService {

    private RedisService redisService;

    /**
     * 创建令牌
     */
    public Map<String,Object> createToken(){

        return null;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }
}
