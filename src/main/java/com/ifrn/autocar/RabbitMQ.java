package com.ifrn.autocar;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitMQ {
    private final static String QUEUE_NAME = "thiago";

    public static void escreverMensagem(String message){
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri("amqp://10.209.2.162:5672");
            factory.setUsername("admin");
            factory.setPassword("admin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("MENSAGEM ENVIADA = '" + message + "'");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void lerMensagem() throws IOException {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;

        String QUEUE_NAME1 = "teste";
        String EXCHANGE_NAME1 = "teste";
        try {
            factory = new ConnectionFactory();
            factory.setUri("amqp://10.209.2.162:5672");
            factory.setUsername("admin");
            factory.setPassword("admin");

            connection = factory.newConnection();
            channel = connection.createChannel();

            //declaring exchanges
            channel.exchangeDeclare(EXCHANGE_NAME1, "fanout", true, false, null);

            //declaring queues
            channel.queueDeclare(QUEUE_NAME1, true, false, false, null);

            //binding queues to exchanges
            channel.queueBind(QUEUE_NAME1, EXCHANGE_NAME1, "");

            DeliverCallback deliverCallbackQuestion = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("MENSAGEM RECEBIDA = '" + message + "'");
            };

            channel.basicConsume(QUEUE_NAME1, true, deliverCallbackQuestion, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}