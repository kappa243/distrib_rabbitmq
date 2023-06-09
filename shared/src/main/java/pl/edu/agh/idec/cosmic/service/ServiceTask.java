package pl.edu.agh.idec.cosmic.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceTask implements Serializable {
    private String producer_id;
    private String task_id;
    private ServiceType serviceType;
    
    public ServiceTask(String producer_id, String task_id, ServiceType serviceType) {
        this.producer_id = producer_id;
        this.task_id = task_id;
        this.serviceType = serviceType;
    }
    
}
