package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.MessagePost;
import com.hsbc.twitter.storage.FollowersStorage;
import com.hsbc.twitter.storage.MessageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageStorage messageStorage;
    private final FollowersStorage followersStorage;

    public void addMessagePost(final MessagePost messagePost) {
        messagePost.setCreatedAt(LocalDateTime.now());
        messageStorage.addMessagePost(messagePost);
    }

    public List<MessagePost> getAllPostMessagesForUser(final String username) {
        return messageStorage.getAllMessagesSortedByDate(username);
    }

    public List<MessagePost> getMessagePostFromAllFollowedUsers(final String username) {
        final Set<String> allFollowUsers = followersStorage.getAllFollowUsers(username);
        return allFollowUsers.stream().map(messageStorage::getAllMessagesSortedByDate)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(MessagePost::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
