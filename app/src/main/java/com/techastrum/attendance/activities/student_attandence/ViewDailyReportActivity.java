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
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.adapter.DailyReportAdapter;
import com.techastrum.attendance.activities.adapter.StudentActivitiesAdapter;
import com.techastrum.attendance.activities.model.DailyReport;
import com.techastrum.attendance.activities.model.Student;

import java.util.ArrayList;
import java.util.List;

public class ViewDailyReportActivity extends AppCompatActivity {
    private static String TAG = ViewDailyReportActivity.class.getSimpleName();
    private String[] arr = {"Select Activity", "Games", "Sport","Quiz", "others"};
    private Context context;
    private List<DailyReport> dailyReportList;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_report);
        context= ViewDailyReportActivity.this;
        dailyReportList = new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        recyclerView = findViewById(R.id.rv_user_post);
        txt_no_data = findViewById(R.id.txt_no_data);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("daily_report");


        GetAllPost();
    }
    private void GetAllPost() {
        dailyReportList.clear();
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        DailyReport post = ds.getValue(DailyReport.class);
                        dailyReportList.add(post);
                    }

                }
                setRecyclerView();
                if (dailyReportList.size()==0){
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
    private void setRecyclerView() {
        DailyReportAdapter userPostAdapter = new DailyReportAdapter(context, dailyReportList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userPostAdapter);
    }


}