package com.example.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ChatController {
    @GetMapping("/chat")
    public String chatGet() {
        log.info("@Chatcontroller, chat GET()");
        return "chat";
    }
}
