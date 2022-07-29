package com.movie.repository;

import com.movie.entity.Board;
import com.movie.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @DisplayName(value = "Reply 엔티티 추가")
    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            // 1번부터 100번까지 random number
            Long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder()
                    .bno(bno)
                    .build();

            Reply reply = Reply.builder()
                    .text("Reply......." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
            System.out.println(i + "번째 Reply saved...\r\n");
            // Board entity에 대한 SELECT query 없이 곧바로 Reply insert query 실행
        });
    }

}