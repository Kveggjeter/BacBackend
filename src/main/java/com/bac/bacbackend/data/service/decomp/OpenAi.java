package com.bac.bacbackend.data.service.decomp;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Big shoutout to:
 * https://github.com/openai/openai-java
 */
@Component
public class OpenAi {

    @Value("${api.key}")
    private String api;

    public String prompt(String command, String message) {
        try {
            OpenAIClient client = OpenAIOkHttpClient.builder()
                    .apiKey(api)
                    .build();

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .model(ChatModel.GPT_4O_MINI)
                    .addDeveloperMessage(command)
                    .addUserMessage(message)
                    .build();

            return client.chat().completions().create(params)
                    .choices().get(0).message().content()
                    .orElse("No Response");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}