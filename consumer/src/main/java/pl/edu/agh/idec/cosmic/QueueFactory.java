package pl.edu.agh.idec.cosmic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.edu.agh.idec.cosmic.mqtt.BrokerConnectionManager;

import java.io.IOException;

@Configuration
public class QueueFactory {
    
    private final BrokerConnectionManager connectionManager;
    
    public QueueFactory(BrokerConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    @Bean(autowireCandidate = false)
    @Scope("prototype")
    public TaskQueue createTaskQueue(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") String queueName) throws IOException {
        return new TaskQueue(connectionManager, queueName);
    }
    
    @Bean(autowireCandidate = false)
    @Scope("prototype")
    public ResponseQueue createResponseQueue(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") String queueName) throws IOException {
        return new ResponseQueue(connectionManager, queueName);
    }
}
