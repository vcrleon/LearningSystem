package com.example.c4q.learningsystem.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.c4q.learningsystem.FragmentNavigator;
import com.example.c4q.learningsystem.R;
import com.example.c4q.learningsystem.lesson_recycler_view.LessonAdapter;
import com.example.c4q.learningsystem.models.Lessons;
import com.example.c4q.learningsystem.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonsFragment extends Fragment {
    private static final String TAG = "logs";
    private Context appContext;
    private FragmentNavigator fragmentNavigator;
    private View rootView;

    @BindView(R.id.new_lesson_bbtn)
    Button addLessonBttn;

    User user;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private String userId;
    private  LessonAdapter lessonAdapter;
    private RecyclerView lessonRV;
    private List<Lessons> lessonsList;

    public LessonsFragment() {
        // Required empty public constructor
    }

    public static LessonsFragment newInstance() {

        Bundle args = new Bundle();
        LessonsFragment fragment = new LessonsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentNavigator = (FragmentNavigator) context;
        appContext = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lessons, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, rootView);
        createNewLesson();

        lessonRV = rootView.findViewById(R.id.lessons_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        lessonRV.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId = user.getUid();
        databaseReference = firebaseDatabase.getReference("users").child(userId).child("lessons");

        Log.e(TAG, "user: " + user);
        Log.e(TAG, "userID : " + userId);

        lessonsList = new ArrayList<>();
        lessonAdapter = new LessonAdapter(lessonsList);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is signed in
                } else {
                    //user is signed out
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Lessons lessons = ds.getValue(Lessons.class);
                    lessonsList.add(lessons);
                }

                Collections.reverse(lessonsList);
                lessonAdapter.notifyDataSetChanged();
                lessonRV.setAdapter(lessonAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void createNewLesson() {

        addLessonBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               fragmentNavigator.displayNewLessonForm();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}
