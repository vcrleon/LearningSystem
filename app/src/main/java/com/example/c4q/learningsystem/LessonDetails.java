package com.example.c4q.learningsystem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.c4q.learningsystem.models.Lessons;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @BindView(R.id.title_view)
    TextView titleView;
    @BindView(R.id.date_view)
    TextView dateView;
    @BindView(R.id.time_view)
    TextView timeView;
    @BindView(R.id.lecture_image)
    ImageButton lectureImage;
    @BindView(R.id.hw_image)
    ImageButton hwImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_details);

        ButterKnife.bind(this);

        String lessonId = getIntent().getStringExtra("lesson_id");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child("8VjZWjZIHdSwX6KBmqUVccxX16Z2").child("lessons").child(lessonId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Lessons lesson = dataSnapshot.getValue(Lessons.class);

                titleView.setText(lesson.getTitle());
                dateView.setText(lesson.getDate());
                timeView.setText(lesson.getTime());
                Picasso.get().load(lesson.getLectureUrl()).fit().into(lectureImage);
                Picasso.get().load(lesson.getHomeworkUrl()).fit().into(hwImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
