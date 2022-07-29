package com.movie.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    // 화면에서 필요한 작성자의 이메일(writerEmail)과 작성자의 이름(writerName)으로 처리.

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; // 작성자의 이메일(id)

    private String writerName; // 작성자의 이름

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount; // 해당 게시글의 댓글 개수
    
}
