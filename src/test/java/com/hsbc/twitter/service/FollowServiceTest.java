package com.hsbc.twitter.service;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.storage.FollowersStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    private FollowService testee;

    @Mock
    private FollowersStorage followersStorageMock;

    @BeforeEach
    void beforeEach() {
        testee = new FollowService(followersStorageMock);
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
        verify(followersStorageMock).follow("Alice", "Bob");
    }

}