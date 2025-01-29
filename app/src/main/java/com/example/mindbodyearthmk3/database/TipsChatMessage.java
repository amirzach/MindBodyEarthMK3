package com.example.mindbodyearthmk3.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipsChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    private int id;
    private String textMessage;
    private boolean isUserMessage;
    private long timestamp;

    public TipsChatMessage(String textMessage, boolean isUserMessage) {
        this.textMessage = textMessage;
        this.isUserMessage = isUserMessage;
        timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public boolean isUserMessage() {
        return isUserMessage;
    }

    public void setUserMessage(boolean userMessage) {
        isUserMessage = userMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
