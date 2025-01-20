package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface JournalEntryDao {

    @Insert
    void insert(JournalEntry journalEntry);

    @Query("SELECT * FROM journal_entries WHERE dayId = :dayId")
    List<JournalEntry> findByDayId(int dayId);

    @Query("SELECT * FROM journal_entries WHERE dayId IN (SELECT dayId FROM days WHERE date = :date)")
    List<JournalEntry> findByDate(String date);

    @Query("SELECT * FROM journal_entries WHERE dayId IN (SELECT dayId FROM days WHERE date < :currentDate)")
    List<JournalEntry> findPastEntries(String currentDate);

    @Query("SELECT * FROM journal_entries")
    List<JournalEntry> getAllEntries();
}
