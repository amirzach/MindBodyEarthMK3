package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.TipsChatMessage;
import com.example.mindbodyearthmk3.database.TipsChatMessageDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TipsChatRepository {
    private final TipsChatMessageDao tipsChatMessageDao;
    private final LiveData<List<TipsChatMessage>> allMessages;
    private final GeminiChatService chatService;
    private final ExecutorService executor;

    public TipsChatRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        tipsChatMessageDao = database.tipsChatMessageDao();
        allMessages = tipsChatMessageDao.getAllMessages();
        chatService = new GeminiChatService();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertMessage(TipsChatMessage message) {
        executor.execute(() -> tipsChatMessageDao.insertTipsChatMessage(message));
    }

    public LiveData<List<TipsChatMessage>> getAllMessages() {
        return allMessages;
    }

    public void clearChatHistory() {
        executor.execute(() -> tipsChatMessageDao.clearAllMessages());
    }

    public void sendMessage(String userMessage) {
        String contextualPrompt = TipsPromptGenerator.generateContextualPrompt(userMessage);

        // Insert user message
        insertMessage(new TipsChatMessage(userMessage, true));

        // Get AI response
        chatService.getTipsChatResponse(contextualPrompt, new TipsChatResponseCallback() {
            @Override
            public void onResponse(String aiResponse) {
                insertMessage(new TipsChatMessage(aiResponse, false));
            }

            @Override
            public void onError(String error) {
                insertMessage(new TipsChatMessage("Error: " + error, false));
            }
        });
    }
}