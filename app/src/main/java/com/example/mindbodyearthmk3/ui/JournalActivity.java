package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.Days;
import com.example.mindbodyearthmk3.database.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JournalActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        TextView tvDay = findViewById(R.id.tvDay);
        TextView tvDate = findViewById(R.id.tvDate);
        EditText etTitle = findViewById(R.id.etJournalTitle);
        EditText etContent = findViewById(R.id.etJournalContent);

        // Get the current day and date
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDay = dayFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());

        tvDay.setText(currentDay);
        tvDate.setText(currentDate);

        AppDatabase db = AppDatabase.getInstance(this);

        // Save journal entry
        findViewById(R.id.btnSaveJournal).setOnClickListener(view -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please enter title and content.", Toast.LENGTH_SHORT).show();
                return;
            }

            executorService.execute(() -> {
                try {
                    Days day = db.daysDao().findByDate(currentDate);
                    if (day == null) {
                        day = new Days(currentDay, currentDate);
                        db.daysDao().insert(day);
                        day = db.daysDao().findByDate(currentDate); // Fetch again to get the ID
                    }

                    JournalEntry entry = new JournalEntry(day.getDayId(), title, content);
                    db.journalEntryDao().insert(entry);

                    runOnUiThread(() -> Toast.makeText(this, "Journal entry saved.", Toast.LENGTH_SHORT).show());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error saving journal.", Toast.LENGTH_SHORT).show());
                }
            });
        });

        // View current day's journals
        findViewById(R.id.btnViewCurrentJournal).setOnClickListener(view -> {
            executorService.execute(() -> {
                try {
                    List<JournalEntry> entries = db.journalEntryDao().findByDate(currentDate);
                    runOnUiThread(() -> {
                        if (entries != null && !entries.isEmpty()) {
                            Toast.makeText(this, "Found " + entries.size() + " entries for today.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No entries for today.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error fetching today's journals.", Toast.LENGTH_SHORT).show());
                }
            });
        });

        // View past journals
        findViewById(R.id.btnViewPastJournal).setOnClickListener(view -> {
            executorService.execute(() -> {
                try {
                    List<JournalEntry> pastEntries = db.journalEntryDao().findPastEntries(currentDate);
                    runOnUiThread(() -> {
                        if (pastEntries != null && !pastEntries.isEmpty()) {
                            Toast.makeText(this, "Found " + pastEntries.size() + " past entries.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No past entries found.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error fetching past journals.", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Ensure the executor service is properly shut down
    }
}
