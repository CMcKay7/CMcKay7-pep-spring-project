package com.example.service;
import com.example.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Integer postedBy, String messageText, Long timePostedEpoch){
        if(messageText == null || messageText.isBlank() || messageText.length() > 255){
            throw new IllegalArgumentException();
        }

        if(!messageRepository.existsById(postedBy)){
            throw new IllegalArgumentException();
        }

        Message message = new Message();
        message.setPostedBy(postedBy);
        message.setMessageText(messageText);
        message.setTimePostedEpoch(timePostedEpoch);

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId){
        return messageRepository.findById(messageId);
    }

    public long deleteMessageById(Integer messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessageById(Integer messageId, String newMessageText){
        if(newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255){
            return 0;
        }

        Message message = messageRepository.findById(messageId).orElse(null);

        if(message == null){
            return 0;
        }

        message.setMessageText(newMessageText);
        messageRepository.save(message);

        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
