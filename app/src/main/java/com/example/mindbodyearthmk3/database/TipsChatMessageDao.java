package com.example.mindbodyearthmk3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TipsChatMessageDao {
    @Insert
    void insertTipsChatMessage(TipsChatMessage tipsChatMessage);

    @Query("SELECT * FROM TipsChatMessage ORDER BY timestamp ASC")
    LiveData<List<TipsChatMessage>> getAllMessages();

    @Query("DELETE FROM TipsChatMessage")
    void clearAllMessages();
}
