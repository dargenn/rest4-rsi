package io.dargenn.rest4;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
class MessageService {
    private Map<Integer, Message> messages = Maps.newHashMap();

    @PostConstruct
    void postConstruct() {
        List<Comment> comments = Lists.newArrayList(new Comment("komentarz"), new Comment("komentarz2"));
        messages.put(1, new Message(1, "Michal", "Testowa wiadomosc", comments));
        messages.put(2, new Message(2, "Michal2", "Inny content", Lists.newArrayList()));
    }

    List<Message> getMessages() {
        return Lists.newArrayList(messages.values());
    }

    Message getMessageById(Integer id) {
        return messages.get(id);
    }

    Message createMessage(Message message) {
        Integer nextId = messages.keySet().stream().max(Integer::compare).orElseThrow(IllegalAccessError::new) + 1;
        messages.put(nextId, new Message(nextId, message.getAuthor(), message.getContent(), message.getComments()));
        return getMessageById(nextId);
    }
}
