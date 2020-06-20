package com.techastrum.attendance.activities.student_attandence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.TeacherClassAdapter;
import com.techastrum.attendance.activities.model.Category;
import com.techastrum.attendance.activities.model.DailyReport;
import com.techastrum.attendance.activities.model.StudentClass;
import com.techastrum.attendance.activities.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class TeacherPanelActivity extends AppCompatActivity {
    private static String TAG = TeacherPanelActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();

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
        categoryList1.add(new StudentClass("4","Add a Report",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Daily Report",R.mipmap.abc));
        categoryList1.add(new StudentClass("4","View Other Activities",R.mipmap.abc));
    }


    private void fragment_call(Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void saveReport(String title,String des,String m_date) {
        String id = mDatabaseReference.child("daily_report").push().getKey();
        DailyReport teacher = new DailyReport();
        teacher.setId(id);
        teacher.setDate(m_date);
        teacher.setTitle(title);
        teacher.setDescription(des);
        teacher.setId(id);

        mDatabaseReference.child("daily_report").child(id).setValue(teacher);
        Toast.makeText(getApplicationContext(), "Report Add successfully", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), ViewAllTeacherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void show_dialog() {
        final Dialog dialog = new Dialog(context, R.style.WideDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setCancelable(true);
        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        assert window != null;
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // View layout = getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        // View.inflate(context, R.layout.fragment_dialog, null);
        dialog.setContentView(View.inflate(context, R.layout.fragment_add_report, null));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.PopupAnimation;

        EditText title = dialog.findViewById(R.id.txt_title);
        EditText des = dialog.findViewById(R.id.txt_des);
        EditText mdate = dialog.findViewById(R.id.txt_date);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(v -> {
            if (title.getText().toString().length()<6){
                Toasty.info(getApplicationContext(),"Enter a title", Toast.LENGTH_SHORT,true).show();
            }else if(des.getText().toString().length()<6){
                Toasty.info(getApplicationContext(),"Enter a description",Toast.LENGTH_SHORT,true).show();
            }else if(mdate.getText().toString().length()<2){
                Toasty.info(getApplicationContext(),"Enter a date",Toast.LENGTH_SHORT,true).show();
            }else {
                saveReport(title.getText().toString(),des.getText().toString(),mdate.getText().toString());
                dialog.dismiss();

            }
        });

        dialog.show();
    }

}