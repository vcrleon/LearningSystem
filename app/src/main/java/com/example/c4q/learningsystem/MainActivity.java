package com.example.c4q.learningsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c4q.learningsystem.models.Lessons;
import com.example.c4q.learningsystem.models.User;
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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.login_button)
    Button loginBttn;
    @BindView(R.id.signup_button)
    Button signUpBttn;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference currentDBUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        currentDBUser = FirebaseDatabase.getInstance().getReference("users");

        signUp();
        login();
    }

    public void login() {
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
    }

    public void userLogin() {
        final String email = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Credentials can't be empty", Toast.LENGTH_SHORT).show();
        } else {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Please enter valid credentials!", Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {

                                Intent i = new Intent(MainActivity.this, LessonsActivity.class);
                                startActivity(i);

                            }

                        }
                    });
        }


    }

    public void signUp() {
        signUpBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });

    }

    public void userSignUp(){
        final String email = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final List<Lessons> lessons = new ArrayList<>();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Error in Signing Up", Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            User currentUser = new User(email, password, userId, lessons);
                            currentDBUser.child(userId).setValue(currentUser);
                            Toast.makeText(MainActivity.this, "You've signed up. Now you can log in!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
