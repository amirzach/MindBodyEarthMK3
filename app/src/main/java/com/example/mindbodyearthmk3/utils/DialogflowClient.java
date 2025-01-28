package com.example.mindbodyearthmk3.utils;

import android.content.Context;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.InputStream;

public class DialogflowClient {
    private static final String PROJECT_ID = "mindbodyearth";
    private static final String SESSION_ID = "mindbodyearth"; // Can be dynamically generated
    private SessionsClient sessionsClient;
    private SessionName session;

    public DialogflowClient(Context context) {
        try {
            // Load credentials from your service account JSON file located in the assets folder
            InputStream credentialsStream = context.getAssets().open("keen-frame-449212-c3-e9208fd77d28.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Initialize SessionsClient with credentials
            sessionsClient = SessionsClient.create();
            session = SessionName.of(PROJECT_ID, SESSION_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResponseFromDialogflow(String userInput) {
        try {
            if (sessionsClient == null) {
                throw new IllegalStateException("SessionsClient is not initialized!");
            }

            TextInput textInput = TextInput.newBuilder()
                    .setText(userInput)
                    .setLanguageCode("en")
                    .build();

            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            return response.getQueryResult().getFulfillmentText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}
