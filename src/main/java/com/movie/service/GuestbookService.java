package com.movie.service;

import com.movie.dto.GuestbookDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.dto.PageResultDTO;
import com.movie.entity.Guestbook;

public interface GuestbookService {

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                                    .gno(dto.getGno())
                                    .title(dto.getTitle())
                                    .content(dto.getContent())
                                    .writer(dto.getWriter())
                                    .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
                                        .gno(entity.getGno())
                                        .title(entity.getTitle())
                                        .content(entity.getContent())
                                        .writer(entity.getWriter())
                                        .regDate(entity.getRegDate())
                                        .modDate(entity.getModDate())
                                        .build();
        return dto;
    }

    public abstract Long register(GuestbookDTO dto);

    public abstract PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

}
