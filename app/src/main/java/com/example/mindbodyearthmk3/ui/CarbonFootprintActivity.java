package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprint;
import com.example.mindbodyearthmk3.database.carbonfootprint.CarbonFootprintDao;

public class CarbonFootprintActivity extends AppCompatActivity {
    private TextView energyFootprintTextView;
    private TextView transportationFootprintTextView;
    private TextView wasteFootprintTextView;
    private TextView totalFootprintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint);

        // Initialize UI components
        energyFootprintTextView = findViewById(R.id.energyFootprintTextView);
        transportationFootprintTextView = findViewById(R.id.transportationFootprintTextView);
        wasteFootprintTextView = findViewById(R.id.wasteFootprintTextView);
        totalFootprintTextView = findViewById(R.id.totalFootprintTextView);

        // Load Carbon Footprint data
        AppDatabase database = AppDatabase.getInstance(getApplicationContext());
        CarbonFootprintDao carbonFootprintDao = database.carbonFootprintDao();

        // Example ID for fetching the CarbonFootprint
        long carbonFootprintId = 1;

        // Fetch the CarbonFootprint object
        CarbonFootprint carbonFootprint = carbonFootprintDao.getById(carbonFootprintId);

        if (carbonFootprint != null) {
            // Calculate individual footprints
            double energyFootprint = calculateEnergyFootprint(carbonFootprint);
            double transportationFootprint = calculateTransportationFootprint(carbonFootprint);
            double wasteFootprint = calculateWasteFootprint(carbonFootprint);
            double totalFootprint = energyFootprint + transportationFootprint + wasteFootprint;

            // Display results
            energyFootprintTextView.setText(String.format("Energy Footprint: %.2f kg CO2", energyFootprint));
            transportationFootprintTextView.setText(String.format("Transportation Footprint: %.2f kg CO2", transportationFootprint));
            wasteFootprintTextView.setText(String.format("Waste Footprint: %.2f kg CO2", wasteFootprint));
            totalFootprintTextView.setText(String.format("Total Carbon Footprint: %.2f kg CO2", totalFootprint));
        } else {
            Toast.makeText(this, "Carbon Footprint data not found!", Toast.LENGTH_LONG).show();
        }
    }

    private double calculateEnergyFootprint(CarbonFootprint carbonFootprint) {
        double electricityFootprint = carbonFootprint.getEnergyConsumption().getElectricityUsage() *
                carbonFootprint.getEnergyConsumption().getElectricityEmissionFactor();
        double gasFootprint = carbonFootprint.getEnergyConsumption().getGasUsage() *
                carbonFootprint.getEnergyConsumption().getGasEmissionFactor();
        return electricityFootprint + gasFootprint;
    }

    private double calculateTransportationFootprint(CarbonFootprint carbonFootprint) {
        return carbonFootprint.getTransportation().getDistanceTravelled() *
                carbonFootprint.getTransportation().getTransportationEmissionFactor();
    }

    private double calculateWasteFootprint(CarbonFootprint carbonFootprint) {
        return (carbonFootprint.getWaste().getWasteProduced() - carbonFootprint.getWaste().getWasteRecycled()) *
                carbonFootprint.getWaste().getWasteEmissionFactor();
    }
}
