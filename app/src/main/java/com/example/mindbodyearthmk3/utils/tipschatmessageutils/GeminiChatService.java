package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.ContentKt;
import com.google.ai.client.generativeai.type.Content;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeminiChatService implements TipsChatService {
    private static final String API_KEY = "YOUR_API_KEY";
    private final GenerativeModel model;
    private final ExecutorService executor;

    public GeminiChatService() {
        model = new GenerativeModel("gemini-pro", API_KEY);
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getTipsChatResponse(String prompt, TipsChatResponseCallback callback) {
        executor.execute(() -> {
            try {
                // Create Content object using the Kotlin extension function
                Content content = ContentKt.content(null, builder -> {
                    builder.addText(prompt);
                    return null;
                });

                // Generate content using the model and handle the response asynchronously
                model.generateContent(content)
                        .thenAccept(response -> {
                            String aiResponse = response.getContent().getText();
                            callback.onResponse(aiResponse);
                        })
                        .exceptionally(e -> {
                            callback.onError(e.getMessage());
                            return null;
                        });

            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        });
    }

    // Don't forget to clean up the executor when you're done
    public void shutdown() {
        executor.shutdown();
    }
}