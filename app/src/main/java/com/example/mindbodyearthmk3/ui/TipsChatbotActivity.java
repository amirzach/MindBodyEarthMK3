package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.utils.DialogflowClient;

public class TipsChatbotActivity extends AppCompatActivity {

    private EditText userInputEditText;
    private Button sendButton;
    private TextView chatHistoryTextView;
    private ScrollView scrollView;

    private DialogflowClient dialogflowClient; // Assuming you have a DialogflowClient class to handle the communication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_chatbot); // Ensure you use the correct layout file name

        // Initialize UI elements
        userInputEditText = findViewById(R.id.userInputEditText);
        sendButton = findViewById(R.id.sendButton);
        chatHistoryTextView = findViewById(R.id.chatHistoryTextView);
        scrollView = findViewById(R.id.svTipsChatbot);

        // Initialize Dialogflow client with context
        dialogflowClient = new DialogflowClient(this);

        // Set up button click listener
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString();
                if (!userInput.isEmpty()) {
                    // Display the user's message
                    appendMessage("You: " + userInput);
                    // Get the chatbot response from Dialogflow
                    String botResponse = dialogflowClient.getResponseFromDialogflow(userInput);
                    // Display the chatbot's response
                    appendMessage("Bot: " + botResponse);
                }
            }
        });
    }

    // Method to append messages to the chat history
    private void appendMessage(String message) {
        String currentText = chatHistoryTextView.getText().toString();
        chatHistoryTextView.setText(currentText + "\n" + message);

        // Automatically scroll to the bottom
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        // Clear the input field
        userInputEditText.setText("");
    }
}
