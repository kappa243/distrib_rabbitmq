package pl.edu.agh.idec.cosmic.mqtt;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class BrokerConnectionManager {
    
    private final String HOST = "localhost";
    
    private final Connection connection;
    
    public BrokerConnectionManager() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        
        this.connection = factory.newConnection();
    }
    
    public Channel createChannel() throws IOException {
        return connection.createChannel();
    }
    
    @PreDestroy
    private void closeConnection() throws IOException {
        connection.close();
    }
}
