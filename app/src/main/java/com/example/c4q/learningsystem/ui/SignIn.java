package com.example.c4q.learningsystem.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c4q.learningsystem.FragmentNavigator;
import com.example.c4q.learningsystem.LessonsActivity;
import com.example.c4q.learningsystem.MainActivity;
import com.example.c4q.learningsystem.R;
import com.example.c4q.learningsystem.models.Lessons;
import com.example.c4q.learningsystem.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends Fragment {

    @BindView(R.id.username)
    EditText usernameInput;
    @BindView(R.id.password)
    EditText passwordInput;
    @BindView(R.id.login_button)
    Button loginBttn;
    @BindView(R.id.signup_button)
    Button signUpBttn;

    private FragmentNavigator fragmentNavigator;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference currentDBUser;
    private View rootView;
    private Context appContext;


    public SignIn() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        return new SignIn();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        appContext = context;
        fragmentNavigator = (FragmentNavigator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_sign_in, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, rootView);

        firebaseAuth = FirebaseAuth.getInstance();
        currentDBUser = FirebaseDatabase.getInstance().getReference("users");

        signUp();
        login();

    }

    private void login() {
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
            
        });
    }

    private void userLogin() {
        final String email = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(appContext, "Credentials can't be empty", Toast.LENGTH_SHORT).show();
        } else {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(appContext, "Please enter valid credentials!", Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {

                                fragmentNavigator.displayLessons();

                            }

                        }
                    });
        }
    }

    private void signUp() {
        signUpBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });
    }

    private void userSignUp() {
        final String email = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final List<Lessons> lessons = new ArrayList<>();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(appContext, "Error in Signing Up", Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            User currentUser = new User(email, password, userId, lessons);
                            currentDBUser.child(userId).setValue(currentUser);
                            Toast.makeText(appContext, "You've signed up. Now you can log in!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
