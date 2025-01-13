package com.example.controller;
import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        try{
            Account createdAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(createdAccount);
        }catch(IllegalArgumentException e){
            if(e.getMessage().equals("Username is already in use")){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        try{
            Account loggedAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(loggedAccount);
        } catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        try{
            Message newMessage = messageService.createMessage(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());
            return ResponseEntity.ok(newMessage);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public @ResponseBody Message getMessage(@PathVariable("message_id") Integer messageId){
        
    }
}
