package com.techastrum.attendance.activities.student_attandence;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.model.Teacher;

public class AddTeacherActivity extends AppCompatActivity {
    private static String TAG = AddTeacherActivity.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;
    private EditText et_category, et_name, et_teacher_id,et_teacher_pass;
    private ImageView student_image;
    private StorageReference storageRef, imageRef;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        context = AddTeacherActivity.this;
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Get reference
        storageRef = storage.getReference();

        et_category = findViewById(R.id.et_category);
        et_name = findViewById(R.id.et_teacher_name);
        et_teacher_id = findViewById(R.id.et_teacher_id);
        et_teacher_pass = findViewById(R.id.et_teacher_pass);

        if (getIntent().getStringExtra("id") != null && getIntent().getStringExtra("name") != null) {
            et_category.setText(getIntent().getStringExtra("name"));
        }
        et_category.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewAllClasses.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Prefs.getInstance(context).SetValue(Constants.IsTeacher, "true");
            startActivity(intent);
        });

        findViewById(R.id.btn_save_post).setOnClickListener(v -> {
            saveTeacher();
        });

    }

    private void saveTeacher() {
        String id = mDatabaseReference.child("teacher").push().getKey();
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setTeacher_class(et_category.getText().toString());
        teacher.setTeacher_name(et_name.getText().toString());
        teacher.setTeacher_id(et_teacher_id.getText().toString());
        teacher.setTeacher_pass(et_teacher_pass.getText().toString());

        mDatabaseReference.child("teacher").child(id).setValue(teacher);
        Toast.makeText(getApplicationContext(), "Teacher Add successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ViewAllTeacherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}