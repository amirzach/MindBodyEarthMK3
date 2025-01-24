package com.example.mindbodyearthmk3.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder> {

    private ArrayList<Workout> workoutList;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private final TextView workoutName;
        private final TextView workoutType;
        private final TextView workoutSets;
        private final TextView workoutReps;

        public WorkoutViewHolder(View v) {
            super(v);

            //define click listeners for the workoutName, workoutType, workoutSets, and workoutReps
            workoutName = v.findViewById(R.id.tvWorkoutName);
            workoutType = v.findViewById(R.id.tvWorkoutType);
            workoutSets = v.findViewById(R.id.tvWorkoutSets);
            workoutReps = v.findViewById(R.id.tvWorkoutReps);
        }

        public TextView getWorkoutName() {
            return workoutName;
        }

        public TextView getWorkoutType() {
            return workoutType;
        }

        public TextView getWorkoutSets() {
            return workoutSets;
        }

        public TextView getWorkoutReps() {
            return workoutReps;
        }
    }

    public WorkoutListAdapter(ArrayList<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    //create new views (invoked by the layout manager)
    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_workout_list_rows, parent, false);
        return new WorkoutViewHolder(v);
    }

    //replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        Workout currentWorkout = workoutList.get(position);
        holder.getWorkoutName().setText(currentWorkout.getWorkoutName());
        holder.getWorkoutType().setText(currentWorkout.getType());
        holder.getWorkoutSets().setText(String.valueOf(currentWorkout.getSets()));
        holder.getWorkoutReps().setText(String.valueOf(currentWorkout.getReps()));
    }

    //return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void updateWorkouts(List<Workout> newWorkouts) {
        this.workoutList = new ArrayList<>(newWorkouts);
        notifyDataSetChanged();
    }

    public Workout getWorkoutAt(int position) {
        return workoutList.get(position);
    }

    public void removeItem(int position) {
        workoutList.remove(position);
        notifyItemRemoved(position);
    }

}
