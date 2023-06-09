package pl.edu.agh.idec.cosmic;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.edu.agh.idec.cosmic.service.ServiceResponse;
import pl.edu.agh.idec.cosmic.service.ServiceTask;
import pl.edu.agh.idec.cosmic.service.ServiceType;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TaskService {
    
    private final HashMap<ServiceType, TaskQueue> taskQueues;
    private final QueueFactory queueFactory;
    private final String id;
    
    private final Environment env;
    
    public TaskService(QueueFactory queueFactory, Environment env) throws IOException {
        this.queueFactory = queueFactory;
        this.env = env;
        
        id = "consumer-" + UUID.randomUUID();
        
        taskQueues = new HashMap<>();
        List<ServiceType> serviceTypes = Arrays.stream(Objects.requireNonNull(env.getProperty("services", String[].class))).map(ServiceType::valueOf).toList();
        for (ServiceType type : serviceTypes) {
            TaskQueue taskQueue = this.queueFactory.createTaskQueue("tasks-" + type.name());
            taskQueue.addReceiveCallback((data -> {
                // receiving task
                ServiceTask task;
                try {
                    task = (ServiceTask) ObjectConverter.bytesToObject(data);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String msg = "Received task from: " +
                    task.getProducer_id() + "\n" +
                    "task_id: " +
                    task.getTask_id() + "\n" +
                    "service_type: " +
                    task.getServiceType().name() + "\n";
                System.out.println(msg);
                
                // sending response (finished task)
                ServiceResponse serviceResponse = new ServiceResponse(
                    id,
                    task.getTask_id(),
                    type
                );
                
                ResponseQueue responseQueue;
                try {
                    responseQueue = this.queueFactory.createResponseQueue(task.getProducer_id());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                byte[] objectBytes;
                try {
                    objectBytes = ObjectConverter.objectToBytes(serviceResponse);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                System.out.println("Sending response to: " + task.getProducer_id());
                responseQueue.send(objectBytes);
            }));
            
            taskQueues.put(type, taskQueue);
        }
    }
}
