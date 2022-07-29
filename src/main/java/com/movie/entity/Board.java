package com.movie.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(exclude = "writer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    // Board의 수정은 제목'title' & 내용'content'만 가능
    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

}
