package com.example.mindbodyearthmk3.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIService {
    private static final String BASE_URL = "https://api.openai.com/v1/";
    private final OkHttpClient client;
    private final ApiKeyManager apiKeyManager;

    public OpenAIService(Context context) throws GeneralSecurityException, IOException {
        apiKeyManager = new ApiKeyManager(context);

        client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + apiKeyManager.getApiKey())
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    public void getChatResponse(String userMessage, ChatCallback callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();

            // Add system message to set context
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant providing tips about mind wellness, body fitness, and environmental consciousness. Keep responses concise and practical.");

            // Add user message
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);

            messages.put(systemMessage);
            messages.put(userMsg);
            jsonBody.put("messages", messages);

        } catch (JSONException e) {
            callback.onFailure(e);
            return;
        }

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "chat/completions")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseData);
                    String assistantMessage = jsonResponse
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                    callback.onSuccess(assistantMessage);
                } catch (JSONException e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    public interface ChatCallback {
        void onSuccess(String response);
        void onFailure(Exception e);
    }
}

