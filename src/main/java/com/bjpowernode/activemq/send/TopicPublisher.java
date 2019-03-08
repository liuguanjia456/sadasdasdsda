package com.bjpowernode.activemq.send;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ClassName:TopicPublisher
 * Package:com.bjpowernode.activemq.send
 * Description:
 *
 * @date:2019/3/4 16:24
 * @author:Felix
 */
//发送发布/订阅消息
public class TopicPublisher {
    public static String BROKER_URL = "tcp://192.168.144.128:61616";
    private static String DESTINATION = "myTopic";

    public static final String USER_NAME = "system";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) {
        sendMessage();
    }

    public static void sendMessage() {

        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;

        try {
            //1.创建连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,BROKER_URL);
            //2.创建连接
            connection = connectionFactory.createConnection();
            //3.创建session   第一个参数：表示是否是事务消息    第二个参数：消息的确认机制
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            //4.创建消息对象
            Message message = session.createTextMessage("hello,activeMQ TOPIC");
            //5.创建目的地对象
            Destination destination = session.createTopic(DESTINATION);
            //6.创建消息的生产者（发送者）
            messageProducer = session.createProducer(destination);
            //7.发送消息
            messageProducer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if(messageProducer != null){
                    messageProducer.close();
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
