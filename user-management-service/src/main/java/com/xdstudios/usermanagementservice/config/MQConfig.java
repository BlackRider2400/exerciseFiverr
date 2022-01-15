package com.xdstudios.usermanagementservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String QUEUE_READ_TO_MAIN = "queue_read_to_main";
    public static final String QUEUE_MAIN_TO_READ = "queue_main_to_read";
    public static final String QUEUE_FROM_CLIENT = "queue_from_client_management";
    public static final String EXCHANGE = "main";

    @Bean
    public Queue queueReadToMain(){
        return new Queue(QUEUE_READ_TO_MAIN);
    }

    @Bean
    public Queue queueMainToRead(){
        return new Queue(QUEUE_MAIN_TO_READ);
    }

    @Bean
    public Queue queueFromClient(){
        return new Queue(QUEUE_FROM_CLIENT);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding1(TopicExchange exchange){
        return BindingBuilder
                .bind(queueReadToMain())
                .to(exchange)
                .with(queueReadToMain().getName());
    }

    @Bean
    public Binding binding2(TopicExchange exchange){
        return BindingBuilder
                .bind(queueMainToRead())
                .to(exchange)
                .with(queueMainToRead().getName());
    }

    @Bean
    public Binding binding3(TopicExchange exchange){
        return BindingBuilder
                .bind(queueFromClient())
                .to(exchange)
                .with(queueFromClient().getName());
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
