package com.example.mindbodyearthmk3.database.carbonfootprint;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CarbonFootprintDao {
    @Insert
    void insertCarbonFootprint(CarbonFootprint carbonFootprint);

    @Update
    void updateCarbonFootprint(CarbonFootprint carbonFootprint);

    @Delete
    void deleteCarbonFootprint(CarbonFootprint carbonFootprint);

    @Query("SELECT (electricityUsage * electricityEmissionFactor) + (gasUsage * gasEmissionFactor) + (transportationEmissionFactor * distanceTravelled) + ((wasteProduced - wasteRecycled) * wasteEmissionFactor) + ((SELECT SUM(totalCalories) FROM Meal WHERE mealPlanId = :mealPlanId) * mealEmissionFactor) AS totalFootprint FROM CarbonFootprint WHERE carbon_footprint_id = :carbonFootprintId")
    double getTotalFootprint(long carbonFootprintId, long mealPlanId);

    @Query("SELECT * FROM CarbonFootprint WHERE carbon_footprint_id = :carbonFootprintId")
    CarbonFootprint getById(long carbonFootprintId);
}
