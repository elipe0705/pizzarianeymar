package com.itb.inf2fm.pizzarianeymar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzarianeymarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzarianeymarApplication.class, args);
        System.out.println("Servidor rodando na porta 8080");
    }

}

