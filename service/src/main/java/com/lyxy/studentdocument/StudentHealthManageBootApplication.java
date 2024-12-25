package com.lyxy.studentdocument;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@SpringBootApplication
public class StudentHealthManageBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentHealthManageBootApplication.class, args);
    }

}
