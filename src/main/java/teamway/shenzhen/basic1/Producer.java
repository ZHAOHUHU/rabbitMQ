package teamway.shenzhen.basic1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public final static  String QUEUE_NAME="rabbitMQ.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        final ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq的相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        //声明一个队列
        /*
        队列名称
        是否持久化（队列在服务器重启时仍可生存）
        是否独占队列（创建者可以私有队列断开后自动删除）
        当前消费客户端断开时是否自动删除队列
        不自动
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg="hello word";
        //将消息推送到队列
        /*
        交换机（exchange名称）
        队列的映射路由key
        消息的其他属性
        发送消息的主体
         */
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
        //关闭通道和连接
        channel.close();
        connection.close();

    }
}
