package com.hsbc.twitter.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FollowUser {

    @NotBlank(message = "Username is mandatory.")
    private String username;
    @NotBlank(message = "FollowUsername is mandatory.")
    private String followUsername;
}
