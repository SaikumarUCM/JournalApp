package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if(o==null){
               return null;
            }
            else{
                return mapper.readValue(o.toString(), entityClass);
            }

        } catch (Exception e) {
            log.error("Exception ", e);
            return null;

        }
    }



    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper= new ObjectMapper();
            String jsonValue= objectMapper.writeValueAsString(o);

             redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ", e);

        }
    }

}
