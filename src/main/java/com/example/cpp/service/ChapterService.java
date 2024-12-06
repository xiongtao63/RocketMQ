package com.example.cpp.service;

import com.example.cpp.dto.ChapterWrapper;
import com.example.cpp.entity.Chapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ChapterService
{

    @Resource
    DefaultMQProducer producer;
    public void publish(Chapter chapter){
        log.info("chapter已处理完毕，准备推送至Broker");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            ChapterWrapper chapterWrapper = new ChapterWrapper(chapter);
            String portalData = mapper.writeValueAsString(chapterWrapper.getObject("PORTAL"));

            Message portalMessage = new Message("PORTAL", "", portalData.getBytes());
            SendResult portalResult = producer.send(portalMessage);
            log.info("PORTAL主题消息已经发送：MSGID:"+portalResult.getMsgId()+", 发送状态："+portalResult.getSendStatus());

            String advData = mapper.writeValueAsString(chapterWrapper.getObject("ADV"));

            Message advMessage = new Message("ADV", "", advData.getBytes());
            SendResult advResult = producer.send(advMessage);
            log.info("ADV主题消息已经发送：MSGID:"+advResult.getMsgId()+", 发送状态："+advResult.getSendStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
