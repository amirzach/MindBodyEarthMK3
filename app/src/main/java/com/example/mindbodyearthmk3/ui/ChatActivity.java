package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.ChatMessage;
import com.example.mindbodyearthmk3.utils.ApiKeyManager;
import com.example.mindbodyearthmk3.utils.ChatAdapter;
import com.example.mindbodyearthmk3.utils.OpenAIService;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private EditText messageInput;
    private Button sendButton;
    private OpenAIService openAIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            ApiKeyManager apiKeyManager = new ApiKeyManager(this);
            apiKeyManager.saveApiKey(); // Save the API key securely on first launch
            openAIService = new OpenAIService(this);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(this, "Error initializing security", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();
        recyclerView.setAdapter(chatAdapter);

        try {
            openAIService = new OpenAIService(this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageInput.setText("");
            }
        });
    }

    private void sendMessage(String message) {
        // Add user message to chat
        chatAdapter.addMessage(new ChatMessage(message, true));

        // Get bot response
        openAIService.getChatResponse(message, new OpenAIService.ChatCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    chatAdapter.addMessage(new ChatMessage(response, false));
                    recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(ChatActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
