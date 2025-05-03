package com.bac.bacbackend.data.service.decomp;

import com.bac.bacbackend.domain.common.exceptions.AiPromptException;
import com.bac.bacbackend.domain.port.IOpenAi;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that implements {@link IOpenAi}. This is the LLM used for analysing info extract in our scraping process.
 * We make use of a beta-library as it stands, but it's quite reliable. The same can't be said with the LLM, but
 * that's just how flows, unfortunately.
 */
@Component
public class OpenAi implements IOpenAi {

    @Value("${api.key}")
    private String api;

    /**
     * We build a chat with the LLM, using 40_mini. Mini is chosen because it's the cheapest one and frankly the
     * fastest one too. Speed is important, but if there is a need to change the model you can simply list the model
     * you want after "ChatModel.". The current iteration builds one chat and completes the chat after one prompt.
     * This is so the memory of the previous prompts that came in won't confuse the LLM more than it manages to do
     * itself. The best result comes from creating a brand-new session each time if we are to re-prompt it. It usually
     * does not do the same mistake given new chances with new "seeds".
     *
     * @param command is the static command given with every prompt
     * @param message message is combined of both the title and the summary of an article
     * @return a string containing the answer from the LLM
     */
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
            throw new AiPromptException("AI response failed: " + e.getMessage());
        }
    }
}