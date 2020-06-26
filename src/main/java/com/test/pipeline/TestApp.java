package com.test.pipeline;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TestApp {
  private  int num1;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot12!";
    }

  @RequestMapping("/go")
  public String index1() {
    return "Greetings from Spring Boot!!!!!";
  }

    public static void main(String[] args) {
      TestApp testApp = new TestApp();
      testApp.num1 = 10;
      testApp.la();
    }


    private void la(){
      if(true){
        return;
      }
      System.out.println("lalal");
    }
}
