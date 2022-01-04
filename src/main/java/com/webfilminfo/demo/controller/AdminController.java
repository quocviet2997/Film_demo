package com.webfilminfo.demo.controller;

import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.dto.FilmDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @GetMapping("/admin-home")
    String getHome(){
        return "views/admin/home";
    }

    @GetMapping("/admin-api-film")
    String getListFilm(@RequestParam(name="page", required = false) Integer page,
                      @RequestParam(name="limit", required = false) Integer limit, Model model){
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_FILM;
        model.addAttribute("page",page);
        model.addAttribute("limit", limit);
        return "views/admin/film/list";
    }

    @GetMapping("/admin-api-film/{id}")
    String editFilm(@PathVariable Long id ,Model model){
        model.addAttribute("id", id);
        return "views/admin/film/form";
    }

    @GetMapping("/admin-api-film/new")
    String insertFilm(){
        return "views/admin/film/form";
    }

    @GetMapping("/admin-api-user")
    String getListUser(@RequestParam(name="page", required = false) Integer page,
                       @RequestParam(name="limit", required = false) Integer limit, Model model){
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_USER;
        model.addAttribute("page",page);
        model.addAttribute("limit", limit);
        return "views/admin/user/list";
    }

    @GetMapping("/admin-api-user/{id}")
    String editUser(@PathVariable Long id ,Model model){
        model.addAttribute("id", id);
        return "views/admin/user/form";
    }

    @GetMapping("/admin-api-user/new")
    String insertUser(){
        return "views/admin/user/form";
    }

    @GetMapping("/admin-api-comment")
    String getListComment(@RequestParam(name="page", required = false) Integer page,
                       @RequestParam(name="limit", required = false) Integer limit, Model model){
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_COMMENT;
        model.addAttribute("page",page);
        model.addAttribute("limit", limit);
        return "views/admin/comment/list";
    }

    @GetMapping("/admin-api-comment/{id}")
    String editComment(@PathVariable Long id ,Model model){
        model.addAttribute("id", id);
        return "views/admin/comment/form";
    }

    @GetMapping("/admin-api-comment/new")
    String insertComment(){
        return "views/admin/comment/form";
    }
}
