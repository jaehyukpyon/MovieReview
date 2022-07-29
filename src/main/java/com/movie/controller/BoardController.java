package com.movie.controller;

import com.movie.dto.PageRequestDTO;
import com.movie.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/board/")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("BoardController.list() starts...");

        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping(value = "/register")
    public void register() {

    }

}
