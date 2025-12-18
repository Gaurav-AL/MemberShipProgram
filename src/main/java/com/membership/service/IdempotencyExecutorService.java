package com.membership.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.membership.dao.IdempotencyStatus;
import com.membership.dao.Operations;
import com.membership.dto.IdempotentRecord;
import jakarta.transaction.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class IdempotencyExecutorService {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyExecutorService.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public <T> T execute(
            String idempotencyKey,
            Operations operation,
            String canonicalRequest,
            Supplier<T> businessLogic,
            TypeReference<T>  responseType){
        
        String requestHash = HashUtil.sha256(idempotencyKey + "|"+ operation + "|" + canonicalRequest);
        String redisCachedKey = "idem:"+idempotencyKey+":"+operation;
        String redisCachedResponse = redisTemplate.opsForValue().get(redisCachedKey);

        if(redisCachedResponse != null){
            log.info("Cache Hit found record {} with redis cache Key {}",
                            redisCachedResponse, redisCachedKey);
            IdempotentRecord record = deserialize(redisCachedResponse);

            log.info("Deserialized IdempotentRecord {} with redis cache Key {}",
                            record, redisCachedKey);

            if(!record.getRequestHash().equals(requestHash)){
                throw new IllegalStateException("Same Key with different Payload been requested");
            }

            if(record.getStatus() == IdempotencyStatus.SUCCESS){
                return deserialize(record.getResponse(), responseType);
            }

            throw new IllegalStateException("Request already In Progress");
        }

        log.info("Cache Miss record not found with redis cache Key {}",
                            redisCachedKey);
        IdempotentRecord idempotentRecord = new IdempotentRecord();
        idempotentRecord.setRequestHash(requestHash);
        idempotentRecord.setResponse(redisCachedResponse);
        idempotentRecord.setStatus(IdempotencyStatus.IN_PROGRESS);
        idempotentRecord.setCreatedAt(LocalDateTime.now());
        try {
            // mark IN_PROGRESS (atomic)
            redisTemplate.opsForValue().set(
                redisCachedKey,
                serialize(idempotentRecord),
                Duration.ofMinutes(10)
            );
            log.info("idempotentRecord :{}, serialize(idempotentRecord) :{}", idempotentRecord, serialize(idempotentRecord));
            T result = businessLogic.get();
            idempotentRecord.setStatus(IdempotencyStatus.SUCCESS);
            idempotentRecord.setResponse(serialize(result));
            idempotentRecord.setCreatedAt(LocalDateTime.now());
            // save response
            redisTemplate.opsForValue().set(
                redisCachedKey,
                serialize(idempotentRecord),
                Duration.ofHours(24)
            );
            log.info("Saved idempotentRecord :{}, serialize(idempotentRecord) :{}", idempotentRecord, serialize(idempotentRecord));
            return result;

        } catch (Exception e) {
            redisTemplate.delete(redisCachedKey);
            throw e;
        }
    } 

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Serialization failed", e);
        }
    }

    private IdempotentRecord deserialize(String value) {
        try {
            return objectMapper.readValue(value, IdempotentRecord.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T deserialize(String value, TypeReference<T>  type) {
        try {
            return objectMapper.readValue(value, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
