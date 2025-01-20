package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "journal_entries",
        foreignKeys = @ForeignKey(
                entity = Days.class,
                parentColumns = "dayId",
                childColumns = "dayId",
                onDelete = ForeignKey.CASCADE
        )
)
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int journalId;

    private int dayId; // Foreign key to Days table
    private String title;
    private String content;

    // Constructor
    public JournalEntry(int dayId, String title, String content) {
        this.dayId = dayId;
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
