package com.example.mindbodyearthmk3.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Days.class,
        parentColumns = "dayId",
        childColumns = "dayId",
        onDelete = ForeignKey.CASCADE))
public class WorkoutPlan {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workout_plan_id")
    private Long id;
    private int dayId;

    public WorkoutPlan(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }
}
