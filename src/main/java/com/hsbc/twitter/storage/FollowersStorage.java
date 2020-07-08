package com.hsbc.twitter.storage;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class FollowersStorage {

    private Map<String, Set<String>> followUsers;

    public FollowersStorage() {
        this.followUsers = new ConcurrentHashMap<>();
    }

    public void follow(final String user, final String followUser) {
        final Set<String> followingUsers = followUsers.getOrDefault(user, new CopyOnWriteArraySet<>());
        followingUsers.add(followUser);
        followUsers.put(user, followingUsers);
    }

    public Set<String> getAllFollowUsers(final String user) {
        return followUsers.getOrDefault(user, Collections.emptySet());
    }
}
