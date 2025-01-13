package com.example.service;
import com.example.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.repository.MessageRepository;
import java.util.List;

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
}
