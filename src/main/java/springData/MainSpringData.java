package springData;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springData.repository.OfficesRepository;

import java.math.BigDecimal;

public class MainSpringData {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("springData.config");

        OfficesRepository officesRepository = context.getBean(OfficesRepository.class);

        officesRepository.findAll().forEach(System.out::println);
        System.out.println("----------------------------------------------------------------------------------------------");
        officesRepository.findByTargetBetween(BigDecimal.valueOf(300), BigDecimal.valueOf(600)).forEach(System.out::println);

        context.close();
    }
}