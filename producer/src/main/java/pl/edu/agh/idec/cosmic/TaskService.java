package pl.edu.agh.idec.cosmic;

import org.springframework.stereotype.Service;
import pl.edu.agh.idec.cosmic.service.ServiceResponse;
import pl.edu.agh.idec.cosmic.service.ServiceTask;
import pl.edu.agh.idec.cosmic.service.ServiceType;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Service
public class TaskService {
    
    private final ResponseQueue responseQueue;
    
    private final HashMap<ServiceType, TaskQueue> taskQueues;
    private final QueueFactory queueFactory;
    
    public TaskService(ResponseQueue responseQueue, QueueFactory queueFactory) throws IOException {
        this.responseQueue = responseQueue;
        this.queueFactory = queueFactory;
        
        
        taskQueues = new HashMap<>();
        for (ServiceType type : ServiceType.values()) {
            taskQueues.put(type, this.queueFactory.createQueue("tasks-" + type.name()));
        }
        
        responseQueue.addReceiveCallback(data -> {
            
            ServiceResponse response;
            try {
                response = (ServiceResponse) ObjectConverter.bytesToObject(data);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            
            String msg = "Received task response from: " +
                response.getConsumer_id() + "\n" +
                "task_id: " +
                response.getTask_id() + "\n" +
                "service_type: " +
                response.getServiceType().name() + "\n";
            
            System.out.println(msg);
        });
        
    }
    
    public void sendTask(ServiceType type) {
        ServiceTask task = new ServiceTask(
            responseQueue.getProducerID(),
            UUID.randomUUID().toString(),
            type
        );
        
        byte[] objectBytes;
        try {
            objectBytes = ObjectConverter.objectToBytes(task);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        taskQueues.get(type).send(objectBytes);
    }
    
}
