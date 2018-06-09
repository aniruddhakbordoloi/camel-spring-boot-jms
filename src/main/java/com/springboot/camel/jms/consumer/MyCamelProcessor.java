package com.springboot.camel.jms.consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by Aniruddha on 10-06-2018.
 */
@Component
public class MyCamelProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("in exchange");
    }
}
