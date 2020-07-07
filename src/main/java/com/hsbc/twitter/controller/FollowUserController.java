package com.hsbc.twitter.controller;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FollowUserController {

    private final FollowService followService;

    @PostMapping("/api/v1/users/follow")
    @ResponseStatus(HttpStatus.CREATED)
    void createPost(@Valid @RequestBody final FollowUser followUser) {
        followService.followUser(followUser);
    }
}
