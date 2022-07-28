package com.movie.service;

import com.movie.dto.GuestbookDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import com.movie.entity.Guestbook;
import com.movie.entity.QGuestbook;
import com.movie.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("----- GuestbookServiceImpl register()");
        log.info("dto.toString() : " + dto.toString());

        Guestbook entity = this.dtoToEntity(dto);

        log.info("entity.toString() : " + entity.toString());

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        // PageRequestDTO's default => page = 1, size = 10
        Pageable pageable = requestDTO.getPabeable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = this.getSearch(requestDTO);
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); // QuerydslPredicateExecutor's findAll method

        Function<Guestbook, GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? this.entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        // 업데이트 대상 항목은 제목 및 내용
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) { // Querydsl 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { // 검색 종류(type)이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        // 모든 검색 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

}
