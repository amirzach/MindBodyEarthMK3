package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.MealPlan;
import com.example.mindbodyearthmk3.database.MealPlanDao;
import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprint;
import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprintDao;
import com.example.mindbodyearthmk3.database.carbonfootprint.EnergyConsumption;
import com.example.mindbodyearthmk3.database.carbonfootprint.Transportation;
import com.example.mindbodyearthmk3.database.carbonfootprint.Waste;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarbonFootprintTrackerActivity extends AppCompatActivity {

    // UI Components
    private EditText etElectricityUsage;
    private EditText etGasUsage;
    private Spinner spTransportMode;
    private EditText etDistance;
    private EditText etFuelEfficiency;
    private EditText etWasteProduced;
    private EditText etWasteRecycled;
    private TextView tvTotalFootprint;
    private Button btnCalculate;
    private Button btnSave;

    // New UI Components for detailed footprint display
    private TextView tvEnergyFootprint;
    private TextView tvTransportFootprint;
    private TextView tvWasteFootprint;
    private TextView tvMealFootprint;

    // Database components
    private CarbonFootprintDao carbonFootprintDao;
    private MealPlanDao mealPlanDao;
    private ExecutorService executorService;

    // Constants for emission factors
    private static final double ELECTRICITY_EMISSION_FACTOR = 0.5; // kgCO2/kWh
    private static final double GAS_EMISSION_FACTOR = 2.0; // kgCO2/m3
    private static final double WASTE_EMISSION_FACTOR = 2.86; // kgCO2/kg
    private static final double MEAL_EMISSION_FACTOR = 2.5; // kgCO2/1000kcal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint_tracker);

        // Initialize database components
        AppDatabase db = AppDatabase.getInstance(this);
        carbonFootprintDao = db.carbonFootprintDao();
        mealPlanDao = db.mealPlanDao();
        executorService = Executors.newSingleThreadExecutor();

        initializeViews();
        setupSpinner();
        setupListeners();
    }

    private void initializeViews() {
        etElectricityUsage = findViewById(R.id.et_electricity_usage);
        etGasUsage = findViewById(R.id.et_gas_usage);
        spTransportMode = findViewById(R.id.sp_transport_mode);
        etDistance = findViewById(R.id.et_distance);
        etFuelEfficiency = findViewById(R.id.et_fuel_efficiency);
        etWasteProduced = findViewById(R.id.et_waste_produced);
        etWasteRecycled = findViewById(R.id.et_waste_recycled);
        tvTotalFootprint = findViewById(R.id.tv_total_footprint);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnSave = findViewById(R.id.btn_save);
        // New view initializations
        tvEnergyFootprint = findViewById(R.id.tv_energy_footprint);
        tvTransportFootprint = findViewById(R.id.tv_transport_footprint);
        tvWasteFootprint = findViewById(R.id.tv_waste_footprint);
        tvMealFootprint = findViewById(R.id.tv_meal_footprint);
    }

    private void setupSpinner() {
        String[] transportModes = new String[]{
                "Public Transport",
                "Private Vehicle (Petrol)",
                "Private Vehicle (Electric)",
                "Bicycle",
                "Walking"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                transportModes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransportMode.setAdapter(adapter);
    }

    private void setupListeners() {
        btnCalculate.setOnClickListener(v -> calculateFootprint());
        btnSave.setOnClickListener(v -> saveFootprint());
    }

    private void calculateFootprint() {
        try {
            // Get energy consumption values
            double electricityUsage = getDoubleFromEditText(etElectricityUsage);
            double gasUsage = getDoubleFromEditText(etGasUsage);

            // Get transportation values
            String transportMode = spTransportMode.getSelectedItem().toString();
            double distance = getDoubleFromEditText(etDistance);
            double fuelEfficiency = getDoubleFromEditText(etFuelEfficiency);

            // Get waste values
            double wasteProduced = getDoubleFromEditText(etWasteProduced);
            double wasteRecycled = getDoubleFromEditText(etWasteRecycled);

            executorService.execute(() -> {
                try {
                    // Get current meal plan ID
                    long mealPlanId = mealPlanDao.getCurrentMealPlanId();

                    // Calculate individual footprints
                    double energyFootprint = calculateEnergyFootprint(electricityUsage, gasUsage);
                    double transportFootprint = calculateTransportFootprint(transportMode, distance, fuelEfficiency);
                    double wasteFootprint = calculateWasteFootprint(wasteProduced, wasteRecycled);

                    // Get meal plan footprint using the DAO
                    int totalCalories = mealPlanDao.getMealPlanTotalCalories(mealPlanId);
                    double mealPlanFootprint = (totalCalories / 1000.0) * MEAL_EMISSION_FACTOR;

                    // Calculate total footprint
                    double totalFootprint = energyFootprint + transportFootprint + wasteFootprint + mealPlanFootprint;

                    // Update UI on main thread with all footprint components
                    runOnUiThread(() -> {
                        // Update individual footprint components
                        tvEnergyFootprint.setText(String.format("Energy Footprint: %.2f kgCO2", energyFootprint));
                        tvTransportFootprint.setText(String.format("Transport Footprint: %.2f kgCO2", transportFootprint));
                        tvWasteFootprint.setText(String.format("Waste Footprint: %.2f kgCO2", wasteFootprint));
                        tvMealFootprint.setText(String.format("Meal Footprint: %.2f kgCO2 (%d calories)",
                                mealPlanFootprint, totalCalories));

                        // Update total footprint
                        tvTotalFootprint.setText(String.format("Total Carbon Footprint: %.2f kgCO2", totalFootprint));

                        btnSave.setEnabled(true);
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(CarbonFootprintTrackerActivity.this,
                                "Error calculating footprint", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFootprint() {
        try {
            // Create objects with current values
            EnergyConsumption energyConsumption = new EnergyConsumption(
                    getDoubleFromEditText(etElectricityUsage),
                    getDoubleFromEditText(etGasUsage)
            );
            energyConsumption.setElectricityEmissionFactor(ELECTRICITY_EMISSION_FACTOR);
            energyConsumption.setGasEmissionFactor(GAS_EMISSION_FACTOR);

            Transportation transportation = new Transportation(
                    spTransportMode.getSelectedItem().toString(),
                    getDoubleFromEditText(etDistance),
                    getDoubleFromEditText(etFuelEfficiency)
            );
            transportation.setTransportationEmissionFactor(getTransportEmissionFactor(
                    spTransportMode.getSelectedItem().toString()
            ));

            Waste waste = new Waste(
                    getDoubleFromEditText(etWasteProduced),
                    getDoubleFromEditText(etWasteRecycled)
            );
            waste.setWasteEmissionFactor(WASTE_EMISSION_FACTOR);

            executorService.execute(() -> {
                try {
                    // Get current meal plan
                    long mealPlanId = mealPlanDao.getCurrentMealPlanId();
                    MealPlan mealPlan = mealPlanDao.getMealPlan(mealPlanId);

                    // Create and save carbon footprint
                    CarbonFootprint carbonFootprint = new CarbonFootprint(
                            0, // ID will be auto-generated
                            energyConsumption,
                            transportation,
                            mealPlan,
                            waste
                    );
                    carbonFootprint.setMealEmissionFactor(MEAL_EMISSION_FACTOR);

                    carbonFootprintDao.insertCarbonFootprint(carbonFootprint);

                    runOnUiThread(() -> {
                        Toast.makeText(CarbonFootprintTrackerActivity.this,
                                "Carbon footprint saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(CarbonFootprintTrackerActivity.this,
                                "Error saving carbon footprint", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error preparing data", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateEnergyFootprint(double electricityUsage, double gasUsage) {
        return (electricityUsage * ELECTRICITY_EMISSION_FACTOR) + (gasUsage * GAS_EMISSION_FACTOR);
    }

    private double calculateTransportFootprint(String mode, double distance, double fuelEfficiency) {
        double emissionFactor = getTransportEmissionFactor(mode);
        return distance * emissionFactor * (1 - (fuelEfficiency / 100));
    }

    private double calculateWasteFootprint(double produced, double recycled) {
        return (produced - recycled) * WASTE_EMISSION_FACTOR;
    }

    private double getTransportEmissionFactor(String mode) {
        switch (mode) {
            case "Public Transport":
                return 0.04; // kgCO2/km
            case "Private Vehicle (Petrol)":
                return 0.2; // kgCO2/km
            case "Private Vehicle (Electric)":
                return 0.05; // kgCO2/km
            case "Bicycle":
            case "Walking":
                return 0.0;
            default:
                return 0.2;
        }
    }

    private double getDoubleFromEditText(EditText editText) throws NumberFormatException {
        String text = editText.getText().toString().trim();
        if (text.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}