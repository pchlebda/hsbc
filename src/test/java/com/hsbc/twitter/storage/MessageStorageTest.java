package com.hsbc.twitter.storage;

import com.hsbc.twitter.dto.MessagePost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageStorageTest {

    private MessageStorage testee;

    @BeforeEach
    void beforeEach() {
        testee = new MessageStorage();
    }

    @Test
    void shouldAddMessages() {
        //given
        MessagePost messagePost = new MessagePost();
        messagePost.setCreatedAt(LocalDateTime.now());
        messagePost.setMessage("Sample message");
        messagePost.setUsername("Alice");

        testee.addMessagePost(messagePost);

        //when
        final List<MessagePost> allMessages = testee.getAllMessagesSortedByDate("Alice");

        //then
        assertEquals(singletonList(messagePost), allMessages);
    }

    @Test
    void shouldMessagesBeSortedInReverseChronologicalOrder() {
        //given
        List<MessagePost> listOf100Message = getListOf100Message();
        listOf100Message.forEach(testee::addMessagePost);

        //when
        List<MessagePost> sortedByDate = testee.getAllMessagesSortedByDate("Alice");

        //then
        LocalDateTime prev = LocalDateTime.MAX;

        for (MessagePost messagePost : sortedByDate) {
            LocalDateTime actual = messagePost.getCreatedAt();
            long minutes = Duration.between(actual, prev).toMinutes();
            Assertions.assertTrue(minutes >= 0);
            prev = actual;
        }
    }

    private List<MessagePost> getListOf100Message() {

        List<MessagePost> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; ++i) {
            MessagePost messagePost = new MessagePost();
            LocalDateTime createdAt = LocalDateTime.now().plus(random.nextInt(60), MINUTES);
            messagePost.setCreatedAt(createdAt);
            messagePost.setMessage("Sample message " + i);
            messagePost.setUsername("Alice");
            result.add(messagePost);
        }
        return result;
    }

}