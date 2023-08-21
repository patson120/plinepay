package com.plinepay.authservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.plinepay.authservice.otp.OptData;

@Repository
public class OptDataDaoImpl implements OptDataDao {

    private static final Object HASH_KEY = "OTP";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public OptData saveOptData(OptData otp) {
        template.opsForHash().put(HASH_KEY, otp.getUserEmail(), otp);
        return otp;
    }

    public List<OptData> findAllOptData() {
        return template.opsForHash().values(HASH_KEY);
    }

    public OptData findOptDataByEmail(String email) {
        return (OptData) template.opsForHash().get(HASH_KEY, email);
    }

    public String deleteOptData(String email) {
        template.opsForHash().delete(HASH_KEY, email);
        return "OTP deleted";
    }
}
