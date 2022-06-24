package com.movie.service;

import com.movie.dto.GuestbookDTO;

public interface GuestbookService {

    public abstract Long register(GuestbookDTO dto);

}
