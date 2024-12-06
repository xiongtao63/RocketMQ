package com.example.cpp;

import com.example.cpp.entity.Chapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@SpringBootTest
class CppApplicationTests {

    @Test
    void contextLoads() {
    }

    private String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();

        StringBuffer sb=new StringBuffer();

        for(int i=0;i<length;i++){

            int number=random.nextInt(str.length());

            sb.append(str.charAt(number));

        }

        return sb.toString();

    }

    private Integer getRandomInteger(int min, int max){
        int r =0;
        do{
            r = new Random().nextInt(max);
            if(r >= min){
                break;
            }
        }while (true);
        return r;
    }

    private String createChapterJson(){
        Chapter chapter =new Chapter();
        chapter.setSeriesId(getRandomInteger(0,10000).longValue());
        chapter.setChapterId(getRandomInteger(0,10000).longValue());
        chapter.setSeriesName("演示用系列标题-"+ getRandomString(5));
        chapter.setSeriesDescription("演示用系列描述文本-"+ getRandomString(100));
        chapter.setChapterCount(getRandomInteger(50,300));
        chapter.setChapterTitle("演示用章节标題-"+ getRandomString( 5));
        chapter.setAuthor("演示用作者名-"+ getRandomString(  10));
        chapter.setSeq(getRandomInteger(0,20));
        chapter.addTag("连载").addTag("签约") .addTag("免费").addTag("玄幻");
        String content =getRandomString(getRandomInteger(50,300));
        chapter.setContent(content);
        chapter.setLength(content.length());
        chapter.setPublishTime("2099-12-31 00:00");
        ObjectMapper mapper =new ObjectMapper();
        String json =null;
        try {
            json = mapper.writeValueAsString(chapter);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return json;
    }

    @Test
    public void testPublish() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 10; i++) {
            String json = createChapterJson();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity request = new HttpEntity(json,httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8000/publish",request,String.class);
            if(response.getStatusCodeValue() == 200){
                System.out.println(response.getBody());
            }
            Thread.sleep(1000);
        }
    }

}
