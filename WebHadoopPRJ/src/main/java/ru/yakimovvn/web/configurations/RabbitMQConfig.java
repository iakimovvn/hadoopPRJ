package ru.yakimovvn.web.configurations;

import lombok.Data;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfig {


    private String host;
    private String port;
    private String username;
    private String password;

    @Value("${hadoopprj.rabbitmq.exchange}")
    private String EXCHANGE;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }




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

        RabbitAdmin rabbitAdmin = new RabbitAdmin(mqConnectionFactory());
        rabbitAdmin.declareExchange(directExchange());
        return rabbitAdmin;
    }




}