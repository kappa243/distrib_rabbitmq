package pl.edu.agh.idec.cosmic.mqtt;

import com.rabbitmq.client.Channel;
import jakarta.annotation.PreDestroy;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractQueue {
    private final BrokerConnectionManager connectionManager;
    protected final Channel channel;
    
    public AbstractQueue(BrokerConnectionManager connectionManager) throws IOException {
        this.connectionManager = connectionManager;
        
        this.channel = this.connectionManager.createChannel();
    }
    
    @PreDestroy
    private void closeChannel() throws IOException, TimeoutException {
        channel.close();
    }
}
