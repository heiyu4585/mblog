package com.mblog.controller;

import com.mblog.model.About;
import com.mblog.service.AboutRepository;
import com.mblog.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/")
public class AboutController {
    @Autowired
    private AboutRepository aboutRepository;

    @GetMapping(path="/getAbout")
    public Result getAbout() {
        System.out.println(aboutRepository.findById(1));
        Optional about =  aboutRepository.findById(1);
        return Result.ok("添加成功",about);
    }

    @PostMapping(path="/updateAbout")
    public Result updateAbout (@RequestBody About about) {
        aboutRepository.save(about);
        return Result.ok("添加成功");
    }
}
