package com.movie.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "table_memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment strategy if mysql
    private Long mno;

    @Column(length = 500, nullable = false)
    private String memoText;

}
