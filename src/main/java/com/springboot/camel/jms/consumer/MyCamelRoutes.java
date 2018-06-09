package com.springboot.camel.jms.consumer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Aniruddha on 09-06-2018.
 */
@Component
public class MyCamelRoutes extends RouteBuilder{
    public void configure() throws Exception {
        System.out.println("check");
    }
}
