package com.hsbc.twitter.integration;

import com.hsbc.twitter.dto.FollowUser;
import com.hsbc.twitter.dto.MessagePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FollowUserController {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldFollowUsersAndGetTheirMessagePosts() {
        //given
        MessagePost first = new MessagePost();
        first.setMessage("First message");
        first.setUsername("Bob");

        MessagePost second = new MessagePost();
        second.setMessage("Second message");
        second.setUsername("Alice");

        MessagePost third = new MessagePost();
        third.setMessage("Third message");
        third.setUsername("Alice");

        String postUrl = "http://localhost:" + port + "/api/v1/posts";
        String followUrl = "http://localhost:" + port + "/api/v1/users/follow";

        FollowUser joeFollowBob = new FollowUser();
        joeFollowBob.setUsername("Joe");
        joeFollowBob.setFollowUsername("Bob");
        FollowUser joeFollowAlice = new FollowUser();
        joeFollowAlice.setUsername("Joe");
        joeFollowAlice.setFollowUsername("Alice");

        restTemplate.postForEntity(postUrl, first, Void.class);
        restTemplate.postForEntity(postUrl, second, Void.class);
        restTemplate.postForEntity(postUrl, third, Void.class);

        restTemplate.postForEntity(followUrl, joeFollowBob, Void.class);
        restTemplate.postForEntity(followUrl, joeFollowAlice, Void.class);

        //when
        final MessagePost[] posts = restTemplate.getForObject(postUrl + "/followed/Joe", MessagePost[].class);

        //then
        assertThat(posts).hasSize(3);
        assertThat(posts).extracting(MessagePost::getMessage).contains("First message", "Second message", "Third message");
        assertThat(posts).extracting(MessagePost::getUsername).contains("Bob", "Alice");

    }
}
