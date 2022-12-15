package com.example.rabbit_example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void consume(String queueName) {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");

        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(queueName, false, false, false, null);


            System.err.println("********Messages**********");
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(
                        String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body) throws IOException {

                    String message = new String(body, "UTF-8");
                    System.err.println(message);
                }
            };

            channel.basicConsume(queueName, true, consumer);
            channel.basicQos(1);


        } catch (IOException e) {
            System.err.println(e);
        } catch (TimeoutException e) {
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {

        while (true) {
            System.err.print("enter any key...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            Consumer.consume("sira3");
        }
    }
}
