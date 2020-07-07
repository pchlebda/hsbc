package com.hsbc.twitter.integration;


import com.hsbc.twitter.dto.MessagePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldAddMessage() throws Exception {
        //given
        MessagePost messagePost = new MessagePost();
        messagePost.setUsername("Bob");
        messagePost.setMessage("Sample message");
        String postUrl = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Void> entity = restTemplate.postForEntity(postUrl, messagePost, Void.class);
        MessagePost[] messagePosts = restTemplate.getForObject(postUrl + "/Bob", MessagePost[].class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(messagePosts.length).isEqualTo(1);
        assertThat(messagePosts[0]).extracting(MessagePost::getUsername).isEqualTo("Bob");
        assertThat(messagePosts[0]).extracting(MessagePost::getMessage).isEqualTo("Sample message");
        assertThat(messagePosts[0]).extracting(MessagePost::getCreatedAt).isNotNull();
    }

    @Test
    void shouldReturn400WhenMessageExceeds140chars(){
        //given
        MessagePost messagePost = new MessagePost();
        messagePost.setUsername("Bob");
        messagePost.setMessage("vjoRJZRgoeWPklHAG2TCB8Abs5ObT8FXfvxxC84dNA7oHMT2dW3lB0L7TzvlY3LjdbGbubWYvelXebg4PPkUHzp2J0bMiJCqteQBeY0ZT82b9QDzJ7yDI4xGlpRpyzzPNa7co06hVYLcv");
        String postUrl = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Void> entity = restTemplate.postForEntity(postUrl, messagePost, Void.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
