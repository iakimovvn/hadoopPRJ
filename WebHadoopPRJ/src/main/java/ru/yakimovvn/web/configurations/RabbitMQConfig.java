package ru.yakimovvn.web.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    private String host;
    private String port;
    private String username;
    private String password;

//    @Value("${hadoopprj.rabbitmq.exchange}")
    private final String EXCHANGE = "hadoop-prj.exchange";

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