package ru.yakimov.web.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
@Configuration
@Slf4j
public class RabbitMQConfig {

    String host;
    String port;
    String username;
    String password;



    @Bean("mqConnectionFactory")
    public ConnectionFactory mqConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setChannelCacheSize(40);

        connectionFactory.setAddresses(host);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin( ) {

        return new RabbitAdmin(mqConnectionFactory());
    }



}