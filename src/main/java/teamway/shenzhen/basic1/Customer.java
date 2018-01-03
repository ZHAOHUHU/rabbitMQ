package teamway.shenzhen.basic1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {
    public final static String QUEUE_NAME = "rabbitMQ.test";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        final ConnectionFactory factory = new ConnectionFactory();
        //设置rabbitmq的相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        final Connection connection = factory.newConnection();
        //创建通道
        final Channel channel = connection.createChannel();
        /*
        声明要关注的队列
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //消费者
        /*
        defauleconsumer类实现了consumer接口，通过传入一个频道，
        告诉我们需要那个频道的消息如果频道中有消息就会执行回掉函数handledelivery
         */
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            /*
            前面代码我们可以看出和生成者一样的，后面的是获取生产者发送的信息，
            其中envelope主要存放生产者相关信息（比如交换机、路由key等）body是消息实体
             */
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("客户端收到的消息是" + msg);
            }
        };
        //自动回复队列应答，rabbitmq的消息确认机制
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
