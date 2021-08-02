package com.example.miniblogfrontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DatabasePostDto {

    private Long id;

    private Long userId;

    private String text;

    private LocalDateTime publicationDate;

    private List<DatabaseCommentDto> comments = new ArrayList<>();

}
