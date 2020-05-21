package ru.yakimov.web.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.yakimov.web.persistence.entities.Wfl_type;
import ru.yakimov.web.persistence.entities.Workflow;
import ru.yakimov.web.persistence.pojo.WorkflowPojo;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class RabbitMqService {


    private final RabbitAdmin rabbitAdmin;

    private final RabbitTemplate rabbitTemplate;

    private final AmqpTemplate amqpTemplate;
//    @Value("${hadoopprj.rabbitmq.exchange}")
    private final String EXCHANGE = "hadoop-prj.exchange";


    @Bean
    public AmqpTemplate amqpTemplate() {

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }



    public void createQueueAndSend(WorkflowPojo workflowPojo, String queName)  throws AmqpException {

        DirectExchange exchange = createExchange(EXCHANGE);
        String routingKey = "key." + queName;
        addExchange(exchange);
        Queue queue = createQueue(queName);
        addQueue(queue);
        addBinding(queue, exchange, routingKey);

        sendQueueMessage(queName,workflowPojo);
    }


    public void sendMessage( WorkflowPojo object, String queName) throws AmqpException {
        if (rabbitAdmin.getQueueInfo(queName) != null) {
            sendQueueMessage(queName, object);
        } else {
            createQueueAndSend(object, queName);
        }

    }

    /**
     * Send messages based on que and rout
     * @param routingKey
     * @param object
     */
    public  void sendQueueMessage(String routingKey, WorkflowPojo object){

        rabbitTemplate.convertAndSend(routingKey,object);
    }

    /**
     * sent according to exhange and rout
     * @param exchange
     * @param routingKey
     * @param object
     */
    public  void sentExchangeMessage(String exchange, String routingKey, Object object){

        rabbitTemplate.convertAndSend(exchange,routingKey,object);
    }

    /**
     * Create Exchange
     *
     * @param exchange
     */
    private void addExchange(AbstractExchange exchange) {
        rabbitAdmin.declareExchange(exchange);
    }


    /**
     * Create a specified Queue
     *
     * @param queue
     * @return queueName
     */
    private String addQueue(Queue queue) {
        return rabbitAdmin.declareQueue(queue);
    }

    /**
     * Bind a queue to a matching switch using a routingKey
     *
     * @param queue
     * @param exchange
     * @param routingKey
     */
    private void addBinding(Queue queue, DirectExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }


    private Queue createQueue(String queueName) {
        return QueueBuilder.durable(queueName).build();
    }


    private DirectExchange createExchange(String exchangeName) {
        return new DirectExchange(exchangeName, true, false);
    }
}
