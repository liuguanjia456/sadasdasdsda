package com.bjpowernode.activemq.send;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ClassName:QueueSender
 * Package:com.bjpowernode.activemq.send
 * Description:
 *
 * @date:2019/3/4 15:45
 * @author:Felix
 */
//发送点对点消息
public class QueueSender {

    public static String BROKER_URL = "failover:(tcp://192.168.121.128:61617,tcp://192.168.121.128:61618,tcp://192.168.121.128:61619)";
    private static String DESTINATION = "myQueue";

    public static final String USER_NAME="system";
    public static final String PASSWORD="123456";

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
            /*
            //文本类型消息
            Message message = session.createTextMessage("hello,activeMQ");
            */
            /*
            //对象类型消息
            User user = new User();
            user.setId(100);
            user.setName("bjpowernode");
            user.setAge(10);
            Message message = session.createObjectMessage(user);
            */
            /*
            //Map映射消息
            MapMessage message = session.createMapMessage();
            message.setInt("age",10);
            message.setString("school","bjpowernode");
            */
            /*
            //字节消息
            BytesMessage message = session.createBytesMessage();
            message.writeBoolean(true);
            message.writeUTF("wkcto");
            */
            //流类型消息
            StreamMessage message = session.createStreamMessage();
            message.writeLong(1000L);
            message.writeString("哈哈");

            //设置消息标记
            //message.setIntProperty("version",1);
            //message.setStringProperty("author","felix");

            //5.创建目的地对象
            Destination destination = session.createQueue(DESTINATION);
            //6.创建消息的生产者（发送者）
            messageProducer = session.createProducer(destination);
            while(true){
                //7.发送消息
                messageProducer.send(message);
                //提交消息
                //session.commit();
            }
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
