package com.example.SpringTestExample;

import com.example.SpringTestExample.configuration.JavaConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTestExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
    }
}
