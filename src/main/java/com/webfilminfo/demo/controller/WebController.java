package com.webfilminfo.demo.controller;

import com.webfilminfo.demo.constant.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class WebController {
    @GetMapping(value = {"/home", ""})
    String getHome(@RequestParam(name="page", required = false) Integer page,
                   @RequestParam(name="limit", required = false) Integer limit, Model model){
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_FILM;
        model.addAttribute("page",page);
        model.addAttribute("limit", limit);
        return "/views/web/home";
    }

    @GetMapping("/{id}")
    String getFilmDetail(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "/views/web/film";
    }
}
