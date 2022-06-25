package com.movie.service;

import com.movie.dto.GuestbookDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import com.movie.entity.Guestbook;
import com.movie.repository.GuestbookRepository;
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
        Pageable pageable = requestDTO.getPabeable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

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
        // 업데이트 항목은 제목 및 내용
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }
}
