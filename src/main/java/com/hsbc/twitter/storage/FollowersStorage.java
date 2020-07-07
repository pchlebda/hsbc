package com.hsbc.twitter.storage;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class FollowersStorage {

    private Map<String, Set<String>> followUsers;

    public FollowersStorage() {
        this.followUsers = new HashMap<>();
    }

    public void follow(final String user, final String followUser) {
        final Set<String> followingUsers = followUsers.getOrDefault(user, new HashSet<>());
        followingUsers.add(followUser);
        followUsers.put(user, followingUsers);
    }

    public Set<String> getAllFollowUsers(final String user) {
        return followUsers.getOrDefault(user, Collections.emptySet());
    }
}
