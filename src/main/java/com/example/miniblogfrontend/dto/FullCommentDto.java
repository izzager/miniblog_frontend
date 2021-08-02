package com.example.miniblogfrontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FullCommentDto {

    private Long id;

    private String userNickname;

    private Long postId;

    private String text;

    private LocalDateTime publicationDate;

}
