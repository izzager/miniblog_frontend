package com.example.miniblogfrontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DatabaseCommentDto {

    private Long id;

    private Long userId;

    private Long postId;

    private String text;

    private LocalDateTime publicationDate;

}
