package io.dargenn.rest4;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
class Message extends ResourceSupport {
    private Integer messageId;
    private String author;
    private String content;
    private List<Comment> comments;
}
