package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

import android.os.Looper;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class GeminiChatService implements TipsChatService {
    private static final String API_KEY = "AIzaSyA3yeKpUwo3sYNm4OYmMQMvZ7uIgdhRXPw";
    private final GenerativeModel generativeModel;
    private final Executor executor;

    public GeminiChatService() {
        generativeModel = new GenerativeModel.Builder()
                .setApiKey(API_KEY)
                .setModel("gemini-pro")
                .build();
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getTipsChatResponse(String prompt,TipsChatResponseCallback callback) {
        executor.execute(() -> {
            try {
                GenerativeModelResponse response = generativeModel.generateText(prompt);
                String aiResponse = response.getText();

                new Handler(Looper.getMainLooper()).post(() ->
                        callback.onResponse(aiResponse)
                );
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() ->
                        callback.onError("Error: " + e.getMessage())
                );
            }
        });
    }
}
