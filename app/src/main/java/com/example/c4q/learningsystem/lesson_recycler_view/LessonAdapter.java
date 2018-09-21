package com.example.c4q.learningsystem.lesson_recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.c4q.learningsystem.R;
import com.example.c4q.learningsystem.models.Lessons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q on 9/9/18.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {
    private List<Lessons> lessonsList;

    public LessonAdapter(List<Lessons> lessonsList) {
        if (lessonsList == null) {
            this.lessonsList = new ArrayList<>();
        } else {
            this.lessonsList = lessonsList;
        }
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lessons_itemview, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lessons lessons = lessonsList.get(position);
        holder.onBind(lessons);
    }

    @Override
    public int getItemCount() {
        return lessonsList.size();
    }
}
