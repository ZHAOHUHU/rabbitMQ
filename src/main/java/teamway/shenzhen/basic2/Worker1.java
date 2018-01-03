package teamway.shenzhen.basic2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker1 {
    private static  final  String QUEUE_NAME="task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //每次从队列中获取的数量
        channel.basicQos(1);
        final Consumer  consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("客户端收到的是"+message);
                try {
                    throw new Exception();
                }catch (Exception e){
                    channel.abort();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck=false;
        //消费完成确认
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
