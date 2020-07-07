package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.storage.FollowersStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowersStorage followersStorage;

    public void followUser(final FollowUser followUser) {
        followersStorage.follow(followUser.getUsername(), followUser.getFollowUsername());
    }
}
