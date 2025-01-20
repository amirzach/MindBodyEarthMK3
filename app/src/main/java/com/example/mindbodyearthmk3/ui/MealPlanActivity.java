package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.Food;
import com.example.mindbodyearthmk3.database.Meal;
import com.example.mindbodyearthmk3.database.MealPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MealPlanActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private AppDatabase db;
    private TextView totalCaloriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        totalCaloriesView = findViewById(R.id.tvTotalCalories);
        db = AppDatabase.getInstance(this);

        loadWeeklyCalories();

        Button editMealsButton = findViewById(R.id.btnEditMeals);
        editMealsButton.setOnClickListener(v -> showEditMealsDialog());
    }

    private void loadWeeklyCalories() {
        executorService.execute(() -> {
            try {
                List<MealPlan> allMealPlans = db.mealPlanDao().getAll();
                int totalCalories = 0;

                for (MealPlan plan : allMealPlans) {
                    totalCalories += calculateCaloriesForMealPlan(plan.getMealPlanId());
                }

                int finalTotalCalories = totalCalories;
                runOnUiThread(() -> totalCaloriesView.setText(String.format("Total Weekly Calories: %d", finalTotalCalories)));
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error calculating weekly calories: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private int calculateCaloriesForMealPlan(long mealPlanId) {
        List<Meal> meals = db.mealDao().findByMealPlanId(mealPlanId);
        int totalCalories = 0;
        for (Meal meal : meals) {
            totalCalories += meal.getTotalCalories();
        }
        return totalCalories;
    }

    private void showEditMealsDialog() {
        executorService.execute(() -> {
            try {
                List<MealPlan> allMealPlans = db.mealPlanDao().getAll();
                List<String> days = new ArrayList<>();
                for (MealPlan plan : allMealPlans) {
                    days.add(plan.getDay());
                }

                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Select Day to Edit Meals");
                    builder.setItems(days.toArray(new String[0]), (dialog, which) -> {
                        String selectedDay = days.get(which);
                        showMealEditDialog(selectedDay);
                    });
                    builder.show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error loading days.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showMealEditDialog(String day) {
        executorService.execute(() -> {
            try {
                MealPlan mealPlan = db.mealPlanDao().findByDay(day);
                if (mealPlan == null) {
                    runOnUiThread(() -> Toast.makeText(this, "No meals found for " + day, Toast.LENGTH_SHORT).show());
                    return;
                }

                List<Meal> meals = db.mealDao().findByMealPlanId(mealPlan.getMealPlanId());

                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Edit Meals for " + day);

                    List<String> mealDescriptions = new ArrayList<>();
                    for (Meal meal : meals) {
                        mealDescriptions.add(meal.getTimeOfDayConsumed() + " - " + meal.getTotalCalories() + " cal");
                    }

                    builder.setItems(mealDescriptions.toArray(new String[0]), (dialog, which) -> {
                        Meal selectedMeal = meals.get(which);
                        showFoodEditDialog(selectedMeal);
                    });

                    builder.setNegativeButton("Add Meal", (dialog, which) -> showAddMealDialog(day));
                    builder.show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error loading meals.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showFoodEditDialog(Meal meal) {
        executorService.execute(() -> {
            try {
                List<Food> foods = db.foodDao().findByMealId(meal.getMealId());

                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Edit Foods for " + meal.getTimeOfDayConsumed());

                    List<String> foodDescriptions = new ArrayList<>();
                    for (Food food : foods) {
                        foodDescriptions.add(food.getFoodName() + " - " + food.getCalories() + " cal");
                    }

                    builder.setItems(foodDescriptions.toArray(new String[0]), (dialog, which) -> {
                        executorService.execute(() -> {
                            db.foodDao().delete(foods.get(which));
                            updateMealCalories(meal);
                        });
                    });

                    builder.setNegativeButton("Add Food", (dialog, which) -> showAddFoodDialog(meal));
                    builder.show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error loading foods.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void updateMealCalories(Meal meal) {
        executorService.execute(() -> {
            try {
                List<Food> foods = db.foodDao().findByMealId(meal.getMealId());
                int totalCalories = 0;
                for (Food food : foods) {
                    totalCalories += food.getCalories();
                }
                meal.setTotalCalories(totalCalories);
                db.mealDao().update(meal);
                loadWeeklyCalories();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error updating meal calories.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void showAddMealDialog(String day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Meal");

        EditText timeOfDayInput = new EditText(this);
        timeOfDayInput.setHint("Time of Day (e.g., Breakfast)");

        builder.setView(timeOfDayInput);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String timeOfDay = timeOfDayInput.getText().toString().trim();
            if (!timeOfDay.isEmpty()) {
                executorService.execute(() -> {
                    MealPlan mealPlan = db.mealPlanDao().findByDay(day);
                    if (mealPlan != null) {
                        Meal meal = new Meal();
                        meal.setTimeOfDayConsumed(timeOfDay);
                        meal.setDay(day);
                        meal.setMealPlanId(mealPlan.getMealPlanId());
                        db.mealDao().insert(meal);
                        showMealEditDialog(day);
                    }
                });
            }
        });
        builder.show();
    }

    private void showAddFoodDialog(Meal meal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Food");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        EditText foodNameInput = new EditText(this);
        foodNameInput.setHint("Food Name");
        layout.addView(foodNameInput);

        EditText caloriesInput = new EditText(this);
        caloriesInput.setHint("Calories");
        layout.addView(caloriesInput);

        builder.setView(layout);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String foodName = foodNameInput.getText().toString().trim();
            String caloriesText = caloriesInput.getText().toString().trim();

            if (!foodName.isEmpty() && !caloriesText.isEmpty()) {
                try {
                    int calories = Integer.parseInt(caloriesText);
                    if (calories > 0) {
                        executorService.execute(() -> {
                            Food food = new Food();
                            food.setFoodName(foodName);
                            food.setCalories(calories);
                            food.setMealId(meal.getMealId());
                            db.foodDao().insert(food);
                            updateMealCalories(meal);
                        });
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid calorie input.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
