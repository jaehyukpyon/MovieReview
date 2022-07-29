package com.movie.repository;

import com.movie.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b, w FROM Board b LEFT JOIN b.writer w WHERE b.bno = :bno")
    public abstract Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON b.bno = r.board.bno WHERE b.bno = :bno")
    public abstract List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, w, count(r) " +
                   "FROM Board b " +
                   "LEFT JOIN b.writer w " +
                   "LEFT JOIN Reply r on b.bno = r.board.bno " +
                   "GROUP BY b",
           countQuery = "SELECT COUNT(b) FROM Board b")
    public abstract Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            "FROM Board b " +
            "LEFT OUTER JOIN b.writer w " +
            "LEFT OUTER JOIN Reply r on b.bno = r.board.bno " +
            "WHERE b.bno = :bno")
    public abstract Object getBoardByBno(@Param("bno") Long bno);
    // Query 결과는 한 개의 row. >> :bno에 해당하는 게시글에 달린 댓글의 개수

}
