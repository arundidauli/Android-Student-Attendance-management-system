package com.techastrum.attendance.activities.student_attandence;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.adapter.ViewAllClassAdapter;
import com.techastrum.attendance.activities.model.Student_C;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ViewAllClasses extends AppCompatActivity {
    private static String TAG = ViewAllClasses.class.getSimpleName();
    private Context context;
    private List<Student_C> student_cList;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference,mDatabaseReference1;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_category);
        context = ViewAllClasses.this;
        progressDialog=new ProgressDialog(context);
        student_cList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference();
        mDatabaseReference1 = firebaseDatabase.getReference("class");

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            super.onBackPressed();
        });

        findViewById(R.id.fab).setOnClickListener(v -> {
            show_dialog();
        });
        GetClass();
    }
    private void setRecyclerViewCategory() {
        RecyclerView recyclerView = findViewById(R.id.rv_classes);
        ViewAllClassAdapter viewAllCategoryAdapter = new ViewAllClassAdapter(context, student_cList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAllCategoryAdapter);
    }


    private void GetClass() {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {

                            Student_C student_c = ds.getValue(Student_C.class);
                            student_cList.add(student_c);
                            Log.e(TAG,ds.child("class_name").getValue(String.class));


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
        mDatabaseReference1.addListenerForSingleValueEvent(eventListener);


    }
   /* private void Category() {
        categoryList1.add(new StudentClass("4","Ambulance Service",R.mipmap.ambulance));
        categoryList1.add(new StudentClass("5","Bank Atm",R.mipmap.bank));
        categoryList1.add(new StudentClass("6","Fire Bridge",R.mipmap.fire_brid));
        categoryList1.add(new StudentClass("7","Police",R.mipmap.police));
        categoryList1.add(new StudentClass("8","Advocates",R.mipmap.advocate));
        categoryList1.add(new StudentClass("9","Doctors",R.mipmap.doctoer));
        categoryList1.add(new StudentClass("10","Politician",R.mipmap.politician));
        categoryList1.add(new StudentClass("11","Shopping",R.mipmap.cart));
        categoryList1.add(new StudentClass("12","Complaints Service",R.mipmap.complaints));
        categoryList1.add(new StudentClass("13","Customer Care",R.mipmap.costomer));
        categoryList1.add(new StudentClass("14","Courier Service",R.mipmap.courier));
        categoryList1.add(new StudentClass("15","Job",R.mipmap.jobs));
    }*/


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
        dialog.setContentView(View.inflate(context, R.layout.fragment_add_class, null));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.PopupAnimation;

        EditText user_id = dialog.findViewById(R.id.user_id);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(v -> {
            if (user_id.getText().toString().length()<3){
                Toasty.info(getApplicationContext(),"Enter a valid name", Toast.LENGTH_SHORT,true).show();
            }else {
                AddClass(user_id.getText().toString());
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void AddClass(String new_Class) {
        String id = mDatabaseReference.child("class").push().getKey();
        Student_C student_c = new Student_C();
        student_c.setId(id);
        student_c.setClass_name(new_Class);
        mDatabaseReference.child("class").child(id).setValue(student_c);
        Toast.makeText(getApplicationContext(), "Class Add successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ViewAllClasses.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
