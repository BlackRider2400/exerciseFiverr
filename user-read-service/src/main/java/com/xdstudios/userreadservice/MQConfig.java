package com.xdstudios.userreadservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE_TO_CLIENT = "queue_to_client_read";
    public static final String QUEUE_TO_MANAGEMENT = "queue_read_to_main";
    public static final String QUEUE_FROM_CLIENT = "queue_from_read_client";
    public static final String QUEUE_FROM_MANAGEMENT = "queue_main_to_read";
    public static final String EXCHANGE = "main";

    @Bean
    public Queue queueToClient(){
        return new Queue(QUEUE_TO_CLIENT);
    }

    @Bean
    public Queue queueFromClient(){
        return new Queue(QUEUE_FROM_CLIENT);
    }

    @Bean
    public Queue queueToManagement(){
        return new Queue(QUEUE_TO_MANAGEMENT);
    }

    @Bean
    public Queue queueFromManagement(){
        return new Queue(QUEUE_FROM_MANAGEMENT);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(TopicExchange exchange){
        return BindingBuilder
                .bind(queueFromClient())
                .to(exchange)
                .with(queueFromClient().getName())
        ;
    }

    @Bean
    public Binding binding2(TopicExchange exchange){
        return BindingBuilder
                .bind(queueToClient())
                .to(exchange)
                .with(queueToClient().getName())
        ;
    }

    @Bean
    public Binding binding3(TopicExchange exchange){
        return BindingBuilder
                .bind(queueToManagement())
                .to(exchange)
                .with(queueToManagement().getName())
        ;
    }

    @Bean
    public Binding binding4(TopicExchange exchange){
        return BindingBuilder
                .bind(queueFromManagement())
                .to(exchange)
                .with(queueFromManagement().getName())
        ;
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
