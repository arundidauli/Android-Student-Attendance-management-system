package com.techastrum.attendance.activities.student_attandence;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.techastrum.attendance.activities.adapter.StudentActivitiesAdapter;
import com.techastrum.attendance.activities.adapter.StudentPositionAdapter;
import com.techastrum.attendance.activities.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentPositionActivity extends AppCompatActivity {
    private static String TAG = StudentPositionActivity.class.getSimpleName();
    private String[] arr = {"Select Position", "Position A", "Position B","Position C", "Position D"};
    private Context context;
    private List<Student> studentList;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_position);
        context= StudentPositionActivity.this;
        setting_spinner();
        studentList = new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        recyclerView = findViewById(R.id.rv_user_post);
        txt_no_data = findViewById(R.id.txt_no_data);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("Student");


        GetAllStudent();
    }

    private void setting_spinner() {
        MaterialSpinner SortBy = findViewById(R.id.spinner_sort_by);
        SortBy.setItems(arr);
        SortBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (position==0){
                    GetAllStudent();
                }else if (position==1){
                    GetStudent(item);
                }else if (position==2){
                    GetStudent(item);

                }else if (position==3){
                    GetStudent(item);
                }
            }
        });
        SortBy.getPopupWindow();
    }

    private void setRecyclerView() {
        StudentPositionAdapter userPostAdapter = new StudentPositionAdapter(context, studentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userPostAdapter);
    }

    private void GetStudent(String post_type) {
        studentList.clear();
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("student_position").getValue(String.class), post_type)){
                            Student post = ds.getValue(Student.class);
                            studentList.add(post);
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

    private void GetAllStudent() {
        studentList.clear();
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        Student post = ds.getValue(Student.class);
                        studentList.add(post);
                    }

                }
                setRecyclerView();
                if (studentList.size()==0){
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

}