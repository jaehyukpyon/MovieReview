package com.movie.service;

import com.movie.dto.BoardDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import com.movie.entity.Board;
import com.movie.entity.Member;

public interface BoardService {

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder()
                                .email(dto.getWriterEmail())
                                .build();

        Board board = Board.builder()
                            .bno(dto.getBno())
                            .title(dto.getTitle())
                            .content(dto.getContent())
                            .writer(member)
                            .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        // Object[]의 냐용은 Board, Member, 댓글의 수는 Long type으로 반환됨.
        // 이를 parameter로 전달받아서 BoardDTO를 구성하도록
        BoardDTO boardDTO = BoardDTO.builder()
                                    .bno(board.getBno())
                                    .title(board.getTitle())
                                    .content(board.getContent())
                                    .regDate(board.getRegDate())
                                    .modDate(board.getModDate())
                                    .writerEmail(member.getEmail())
                                    .writerName(member.getName())
                                    .replyCount(replyCount.intValue())
                                    .build();
        return boardDTO;
    }

    public abstract Long register(BoardDTO dto);

    // 게시물의 목록 처리
    public abstract PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 한 개의 게시물 조회 처리 >> 게시물의 번호(bno)를 parameter로 받아서 처리
    public abstract BoardDTO get(Long bno);

    // 삭제 기능 추가
    public abstract void removeWithReplies(Long bno);

    // 게시글의 'title' & 'content' 수정
    public abstract void modify(BoardDTO boardDTO);

}
