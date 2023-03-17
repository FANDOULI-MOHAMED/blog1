package com.example.blog1.controllers;

import com.example.blog1.entities.News;
import com.example.blog1.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class NewsController {


    //test*******************************
    @GetMapping("**")
    @ResponseBody
    public String home() {
        return "ya rabi ";
    }
    @Autowired
    NewsRepository newsRepository;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

    @GetMapping("all")
    public String list(Model model) {
    model.addAttribute("news",newsRepository.findAll());
        return "list";
    }

    @GetMapping("add")
    public String list() {

        return "update";
    }


    @PostMapping("add")
    //@ResponseBody
    public String addArticle( @RequestBody News news,  MultipartFile[] files) {


        /// part upload

        StringBuilder fileName = new StringBuilder();
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        fileName.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        news.setPicture(fileName.toString());
        newsRepository.save(news);
        return "redirect:all";


    }

    @GetMapping("edit/{id}")
    public String updateArticle(@PathVariable("id") long id, Model model) {
        News n= newsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid news Id:" + id));

        model.addAttribute("news",n);
        return "update";
    }


    @PostMapping("edit/{id}")
    public String updateArticle(@PathVariable("id") long id,@RequestBody News news) {

        newsRepository.save(news);
        return "redirect:all";
    }

    @DeleteMapping("delete/{id}")
    public String del(@RequestParam Long id ){
            newsRepository.deleteById(id);
        return "redirect:all";
    }
}
