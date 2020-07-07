package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.MessagePost;
import com.hsbc.twitter.storage.FollowersStorage;
import com.hsbc.twitter.storage.MessageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    private MessageService testee;

    @Mock
    private FollowersStorage followersStorageMock;

    @Mock
    private MessageStorage messageStorageMock;

    @BeforeEach
    void beforeEach() {
        testee = new MessageService(messageStorageMock, followersStorageMock);
    }

    @Test
    void shouldAddMessagePost() {
        //given
        MessagePost messagePost = new MessagePost();
        messagePost.setUsername("Alice");
        messagePost.setMessage("Sample message");

        //when
        testee.addMessagePost(messagePost);

        //then
        verify(messageStorageMock, times(1)).addMessagePost(messagePost);
    }

    @Test
    void shouldGetAllMessagesForUser() {
        //given
        List<MessagePost> messagePosts = Arrays.asList(new MessagePost(), new MessagePost());
        when(messageStorageMock.getAllMessagesSortedByDate("Alice")).thenReturn(messagePosts);

        //when
        List<MessagePost> results = testee.getAllPostMessagesForUser("Alice");

        //then
        assertEquals(messagePosts, results);
        verify(messageStorageMock, times(1)).getAllMessagesSortedByDate("Alice");
    }

    @Test
    void shouldGetAllMessagesFromFollowedUsers() {
        //given
        LocalDateTime now = LocalDateTime.now();
        MessagePost bobFirstMessagePost = new MessagePost("Bob","Bob 1 message", now);
        MessagePost bobSecondMessagePost = new MessagePost("Bob","Bob 2 message", now);
        List<MessagePost> bobsMessages = Arrays.asList(bobFirstMessagePost, bobSecondMessagePost);

        MessagePost aliceFirstMessagePost = new MessagePost("Alice","Alice 1 message", now);
        List<MessagePost> aliceMessages = Arrays.asList(aliceFirstMessagePost);

        when(followersStorageMock.getAllFollowUsers("Joe")).thenReturn(Stream.of("Alice", "Bob").collect(Collectors.toSet()));
        when(messageStorageMock.getAllMessagesSortedByDate("Alice")).thenReturn(aliceMessages);
        when(messageStorageMock.getAllMessagesSortedByDate("Bob")).thenReturn(bobsMessages);

        List<MessagePost> expected = Arrays.asList(bobFirstMessagePost, bobSecondMessagePost, aliceFirstMessagePost);

        //when
        List<MessagePost> result = testee.getMessagePostFromAllFollowedUsers("Joe");

        //then
        assertEquals(expected, result);
        verify(followersStorageMock, times(1)).getAllFollowUsers("Joe");
        verify(messageStorageMock, times(1)).getAllMessagesSortedByDate("Alice");
        verify(messageStorageMock, times(1)).getAllMessagesSortedByDate("Bob");
    }
}