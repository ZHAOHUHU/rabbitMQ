package teamway.shenzhen.basic2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    /*
    实现任务的分发
     */
    private  static  final  String QUEUE_NAME=  "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
       factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //分发消息
        for (int i = 0; i <10 ; i++) {
            String msg="报数："+i;
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("客户端发送的消息是"+msg);
        }
        channel.close();
        connection.close();
    }
}
