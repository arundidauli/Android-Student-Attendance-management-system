package com.techastrum.attendance.activities.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.adapter.AdminPostAdapter;
import com.techastrum.attendance.activities.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminPanel extends AppCompatActivity {
    private static String TAG = AdminPanel.class.getSimpleName();
    private String[] arr = {"All Post", "Pending", "Approve","Pco Post", "Mera Zila", "Gallery", "Latest","Government Officers"};
    private Context context;
    private List<Post> postList;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        context=AdminPanel.this;
        setting_spinner();
        postList = new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        recyclerView = findViewById(R.id.rv_user_post);
        txt_no_data = findViewById(R.id.txt_no_data);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("post");


        findViewById(R.id.fab_add_post).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminPost.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        GetAllPost();

    }
    private void setting_spinner() {
        MaterialSpinner SortBy = findViewById(R.id.spinner_sort_by);
        SortBy.setItems(arr);
        SortBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                 if (position==0){
                    GetAllPost();
                }else if (position==1){
                    GetPendingPost();
                }else if (position==2){
                    GetApprovePost();
                }else if (position==3){
                    GetUserPost(item);
                }else if (position==4){
                    GetUserPost(item);
                }else if (position==5){
                    GetUserPost(item);
                }else if (position==6){
                    GetUserPost(item);
                }else if (position==7){
                    GetUserPost(item);
                }
            }
        });
        SortBy.getPopupWindow();
    }

    private void setRecyclerView() {
        AdminPostAdapter userPostAdapter = new AdminPostAdapter(context, postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userPostAdapter);
    }

    private void GetUserPost(String post_type) {
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
                        if (Objects.equals(ds.child("post_type").getValue(String.class), post_type)){
                            Post post = ds.getValue(Post.class);
                            postList.add(post);
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
    private void GetAllPost() {
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
                        Post post = ds.getValue(Post.class);
                        postList.add(post);
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

                            Post post = ds.getValue(Post.class);
                            postList.add(post);

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
    private void GetApprovePost() {
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

                            Post post = ds.getValue(Post.class);
                            postList.add(post);

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
