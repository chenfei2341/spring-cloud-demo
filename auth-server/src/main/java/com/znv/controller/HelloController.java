package com.znv.controller;

import com.znv.producer.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HelloController {

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${nickName}")
    private String nickName;

    @Autowired
    private Sender sender;

    @RequestMapping("/send")
    public void send() {
        sender.send();
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        logger.info("called by product-service");
        return new ResponseEntity<String>("hello  " + nickName, HttpStatus.OK);
    }
}
