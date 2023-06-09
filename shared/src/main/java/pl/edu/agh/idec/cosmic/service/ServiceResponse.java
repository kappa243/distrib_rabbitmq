package pl.edu.agh.idec.cosmic.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResponse implements Serializable {
    private String consumer_id;
    private String task_id;
    private ServiceType serviceType;
    
    public ServiceResponse(String consumer_id, String task_id, ServiceType serviceType) {
        this.consumer_id = consumer_id;
        this.task_id = task_id;
        this.serviceType = serviceType;
    }
}
