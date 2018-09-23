package com.example.c4q.learningsystem;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.c4q.learningsystem.models.Lessons;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewLessonFragment extends Fragment {
    @BindView(R.id.title_input)
    EditText lessonTitle;
    @BindView(R.id.date_input)
    EditText lessonDate;
    @BindView(R.id.time_input)
    EditText lessonTime;
    @BindView(R.id.lecture_pic_bttn)
    ImageButton lecturePicBttn;
    @BindView(R.id.hw_pic_bttn)
    ImageButton hwPicBttn;
    @BindView(R.id.create_lesson_bttn)
    Button createLessonBttn;

    @BindView(R.id.lecture_pic)
    ImageView lectureImage;
    @BindView(R.id.hw_pic)
    ImageView hwImage;

    static final int REQUEST_LECTURE_CAPTURE = 1;
    static final int REQUEST_HOMEWORK_CAPTURE = 2;

    private String notesPicPath;
    private String hwPicPath;

    private View rootview;

    private DatabaseReference currentDBUser;
    String notesPics;
    String hwPics;

    private StorageReference storageReference;

    FirebaseAuth firebaseAuth;

    String userId;

    List<Lessons> userLessons = new ArrayList<>();


    public NewLessonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_new_lesson, container, false);
        ButterKnife.bind(this, rootview);

        FirebaseUser currentUser = firebaseAuth.getInstance().getCurrentUser();
        userId = currentUser.getUid();
        Log.e("User ID: ", userId);

        currentDBUser = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("lessons");


        takeLecturePic();
        takeHomeworkPic();

        storageReference = FirebaseStorage.getInstance().getReference();


        createLessonBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLesson();
            }
        });


        return rootview;
    }

    private void createLesson() {
        String title = lessonTitle.getText().toString();
        String date = lessonDate.getText().toString();
        String time = lessonTime.getText().toString();
        String notesImage = getNotesPics();
        String hwImage = getHwPics();

        String lessonId = currentDBUser.push().getKey();
        Lessons lesson = new Lessons(title, date, time, notesImage, hwImage, lessonId);
        currentDBUser.child(lessonId).setValue(lesson);
        userLessons.add(lesson);


        Toast.makeText(getActivity(), "Lesson Created!", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();

    }

    public String getNotesPics(){
        return notesPics;
    }

    public String getHwPics(){
        return hwPics;
    }


    public void takeLecturePic() {
        lecturePicBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    dispatchTakePictureIntent(REQUEST_LECTURE_CAPTURE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void takeHomeworkPic() {
        hwPicBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dispatchTakePictureIntent(REQUEST_HOMEWORK_CAPTURE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private File createImageFile(int requestCode) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        if (requestCode == REQUEST_LECTURE_CAPTURE) {
            notesPicPath = image.getAbsolutePath();
        } else if (requestCode == REQUEST_HOMEWORK_CAPTURE) {
            hwPicPath = image.getAbsolutePath();
        }

        return image;
    }

    private void dispatchTakePictureIntent(int requestCode) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(requestCode);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.c4q.learningsystem.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);

            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_LECTURE_CAPTURE: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(notesPicPath);

                        uploadImage(Uri.fromFile(file), notesPicPath, REQUEST_LECTURE_CAPTURE);

                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            lectureImage.setImageBitmap(bitmap);
                        }
                    }
                    break;
                }

                case REQUEST_HOMEWORK_CAPTURE: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(hwPicPath);

                        uploadImage(Uri.fromFile(file), hwPicPath, REQUEST_HOMEWORK_CAPTURE);

                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            hwImage.setImageBitmap(bitmap);
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void uploadImage(Uri uri, final String picturePath, final int requestCode){
        final StorageReference riversRef = storageReference.child("images/users/" + userId + picturePath);

        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        switch (requestCode){
                            case REQUEST_LECTURE_CAPTURE: {
                                notesPics = uri.toString();
                                Log.e("Notes URI Uploaded: ", notesPics);
                                break;
                            }
                            case REQUEST_HOMEWORK_CAPTURE: {
                                hwPics = uri.toString();
                                Log.e("HW URI Uploaded: ", hwPics);
                                break;
                            }
                        }
                    }
                });
            }
        });
    }

}