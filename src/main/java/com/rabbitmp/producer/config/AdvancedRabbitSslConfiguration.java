package com.rabbitmp.producer.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.amqp.autoconfigure.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Objects;

//@Configuration
public class AdvancedRabbitSslConfiguration {

   //@Bean
    public RabbitConnectionFactoryBean rabbitConnectionFactoryBean(RabbitProperties properties) throws Exception {
       final RabbitConnectionFactoryBean factoryBean = new RabbitConnectionFactoryBean();
        final PropertyMapper map = PropertyMapper.get();
        map.from(properties::determineHost).to(factoryBean::setHost);
        map.from(properties::determinePort).to(factoryBean::setPort);
        map.from(properties::determineUsername).to(factoryBean::setUsername);
        map.from(properties::determinePassword).to(factoryBean::setPassword);
        map.from(properties::determineVirtualHost).to(factoryBean::setVirtualHost);
        map.from(properties::getRequestedHeartbeat).asInt(Duration::getSeconds)
                .to(factoryBean::setRequestedHeartbeat);
        final RabbitProperties.Ssl ssl = properties.getSsl();
        map.from(properties::getConnectionTimeout).asInt(Duration::toMillis)
                .to(factoryBean::setConnectionTimeout);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

   // @Bean
    public CachingConnectionFactory cachingConnectionFactory(RabbitConnectionFactoryBean factoryBean) throws Exception {
        // Extract the fully configured com.rabbitmq.client.ConnectionFactory
       final com.rabbitmq.client.ConnectionFactory nativeFactory = factoryBean.getObject();
        final PropertyMapper map = PropertyMapper.get();
        
        // Wrap it in Spring's CachingConnectionFactory
       final CachingConnectionFactory cachingFactory = new CachingConnectionFactory(nativeFactory);
        
        // Apply custom caching settings
       // cachingFactory.setAddresses();
        cachingFactory.setChannelCacheSize(30);
        cachingFactory.setChannelCheckoutTimeout(5000);
        
        return cachingFactory;
    }

    //@Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory cachingConnectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(cachingConnectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }
}