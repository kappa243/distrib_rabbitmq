package pl.edu.agh.idec.cosmic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.idec.cosmic.mqtt.AbstractQueue;
import pl.edu.agh.idec.cosmic.mqtt.BrokerConnectionManager;
import pl.edu.agh.idec.cosmic.mqtt.IQueueConsumer;
import pl.edu.agh.idec.cosmic.mqtt.ReceiveCallback;

import java.io.IOException;
import java.util.UUID;

@Component
public class ResponseQueue extends AbstractQueue implements IQueueConsumer {
    
    private final String QUEUE_NAME;
    
    @Autowired
    public ResponseQueue(BrokerConnectionManager connectionManager) throws IOException {
        super(connectionManager);
        
        QUEUE_NAME = "producer-" + UUID.randomUUID();
//        QUEUE_NAME = "tasks";
        
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
    
    public String getProducerID() {
        return QUEUE_NAME;
    }
}
