package com.cc.gptselfspringboot.controller;

import com.cc.gptselfspringboot.VO.MessageVO;
import com.cc.gptselfspringboot.gpt4all.LLModel;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;


@RestController
@Slf4j
public class GPTController {

    private String model = "C:\\docker_volumes\\gpt\\mistral-7b-openorca.Q4_0.gguf";

    @GetMapping("/api/generateText/{question}")
    public String generateText(@PathVariable(name = "question", required = false)  String question) {
        if (question == null || question.isEmpty()) {
            question = "who are you";
        }
        String prompt = "### Human:\n "+question+" \n### Assistant:";
        LLModel.LIBRARY_SEARCH_PATH = getResourcesFolderPath();

        LLModel mptModel = new LLModel(Path.of(model));
        LLModel.GenerationConfig config = LLModel.config()
                .withNPredict(4096)
                .withRepeatLastN(64)
                .build();

        return mptModel.generate(prompt, config, true);
    }
    // 流式回复
    @PostMapping(value = "/api/generateStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateStream(@RequestBody MessageVO[] messageVOS) {
        String prompt =  convert(messageVOS);
        log.info(prompt);
        LLModel.LIBRARY_SEARCH_PATH = getResourcesFolderPath();

        LLModel mptModel = new LLModel(Path.of(model));
        LLModel.GenerationConfig config = LLModel.config()
                .withNPredict(4096)
                .withRepeatLastN(64)
                .build();

        return mptModel.generateStream(prompt, config, true);
    }

    private String convert(MessageVO[] messageVOS) {
        StringBuilder sb = new StringBuilder();
        for (MessageVO item : messageVOS) {
            sb.append(" ### Human:\\n ");
            sb.append(item.getHuman());
            sb.append(" ### Assistant:\\n ");
            if (StringUtils.isNotBlank(item.getAssistant())){
                sb.append(item.getAssistant());
            }
        }
        return sb.toString();
    }

    private String getResourcesFolderPath() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource("lib");
        if (resourceUrl != null) {
            File resourceFile = new File(resourceUrl.getFile());
            return resourceFile.getAbsolutePath();
        } else {
            return null;
        }
    }
}




