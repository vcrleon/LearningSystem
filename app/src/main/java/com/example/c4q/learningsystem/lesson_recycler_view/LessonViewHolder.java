package com.example.c4q.learningsystem.lesson_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.c4q.learningsystem.R;
import com.example.c4q.learningsystem.models.Lessons;

/**
 * Created by c4q on 9/9/18.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {
    private TextView lessonTitle;
    private TextView lessonDate;
    private TextView lessonTime;

    public LessonViewHolder(View itemView) {
        super(itemView);

        lessonTitle = itemView.findViewById(R.id.lesson_title);
        lessonDate = itemView.findViewById(R.id.lesson_date);
        lessonTime = itemView.findViewById(R.id.lesson_time);

    }

    public void onBind(Lessons lessons) {
        lessonTitle.setText(lessons.getTitle());
        lessonDate.setText(lessons.getDate());
        lessonTime.setText(lessons.getTime());
    }

}
