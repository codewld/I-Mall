package pers.codewld.imall.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * JWT 工具类类
 * </p>
 *
 * @author codewld
 * @since 2022-02-06
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret; // 密钥

    @Value("${jwt.expiration}")
    private Long expiration; // 过期时长，以秒为单位

    @Autowired
    JWTVerifier JWTVerifier; // JWT校验器

    /**
     * 签发
     */
    public String sign(MyUserDetails myUserDetails) {
        return JWT.create()
                .withAudience(myUserDetails.getId().toString(), myUserDetails.getUsername())
                .withClaim("roles", myUserDetails.getRoleCodeList())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration * 1000))
                .sign(algorithm());
    }

    /**
     * 判断JWT是否无效
     */
    public boolean isInvalid(String token) {
        try {
            if (token == null) {
                return true;
            }
            DecodedJWT decodedJWT = JWTVerifier.verify(token);
            // 检查过期时间
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return true;
            }
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * 解密
     */
    public MyUserDetails decode(String token) {
        if (isInvalid(token)) {
            return null;
        }
        // 提取信息
        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = Long.valueOf(decodedJWT.getAudience().get(0));
        String username = decodedJWT.getAudience().get(1);
        List<String> roleCodeList = decodedJWT.getClaim("roles").asList(String.class);
        // 重新组合为对象
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setId(id);
        myUserDetails.setUsername(username);
        myUserDetails.setRoleCodeList(roleCodeList);
        return myUserDetails;
    }

    /**
     * 加密/解密算法
     */
    Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    /**
     * JWT校验器
     */
    @Bean
    JWTVerifier JWTVerifier() {
        return JWT.require(algorithm()).build();
    }

}
