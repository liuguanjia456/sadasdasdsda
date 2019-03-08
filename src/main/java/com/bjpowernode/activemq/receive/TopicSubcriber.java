package com.bjpowernode.activemq.receive;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ClassName:TopicSubcriber
 * Package:com.bjpowernode.activemq.receive
 * Description:
 *
 * @date:2019/3/4 16:28
 * @author:Felix
 */
//接收发布/订阅消息
public class TopicSubcriber {
    private static String BROKER_URL = "tcp://192.168.144.128:61616";

    private static String DESTINATION = "myTopic";

    public static final String USER_NAME = "system";
    public static final String PASSWORD = "123456";


    public static void main(String[] args) {
        receiveMessage();
    }

    public static void receiveMessage() {
        Connection connection = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,BROKER_URL);
            //2.创建连接
            connection = connectionFactory.createConnection();
            //消息的消费者，必须显示调用start方法之后，才会对消息进行消费
            connection.start();
            //3.创建session
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地对象
            Destination destination = session.createTopic(DESTINATION);
            //5.创建消息的接收者
            messageConsumer = session.createConsumer(destination);
            //6.消费消息
            Message message = messageConsumer.receive();

            //7.判断接收到的消息类型
            if(message instanceof TextMessage){
                String text = ((TextMessage) message).getText();
                System.out.println("接收到的消息为：" + text);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if(messageConsumer != null){
                    messageConsumer.close();
                }
                if(session != null){
                    session.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
