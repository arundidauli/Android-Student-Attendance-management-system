package com.techastrum.attendance.activities.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.UserPostAdapter;
import com.techastrum.attendance.activities.model.Post;
import com.techastrum.attendance.activities.student_attandence.AddStudent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity  {
    private static String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private List<Post> postList;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data,user_email,user_name,user_contact;
    private CircleImageView user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        context = UserProfile.this;
        postList = new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        recyclerView = findViewById(R.id.rv_user_post);
        txt_no_data = findViewById(R.id.txt_no_data);
        user_image = findViewById(R.id.user_image);
        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        user_contact = findViewById(R.id.user_contact);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("post");
        findViewById(R.id.btn_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        Glide.with(context).load(Prefs.getInstance(context).GetValue(Constants.Profile_Pic))
                .placeholder(R.drawable.ic_privacy)
                .into(user_image);
        user_name.setText(Prefs.getInstance(context).GetValue(Constants.Full_Name));
        user_email.setText(Prefs.getInstance(context).GetValue(Constants.Email));
        user_contact.setText(Prefs.getInstance(context).GetValue(Constants.Phone_No));


        findViewById(R.id.fab_add_post).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddStudent.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        findViewById(R.id.txt_logout).setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }).setNegativeButton("No", null).show();




        });


        findViewById(R.id.txt_pending).setOnClickListener(v -> {
            GetPendingPost();
        });
        findViewById(R.id.txt_approve).setOnClickListener(v -> {
            GetApprovedPost();
        });
        GetAllPost();
    }
    private void setRecyclerView() {
        UserPostAdapter userPostAdapter = new UserPostAdapter(context, postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userPostAdapter);
    }

    private void GetAllPost() {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {

                            //get post by user
                            if (Objects.equals(ds.child("user_id").getValue(String.class), firebaseAuth.getUid())){
                                Post post = ds.getValue(Post.class);
                                postList.add(post);
                            }


                        }



                }
                setRecyclerView();
                if (postList.size()==0){
                    txt_no_data.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                progressDialog.dismiss();

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);


    }
    private void GetPendingPost() {
        postList.clear();
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("is_approve").getValue(Boolean.class), false)) {
                            //get post by user
                            if (Objects.equals(ds.child("user_id").getValue(String.class), firebaseAuth.getUid())){
                                Post post = ds.getValue(Post.class);
                                postList.add(post);
                            }


                        }

                    }

                }
                setRecyclerView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                progressDialog.dismiss();

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);


    }
    private void GetApprovedPost() {
        postList.clear();
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("is_approve").getValue(Boolean.class), true)) {
                            //get post by user
                            if (Objects.equals(ds.child("user_id").getValue(String.class), firebaseAuth.getUid())){
                                Post post = ds.getValue(Post.class);
                                postList.add(post);
                            }


                        }

                    }

                }
                setRecyclerView();
               


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                progressDialog.dismiss();

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);


    }



}
