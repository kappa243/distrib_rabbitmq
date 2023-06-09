package pl.edu.agh.idec.cosmic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.agh.idec.cosmic.service.ServiceType;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "pl.edu.agh.idec.cosmic")
public class ProducerApplication implements CommandLineRunner {
    
    private final TaskService taskService;
    
    public ProducerApplication(TaskService taskService) {
        this.taskService = taskService;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    @Override
    public void run(String... args) {
        
        while (true) {
            System.out.println("Choose what types of task send to queue:");
            for (ServiceType type : ServiceType.values()) {
                System.out.println(type.ordinal() + " - " + type);
            }
            
            Scanner scanner = new Scanner(System.in);
            String input;
            do {
                input = scanner.nextLine();
            } while (input.isEmpty());
            int choice = Integer.parseInt(input);
            
            if (choice >= 0 && choice < ServiceType.values().length) {
                ServiceType type = ServiceType.values()[choice];
                System.out.println("Sending task of type: " + type);
                
                taskService.sendTask(type);
            } else {
                System.out.println("Wrong choice!");
            }
            
        }
    }
    
}
