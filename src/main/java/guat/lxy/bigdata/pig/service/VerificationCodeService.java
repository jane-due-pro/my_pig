package guat.lxy.bigdata.pig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final long CODE_EXPIRE_TIME = 1;
    public String generateAndSaveCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        String redisKey = "email_code:" + email;
        redisTemplate.opsForValue().set(redisKey, code, CODE_EXPIRE_TIME, TimeUnit.MINUTES);
        return code;
    }
    public boolean verifyCode(String email, String inputCode) {
        String redisKey = "email_code:" + email;
        String savedCode = redisTemplate.opsForValue().get(redisKey);
        return savedCode != null && savedCode.equals(inputCode) && redisTemplate.delete(redisKey);
    }
}