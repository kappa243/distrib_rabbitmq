package pl.edu.agh.idec.cosmic.mqtt;

public interface IQueueConsumer {
    void addReceiveCallback(ReceiveCallback callback);
}
