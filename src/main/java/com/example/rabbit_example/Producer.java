package com.example.rabbit_example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {


    public static void produce(String queueName, String message) {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("localhost");


        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(queueName, false, false, false, null);

            // kanal uzerinden artiq data-ni uygun siraya gondere bilerik.


            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));


            System.err.println("*MESSAGE ELAVE EDILDI*");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (TimeoutException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }

    public static void main(String[] args) {

        while (true) {
            System.err.print("enter your message : ");
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            Producer.produce("sira3", msg);
        }
    }

}
