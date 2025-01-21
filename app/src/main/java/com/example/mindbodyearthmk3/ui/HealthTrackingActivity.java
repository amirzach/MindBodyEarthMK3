package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.HealthData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HealthTrackingActivity extends AppCompatActivity {
    private AppDatabase db;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private CustomChartView customChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tracking);

        db = AppDatabase.getInstance(this);
        customChartView = findViewById(R.id.customChartView);

        EditText etBloodPressure = findViewById(R.id.etBloodPressure);
        EditText etHeartRate = findViewById(R.id.etHeartRate);
        EditText etWeight = findViewById(R.id.etWeight);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String bloodPressure = etBloodPressure.getText().toString().trim();
            String heartRate = etHeartRate.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();

            if (!bloodPressure.isEmpty() && !heartRate.isEmpty() && !weight.isEmpty()) {
                try {
                    int bp = Integer.parseInt(bloodPressure);
                    int hr = Integer.parseInt(heartRate);
                    float wt = Float.parseFloat(weight);

                    saveHealthData(bp, hr, wt);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        loadHealthData();
    }

    private void saveHealthData(int bloodPressure, int heartRate, float weight) {
        executorService.execute(() -> {
            try {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                HealthData healthData = new HealthData(currentDate, bloodPressure, heartRate, weight);
                db.healthDataDao().insert(healthData);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Health data saved successfully!", Toast.LENGTH_SHORT).show();
                    loadHealthData();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error saving health data.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void loadHealthData() {
        executorService.execute(() -> {
            try {
                List<HealthData> healthDataList = db.healthDataDao().getAll();
                List<Integer> bloodPressureData = new ArrayList<>();

                // Extract blood pressure values for plotting
                for (HealthData data : healthDataList) {
                    bloodPressureData.add(data.getBloodPressure());
                }

                runOnUiThread(() -> {
                    customChartView.setDataPoints(bloodPressureData);
                    customChartView.invalidate(); // Redraw the chart
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error loading health data.", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
