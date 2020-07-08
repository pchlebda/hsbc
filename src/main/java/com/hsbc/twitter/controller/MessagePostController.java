package com.hsbc.twitter.controller;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.dto.MessagePost;
import com.hsbc.twitter.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessagePostController {

    private final MessageService messageService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED)
    void createPost(@Valid @RequestBody final MessagePost messagePost) {
        messageService.addMessagePost(messagePost);
    }

    @GetMapping("/api/v1//posts/{username}")
    @ResponseStatus(HttpStatus.OK)
    List<MessagePost> getAllUserMessagePosts(@PathVariable("username") final String username) {
        return messageService.getAllPostMessagesForUser(username);
    }

    @GetMapping("/api/v1//posts/followed/{username}")
    @ResponseStatus(HttpStatus.OK)
    List<MessagePost> getAllUserMessagePostsFollowed(@PathVariable("username") final String username) {
        return messageService.getMessagePostFromAllFollowedUsers(username);
    }
}
