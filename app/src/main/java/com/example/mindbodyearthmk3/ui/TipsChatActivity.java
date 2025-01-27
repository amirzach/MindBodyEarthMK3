package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.utils.tipschatmessageutils.TipsChatAdapter;
import com.example.mindbodyearthmk3.utils.tipschatmessageutils.TipsChatViewModel;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TipsChatActivity extends AppCompatActivity {
    private TipsChatViewModel tipsChatViewModel;
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private TipsChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_chat);

        // Initialize ViewModel
        tipsChatViewModel = new ViewModelProvider(this).get(TipsChatViewModel.class);

        // Setup RecyclerView
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new TipsChatAdapter();
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Message input and send button
        messageInput = findViewById(R.id.messageInputEditText);
        Button sendButton = findViewById(R.id.sendButton);

        // Observe chat messages
        tipsChatViewModel.getAllMessages().observe(this, messages -> {
            chatAdapter.submitList(messages);
            if (!messages.isEmpty()) {
                chatRecyclerView.scrollToPosition(messages.size() - 1);
            }
        });

        // Send message
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                tipsChatViewModel.sendMessage(message);
                messageInput.setText("");
            }
        });
    }
}