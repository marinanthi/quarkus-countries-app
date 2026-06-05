package com.example.cache;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RedisCaching {

    @Inject
    RedisDataSource redis;

    Logger logger = LoggerFactory.getLogger(RedisCaching.class.getName());

    @Scheduled(every="30s")
    public void clearCache() {
        logger.info("Clearing Redis cache");
        redis.flushall();
    }
}
