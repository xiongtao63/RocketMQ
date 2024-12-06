package com.example.cpp.controller;


import com.example.cpp.dto.Response;
import com.example.cpp.entity.Chapter;
import com.example.cpp.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ChapterController {

    @Resource
    ChapterService chapterService;

    @PostMapping("/publish")
    public Response publish(@RequestBody Chapter chapter){
        try {
            chapterService.publish(chapter);
            return new Response("200","success");
        }catch (Exception e){
            return new Response("500",e.getMessage());
        }

    }
}
