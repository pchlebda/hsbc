package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.storage.FollowersStorage;
import com.hsbc.twitter.storage.MessageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    private FollowService testee;

    @Mock
    private FollowersStorage followersStorageMock;

    @Mock
    private MessageStorage messageStorage;

    @BeforeEach
    void beforeEach() {
        testee = new FollowService(followersStorageMock, messageStorage);
    }

    @Test
    void shouldFollowUser() {
        //given
        FollowUser followUser = new FollowUser();
        followUser.setUsername("Alice");
        followUser.setFollowUsername("Bob");

        //when
        testee.followUser(followUser);

        //then
        verify(messageStorage,times(1)).checkIfUserExists("Alice");
        verify(messageStorage,times(1)).checkIfUserExists("Bob");
        verify(followersStorageMock,times(1)).follow("Alice", "Bob");
    }

}