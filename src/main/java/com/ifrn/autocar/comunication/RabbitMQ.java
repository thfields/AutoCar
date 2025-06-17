package com.ifrn.autocar.comunication;

import com.ifrn.autocar.models.Log;
import com.ifrn.autocar.repositories.LogRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RabbitMQ {
    private final static String QUEUE_NAME = "Thiago";

    @Autowired
    private LogRepository logRepository;

    public void escreverMensagem(String message) {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri("amqp://10.209.2.162:5672");
            factory.setUsername("admin");
            factory.setPassword("admin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            if (logRepository != null) {
                logRepository.save(new Log("ENVIO", message, QUEUE_NAME));
            }

            System.out.println("MENSAGEM ENVIADA = '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String lerMensagem() throws IOException {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;

        String QUEUE_NAME1 = "Paulo";
        String EXCHANGE_NAME1 = "Paulo";
        final String[] resposta = {""};

        try {
            factory = new ConnectionFactory();
            factory.setUri("amqp://10.209.2.162:5672");
            factory.setUsername("admin");
            factory.setPassword("admin");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME1, "fanout", true, false, null);
            channel.queueDeclare(QUEUE_NAME1, true, false, false, null);
            channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME1, "");

            DeliverCallback deliverCallbackQuestion = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                resposta[0] = message;

                if (logRepository != null) {
                    logRepository.save(new Log("RECEBIMENTO", message, QUEUE_NAME1));
                }

                System.out.println("MENSAGEM RECEBIDA = '" + message + "'");
            };

            channel.basicConsume(QUEUE_NAME1, true, deliverCallbackQuestion, consumerTag -> {});
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resposta[0];
    }

    public LogRepository getLogRepository() {
        return logRepository;
    }
}