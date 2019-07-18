package com.example.c4q.learningsystem;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.c4q.learningsystem.ui.LessonsFragment;
import com.example.c4q.learningsystem.ui.NewLessonFragment;
import com.example.c4q.learningsystem.ui.SignIn;

public class MainActivity extends AppCompatActivity implements FragmentNavigator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn();
    }

    @Override
    public void signIn() {
        final Fragment signInFragment = SignIn.newInstance();
        inflateFragment(signInFragment);
    }

    @Override
    public void displayLessons() {
        final Fragment lessonsFragment = LessonsFragment.newInstance();
        inflateFragment(lessonsFragment);

    }

    @Override
    public void displayLessonDetails() {

    }

    @Override
    public void displayNewLessonForm() {
        final Fragment newLessonFragment = NewLessonFragment.newInstance();
        inflateFragment(newLessonFragment);
    }

    public void inflateFragment(@NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}