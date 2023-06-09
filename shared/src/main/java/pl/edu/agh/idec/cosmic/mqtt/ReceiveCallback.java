package pl.edu.agh.idec.cosmic.mqtt;

@FunctionalInterface
public interface ReceiveCallback {
    void process(byte[] data);
}
