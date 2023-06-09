package pl.edu.agh.idec.cosmic;

import org.springframework.stereotype.Component;
import pl.edu.agh.idec.cosmic.mqtt.AbstractQueue;
import pl.edu.agh.idec.cosmic.mqtt.BrokerConnectionManager;
import pl.edu.agh.idec.cosmic.mqtt.IQueueConsumer;
import pl.edu.agh.idec.cosmic.mqtt.ReceiveCallback;

import java.io.IOException;

public class TaskQueue extends AbstractQueue implements IQueueConsumer {
    
    private String QUEUE_NAME;
    
    public TaskQueue(BrokerConnectionManager connectionManager, String queueName) throws IOException {
        super(connectionManager);
        
        QUEUE_NAME = queueName;
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }
    
    @Override
    public void addReceiveCallback(ReceiveCallback callback) {
        try {
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
                callback.process(message.getBody());
            }, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
