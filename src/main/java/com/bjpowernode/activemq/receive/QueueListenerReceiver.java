package com.bjpowernode.activemq.receive;

import com.bjpowernode.activemq.model.User;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:QueueReceiver
 * Package:com.bjpowernode.activemq.receive
 * Description:
 *
 * @date:2019/3/4 16:04
 * @author:Felix
 */
//使用监听器异步接收接收点对点消息
public class QueueListenerReceiver {

    private static String BROKER_URL = "tcp://192.168.144.128:61616";

    private static String DESTINATION = "myQueue";

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
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER_NAME,PASSWORD,BROKER_URL);
            List<String> trustedPackages = new ArrayList<String>();
            trustedPackages.add("com.bjpowernode.activemq.model");
            //设置受信任的包
            connectionFactory.setTrustedPackages(trustedPackages);

            //2.创建连接
            connection = connectionFactory.createConnection();
            //消息的消费者，必须显示调用start方法之后，才会对消息进行消费
            connection.start();
            //3.创建session
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            //4.创建目的地对象
            Destination destination = session.createQueue(DESTINATION);
            //5.创建消息的接收者
            /*String selector = "author='felix' and version = 1";
            messageConsumer = session.createConsumer(destination,selector);*/
            messageConsumer = session.createConsumer(destination);
            //6.消费消息
            messageConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message){
                    try {
                        //7.判断接收到的消息类型
                        if(message instanceof TextMessage){
                            String text = ((TextMessage) message).getText();
                            System.out.println("接收到的消息为：" + text);
                        }else if(message instanceof ObjectMessage){
                            User user = (User) ((ObjectMessage) message).getObject();
                            System.out.println("接收到的用户名字为：" + user.getName() +",年龄：" + user.getAge());
                        }else if(message instanceof MapMessage){
                            System.out.println("学校" + ((MapMessage) message).getString("school")
                                    +",年龄：" + ((MapMessage) message).getInt("age"));
                        }else if(message instanceof BytesMessage){
                            boolean flag = ((BytesMessage) message).readBoolean();
                            String text = ((BytesMessage) message).readUTF();
                            System.out.println(text + "::::" + flag);
                        }else if(message instanceof StreamMessage){
                            Long lo = ((StreamMessage) message).readLong();
                            String s = ((StreamMessage) message).readString();
                            System.out.println(s + "::::" + lo);
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
