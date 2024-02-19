package com.hustleknight.consumer.redis

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hustleknight.consumer.dto.BattleInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun battleRedisTemplate(
        connectionFactory: RedisConnectionFactory
    ): RedisTemplate<Long, BattleInfo> {
        val template = RedisTemplate<Long, BattleInfo>()

        template.connectionFactory = connectionFactory
        template.keySerializer = GenericToStringSerializer(Long::class.java)
        template.valueSerializer = Jackson2JsonRedisSerializer(jacksonObjectMapper(), BattleInfo::class.java)

        return template
    }

    @Bean
    fun emitterRedisTemplate(
        connectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Boolean>{
        val template = RedisTemplate<String, Boolean>()
        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericToStringSerializer(Boolean::class.java)

        return template
    }
}