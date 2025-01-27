package com.example.mindbodyearthmk3.utils.tipschatmessageutils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mindbodyearthmk3.database.TipsChatMessage;
import com.example.mindbodyearthmk3.utils.tipschatmessageutils.TipsChatRepository;

import java.util.List;

public class TipsChatViewModel extends AndroidViewModel {
    private final TipsChatRepository tipsChatRepository;
    private final LiveData<List<TipsChatMessage>> allMessages;

    public TipsChatViewModel(@NonNull Application application) {
        super(application);
        tipsChatRepository = new TipsChatRepository(application);
        allMessages = tipsChatRepository.getAllMessages();
    }

    public LiveData<List<TipsChatMessage>> getAllMessages() {
        return allMessages;
    }

    public void sendMessage(String message) {
        if (isValidMessage(message)) {
            tipsChatRepository.sendMessage(message);
        }
    }

    public void clearChatHistory() {
        tipsChatRepository.clearChatHistory();
    }

    private boolean isValidMessage(String message) {
        return message != null &&
                message.length() > 2 &&
                message.length() < 500;
    }
}