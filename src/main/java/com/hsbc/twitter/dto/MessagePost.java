package com.hsbc.twitter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePost {

    @NotBlank(message = "Username is mandatory.")
    private String username;
    @NotBlank(message = "Message is mandatory.")
    @Size(max = 140, message = "Message cannot be longer than 140 characters.")
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
