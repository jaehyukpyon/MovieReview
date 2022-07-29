package com.movie.service;

import com.movie.dto.BoardDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import com.movie.entity.Board;
import com.movie.entity.Member;
import com.movie.repository.BoardRepository;
import com.movie.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info("BoardServiceImpl.register()...");
        log.info("dto.toString() : {}", dto.toString());

        Board board = this.dtoToEntity(dto);
        repository.save(board); // Board insert query전에, email로 Member를 찾음

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info("BoardServiceImpl.getList()...");

        Function<Object[], BoardDTO> fn = en -> this.entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]);

        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPabeable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
        //return new PageResultDTO<>();
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[])result;

        return this.entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        // 해당 Board와 연관된 댓글부터 삭제
        replyRepository.deleteByBno(bno); // select 쿼리 없이 바로 delete query를 DB에 전송한다.

        // 게시글 삭제
        repository.deleteById(bno); // select query로 먼저 board를 조회한 다음(bno pk 사용), 그 다음 실질적인 db delete query 실행
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        System.out.println("BoardServiceImpl.modify() starts...");
        Board board = repository.getById(boardDTO.getBno());
        // 실제 entity 객체를 return 하는 것이 아닌, proxy entity를 return
        // 그러므로 영속성 context가 필요하기 때문에, @Transactional annotation is required.

        System.out.println("before calling board.changeTitle & board.changeContent");
        board.changeTitle(boardDTO.getTitle()); // 이 시점에서 실질적인 SELECT 쿼리 DB로 전송
        board.changeContent(boardDTO.getContent());
        System.out.println("after calling board.changeTitle & board.changeContent");

        repository.save(board);

        System.out.println("BoardServiceImpl.modify() ends...");
    }

}
