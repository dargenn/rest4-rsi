package io.dargenn.rest4;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MessageResource {
    private final @NonNull MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    public HttpEntity<Message> getMessage(@PathVariable Integer id) {
        Message message = messageService.getMessageById(id);
        message.getLinks().clear();
        message.add(linkTo(methodOn(MessageResource.class).getMessage(id)).withSelfRel());
        message.add(linkTo(methodOn(MessageResource.class).getCommentsFromMessage(id)).withSelfRel());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsFromMessage(@PathVariable Integer id) {
        return messageService.getMessageById(id).getComments();
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{messageId}").buildAndExpand(newMessage.getMessageId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
