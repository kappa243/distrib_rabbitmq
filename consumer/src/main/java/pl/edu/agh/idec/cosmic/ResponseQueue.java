package pl.edu.agh.idec.cosmic;

import jakarta.annotation.PreDestroy;
import pl.edu.agh.idec.cosmic.mqtt.AbstractQueue;
import pl.edu.agh.idec.cosmic.mqtt.BrokerConnectionManager;
import pl.edu.agh.idec.cosmic.mqtt.IQueueProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ResponseQueue extends AbstractQueue implements IQueueProducer {
    
    private String QUEUE_NAME;
    
    public ResponseQueue(BrokerConnectionManager connectionManager, String queueName) throws IOException {
        super(connectionManager);
        
        this.QUEUE_NAME = queueName;
        this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }
    
    @Override
    public void send(byte[] data) {
        try {
            this.channel.basicPublish("", QUEUE_NAME, null, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @PreDestroy
    private void closeChannel() throws IOException, TimeoutException {
        this.channel.close();
    }
}
