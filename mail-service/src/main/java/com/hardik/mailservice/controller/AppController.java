package com.hardik.mailservice.controller;

import com.hardik.mailservice.service.MimeEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private final MimeEmailService mimeEmailService;

    public AppController(MimeEmailService mimeEmailService) {
        this.mimeEmailService = mimeEmailService;
    }

    @GetMapping()
    public String hello(){
        return "hello";
    }

    @GetMapping("/mime")
    public String mime(@RequestParam String to, @RequestParam String subject, @RequestParam String text){
        mimeEmailService.sendEmail(to, subject, text);
        return "Mail sent succesfully using MIME!";
    }
}
