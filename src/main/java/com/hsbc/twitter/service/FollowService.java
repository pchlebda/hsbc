package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.storage.FollowersStorage;
import com.hsbc.twitter.storage.MessageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowersStorage followersStorage;
    private final MessageStorage messageStorage;

    public void followUser(final FollowUser followUser) {
        messageStorage.checkIfUserExists(followUser.getUsername());
        messageStorage.checkIfUserExists(followUser.getFollowUsername());
        followersStorage.follow(followUser.getUsername(), followUser.getFollowUsername());
    }
}
