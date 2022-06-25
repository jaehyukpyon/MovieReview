package com.movie.controller;

import com.movie.dto.GuestbookDTO;
import com.movie.dto.PageRequestDTO;
import com.movie.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping(value = "/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping(value = {"/", "/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("----- GuestbookController list()");

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping(value = "/register")
    public void register() {
        log.info("----- GuestbookController GET register()");
    }

    @PostMapping(value = "/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("----- GuestbookController POST register()");

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping(value = {"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        // list.html의 a 태그에서 <a th:href="@{/guestbook/read(gno = ${dto.gno}, page= ${result.page})}"></a>
        log.info("----- GuestbookController GET read()/modify()");

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping(value = "/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes) {
        log.info("----- GuestbookController POST remove()");
        log.info("target gno : " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping(value = "/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("----- GuestbookController POST modify()");
        log.info("target dto : {}", dto.toString());

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

}
