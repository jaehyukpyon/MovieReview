package com.movie.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(exclude = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply extends BaseEntity {
    // Reply 는 회원이 아닌 사람도 댓글을 남길 수 있다고 가정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    @ManyToOne
    private Board board;

}
