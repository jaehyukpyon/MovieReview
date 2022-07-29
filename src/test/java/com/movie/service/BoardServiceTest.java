package com.movie.service;

import com.movie.dto.BoardDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @DisplayName(value = "새로운 Board 게시글 등록")
    //@Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                                .title("Test.")
                                .content("Test...")
                                .writerEmail("user55@aaa.com") // 현재 DB에 저장 돼 있는 회원 email
                                .build();

        Long bno = boardService.register(dto);
        assertEquals(bno, 101L);
    }

    @DisplayName(value = "BoardServiceImpl.getList 메서드 test")
    //@Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @DisplayName(value = "Board 삭제 시 연관된 댓글 먼저 삭제 후 게시글 삭제")
    //@Test
    public void testRemove() {
        Long bno = 1L; // 1번 게시물과 연관된 댓글 PK >> 106, 168

        boardService.removeWithReplies(bno);
    }

    @DisplayName(value = "게시글 제목 및 내용 수정")
    @Test
    public void testModify() {
        System.out.println("starts...");

        String newTitle = new String("제목 변경합니다.");
        String newContent = new String("내용 변경합니다.");

        BoardDTO boardDTO = BoardDTO.builder()
                                    .bno(2L)
                                    .title(newTitle)
                                    .content(newContent)
                                    .build();

        System.out.println("middle...");

        boardService.modify(boardDTO);

        System.out.println("end...");
    }

}