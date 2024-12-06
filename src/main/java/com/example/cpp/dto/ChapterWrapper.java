package com.example.cpp.dto;

import com.example.cpp.entity.Chapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChapterWrapper {

    private Chapter chapter;

    public ChapterWrapper(Chapter chapter) {
        this.chapter = chapter;
    }

    public Chapter getObject(String topic){
        Chapter clone = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String raw = mapper.writeValueAsString(this.chapter);
            clone = mapper.readValue(raw,Chapter.class);
            switch (topic){
                case "ADV":
                    clone.setSeriesDescription(null);
                    clone .setContent(null);
                    clone.setSeriesName(null);
                    clone.setChapterCount(null);
                    clone.setAuthor(null);
                    clone.setSeq(null);
                    clone.setLength(null);
                    clone.setChapterTitle(null);
                    break;
                case "OT":
                    clone.addTag("SRC-CMS");
                    break;
                case "PORTAL":
                default:{
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
