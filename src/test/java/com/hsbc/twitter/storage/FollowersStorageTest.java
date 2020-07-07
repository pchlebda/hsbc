package com.hsbc.twitter.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowersStorageTest {

    private FollowersStorage testee;

    @BeforeEach
    void beforeEach() {
        testee = new FollowersStorage();
    }

    @Test
    void shouldFollow2OtherUsers() {
        //given
        final Set<String> expectedUsers = Stream.of("Bob", "Joe").collect(Collectors.toSet());
        testee.follow("Alice", "Bob");
        testee.follow("Alice", "Joe");

        //when
        final Set<String> followUsers = testee.getAllFollowUsers("Alice");

        //then
        assertEquals(expectedUsers, followUsers);
    }

    @Test
    void shouldFollowBeOneDirection() {
        //given
        final Set<String> expectedUser = Stream.of("Bob").collect(Collectors.toSet());
        testee.follow("Alice", "Bob");

        //when
        final Set<String> aliceFollowed = testee.getAllFollowUsers("Alice");
        final Set<String> bobFollowed = testee.getAllFollowUsers("Bob");

        //then
        assertEquals(expectedUser, aliceFollowed);
        assertEquals(Collections.EMPTY_SET, bobFollowed);
    }
}