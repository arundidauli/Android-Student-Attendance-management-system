package com.techastrum.attendance.activities.student_attandence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.TeacherClassAdapter;
import com.techastrum.attendance.activities.model.Category;
import com.techastrum.attendance.activities.model.StudentClass;

import java.util.ArrayList;
import java.util.List;

public class TeacherPanelActivity extends AppCompatActivity {
    private static String TAG = TeacherPanelActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private RelativeLayout progressDialog;
    private Context context;
    private List<StudentClass> categoryList1;
    private List<Category> categoryList;
    private List<Category> galleryList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference,mDatabaseReference_s;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherpanel);
        context = TeacherPanelActivity.this;


        setRecyclerViewCategory();

        Log.e(TAG, Prefs.getInstance(context).GetValue("student_class"));

    }

    private void setRecyclerViewCategory() {
        categoryList1 = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_category);
        TeacherClassAdapter ridesAdapter = new TeacherClassAdapter(context, categoryList1);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ridesAdapter);
        Category();
    }
    private void Category() {
        categoryList1.add(new StudentClass("4","Add Attendance",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Students",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Attendance",R.mipmap.abc));

    }

}