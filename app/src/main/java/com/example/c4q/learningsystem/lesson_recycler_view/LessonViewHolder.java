package com.example.c4q.learningsystem.lesson_recycler_view;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.c4q.learningsystem.LessonDetails;
import com.example.c4q.learningsystem.R;
import com.example.c4q.learningsystem.models.Lessons;

/**
 * Created by c4q on 9/9/18.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {
    private TextView lessonTitle;
    private TextView lessonDate;
    private TextView lessonTime;
    private CardView lessonCard;

    public LessonViewHolder(View itemView) {
        super(itemView);

        lessonTitle = itemView.findViewById(R.id.lesson_title);
        lessonDate = itemView.findViewById(R.id.lesson_date);
        lessonTime = itemView.findViewById(R.id.lesson_time);
        lessonCard = itemView.findViewById(R.id.lesson_card);

    }

    public void onBind(final Lessons lessons) {
        lessonTitle.setText(lessons.getTitle());
        lessonDate.setText(lessons.getDate());
        lessonTime.setText(lessons.getTime());

        lessonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lessonID = lessons.getLessonId();

                Intent i = new Intent(itemView.getContext(), LessonDetails.class);
                i.putExtra("lesson_id", lessonID);
                itemView.getContext().startActivity(i);

            }
        });
    }

}
