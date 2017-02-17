//package com.example.web;
//
//import com.olbati.server.web.dto.ReactEvent;
//import org.apache.log4j.Logger;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.inject.Inject;
//
//@Configuration
//@RestController
//public class WebSocketController
//{
//
//
//    private static final Logger log = Logger.getLogger(WebSocketController.class);
//
//    @Inject
//    SimpMessagingTemplate messagingTemplate;
//
//    @EventListener
//    public void sendMessage(ReactEvent event) {
//
//        log.info("Got angular message event " + event);
//
//        messagingTemplate.convertAndSend("/topic/events", event);
//
//    }
//
//}
