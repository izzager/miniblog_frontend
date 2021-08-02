package com.example.miniblogfrontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FullPostDto {

    private Long id;

    private String userNickname;

    private String text;

    private LocalDateTime publicationDate;

    private List<FullCommentDto> comments = new ArrayList<>();

}
