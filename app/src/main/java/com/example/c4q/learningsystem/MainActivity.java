package com.example.c4q.learningsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c4q.learningsystem.models.Lessons;
import com.example.c4q.learningsystem.models.User;
import com.example.c4q.learningsystem.ui.LessonsFragment;
import com.example.c4q.learningsystem.ui.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    }

    public void inflateFragment(@NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}