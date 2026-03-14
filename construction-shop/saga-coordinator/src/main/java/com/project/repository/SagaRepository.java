package com.project.repository;


import com.project.model.SagaInstance;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SagaRepository {

    private static final String KEY_PREFIX = "saga:";

    private final RedisTemplate<String, Object> redisTemplate;

    public SagaRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(SagaInstance saga) {
        redisTemplate.opsForValue().set(KEY_PREFIX + saga.getOrderId(), saga);
    }

    public SagaInstance findByOrderId(Long orderId) {
        Object obj = redisTemplate.opsForValue().get(KEY_PREFIX + orderId);
        return obj != null ? (SagaInstance) obj : null;
    }
}
