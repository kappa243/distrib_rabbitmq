package pl.edu.agh.idec.cosmic.mqtt;

public interface IQueueProducer {
    
    void send(byte[] data);
}
