package com.sda.javacraiova.berecap;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.sda.javacraiova.berecap.model.UserModel;
import com.sda.javacraiova.berecap.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class BerecapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BerecapApplication.class, args);
    }

   @Bean
   CommandLineRunner init(UserRepository userRepository) {
       return args -> {
           Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
               UserModel user = new UserModel(name, name.toLowerCase() + "@domain.com");
               userRepository.save(user);
           });
           userRepository.findAll().forEach(System.out::println);
       };
   }

    @Bean
    public Module hibernate5Module()
    {
        return new Hibernate5Module();
    }
}
