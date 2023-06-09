package pl.edu.agh.idec.cosmic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pl.edu.agh.idec.cosmic")
public class ConsumerApplication {
    
    public static final String QUEUE_NAME = "tasks";
    
    public static void main(String[] args) {
         SpringApplication.run(ConsumerApplication.class, args);
    }
}
