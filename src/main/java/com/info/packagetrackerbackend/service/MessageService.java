package com.info.packagetrackerbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.packagetrackerbackend.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private static final Logger logger = LogManager.getLogger(MessageService.class);

    private RabbitTemplate template;

    public MessageService(RabbitTemplate template) {
        this.template = template;
    }

    public void sendOrderMessage(Order order) {
        String messageJson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            messageJson = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            logger.info("JsonProcessingException", e);
        }
        template.convertAndSend("amq.topic", "message." + order.getCode(), messageJson);
    }

}
