package com.cc.gptselfspringboot.controller;

import com.cc.gptselfspringboot.gpt4all.LLModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

@RestController
public class GPTController {

    String model = "mistral-7b-openorca.Q4_0.gguf";

    @GetMapping("/api/generateText/{question}")
    public String generateText(@PathVariable(name = "question", required = false)  String question) {
        if (question == null || question.isEmpty()) {
            question = "who are you";
        }
        String prompt = "### Human:\n "+question+" \n### Assistant:";
        LLModel.LIBRARY_SEARCH_PATH = getResourcesFolderPath("lib");

        LLModel mptModel = new LLModel(Path.of(getResourcesFolderPath("models")+File.separator+model));
        LLModel.GenerationConfig config = LLModel.config()
                .withNPredict(4096)
                .withRepeatLastN(64)
                .build();

        return mptModel.generate(prompt, config, true);
    }
    private String getResourcesFolderPath(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(path);
        if (resourceUrl != null) {
            File resourceFile = new File(resourceUrl.getFile());
            return resourceFile.getAbsolutePath();
        } else {
            return null;
        }
    }
}




