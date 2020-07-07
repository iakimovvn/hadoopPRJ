package ru.yakimovvn.web.services.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;
import ru.yakimovvn.api.pojo.WorkflowPojo;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class RabbitMqService {


    private final RabbitAdmin rabbitAdmin;

    private final AmqpTemplate amqpTemplate;

    private final DirectExchange directExchange;



    public void createQueueAndSend(WorkflowPojo workflowPojo, String queName)  throws AmqpException {

        String routingKey = "key." + queName;
        Queue queue = createQueue(queName);
        addQueue(queue);
        addBinding(queue, directExchange, routingKey);

        sendQueueMessage(queName,workflowPojo);
    }


    public void sendMessage( WorkflowPojo object, String queName) throws AmqpException {
        if (rabbitAdmin.getQueueInfo(queName) != null) {
            sendQueueMessage(queName, object);
        } else {
            createQueueAndSend(object, queName);
        }

    }


    public  void sendQueueMessage(String routingKey, WorkflowPojo object){

        amqpTemplate.convertAndSend(routingKey,object);
    }

    public  void sentExchangeMessage(String exchange, String routingKey, Object object){

        amqpTemplate.convertAndSend(exchange,routingKey,object);
    }

    private String addQueue(Queue queue) {
        return rabbitAdmin.declareQueue(queue);
    }


    private void addBinding(Queue queue, DirectExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }


    private Queue createQueue(String queueName) {
        return QueueBuilder.durable(queueName).build();
    }


}
