package com.techastrum.attendance.activities.student_attandence;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.adapter.TeacherAdapter;
import com.techastrum.attendance.activities.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class ViewAllTeacherActivity extends AppCompatActivity {
    private static String TAG = ViewAllTeacherActivity.class.getSimpleName();
    private Context context;
    private List<Teacher> teacherList;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_teacher);
        context = ViewAllTeacherActivity.this;
        teacherList = new ArrayList<>();
        progressDialog=new ProgressDialog(context);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("teacher");
        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddTeacherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });


        findViewById(R.id.btn_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        GetTeacher();
    }
    private void setRecyclerViewCategory() {
        RecyclerView recyclerView = findViewById(R.id.rv_teacher);
        TeacherAdapter teacherAdapter = new TeacherAdapter(context, teacherList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(teacherAdapter);
    }


    private void GetTeacher() {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {

                        Teacher teacher = ds.getValue(Teacher.class);
                        teacherList.add(teacher);

                    }
                }
                setRecyclerViewCategory();

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