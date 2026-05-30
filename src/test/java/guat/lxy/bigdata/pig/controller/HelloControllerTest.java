package guat.lxy.bigdata.pig.controller;

import org.junit.jupiter.api.Test;

class HelloControllerTest {
    HelloController helloController = new HelloController();
    @Test
    void hello() {
        System.out.println(helloController.hello());
    }
}