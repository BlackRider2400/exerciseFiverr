package com.xdstudios.userclientserver;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE_TO_MANAGEMENT = "queue_from_client_management";
    public static final String QUEUE_TO_READ = "queue_from_client_read";
    public static final String QUEUE_FROM_READ = "queue_from_read_client";

    public static final String EXCHANGE = "main";

    @Bean
    public Queue queueToManagement(){
        return new Queue(QUEUE_TO_MANAGEMENT);
    }

    @Bean
    public Queue queueToRead(){
        return new Queue(QUEUE_TO_READ);
    }

    @Bean
    public Queue queueFromRead(){
        return new Queue(QUEUE_TO_READ);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding1(TopicExchange exchange){
        return BindingBuilder
                .bind(queueToManagement())
                .to(exchange)
                .with(queueToManagement().getName());
    }

    @Bean
    public Binding binding2(TopicExchange exchange){
        return BindingBuilder
                .bind(queueToRead())
                .to(exchange)
                .with(queueToRead().getName());
    }

    @Bean
    public Binding binding3(TopicExchange exchange){
        return BindingBuilder
                .bind(queueFromRead())
                .to(exchange)
                .with(queueFromRead().getName());
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
