package com.hsbc.twitter.storage;

import com.hsbc.twitter.dto.MessagePost;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
public class MessageStorage {

    private Map<String, List<MessagePost>> messagesByUserId;

    public MessageStorage() {
        this.messagesByUserId = new HashMap<>();
    }

    public void addMessagePost(@NonNull final MessagePost messagePost) {
        final List<MessagePost> postedMessages = messagesByUserId.getOrDefault(messagePost.getUsername(), new ArrayList<>());
        postedMessages.add(messagePost);
        messagesByUserId.put(messagePost.getUsername(), postedMessages);
    }

    public List<MessagePost> getAllMessagesSortedByDate(@NonNull final String username) {
        final List<MessagePost> messagePosts = messagesByUserId.getOrDefault(username, Collections.emptyList());
        return messagePosts.stream().
                sorted(Comparator.comparing(MessagePost::getCreatedAt).reversed())
                .collect(toList());
    }

}
