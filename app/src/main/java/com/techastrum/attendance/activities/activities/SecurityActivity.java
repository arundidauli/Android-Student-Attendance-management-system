package com.techastrum.attendance.activities.activities;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.student_attandence.HomeActivity;
import com.techastrum.attendance.activities.student_attandence.TeacherPanelActivity;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SecurityActivity extends AppCompatActivity {
    private static String TAG = SecurityActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference,mDatabaseReference_teacher;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        context = SecurityActivity.this;
        progressDialog=new ProgressDialog(this);

        EditText et_code = findViewById(R.id.et_security_code);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("Security");
        mDatabaseReference_teacher = firebaseDatabase.getReference("teacher");
        GetUserCode();
        findViewById(R.id.btn_save_post).setOnClickListener(v -> {
            if (Prefs.getInstance(context).GetValue(Constants.CODE) != null && et_code.getText().toString() != null) {
                if (Prefs.getInstance(context).GetValue(Constants.CODE).equals(et_code.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.info(getApplicationContext(), "Enter Valid Code", Toasty.LENGTH_LONG, true).show();
                }

            }

        });


        findViewById(R.id.txt_teacher).setOnClickListener(v -> {
            show_dialog();

        });

    }

    private void GetUserCode() {

        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e(TAG, dataSnapshot.child("code").getValue().toString());
                    Prefs.getInstance(context).SetValue(Constants.CODE, dataSnapshot.child("code").getValue().toString());


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        };
        mDatabaseReference.addListenerForSingleValueEvent(eventListener);


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
        dialog.setContentView(View.inflate(context, R.layout.fragment_login_dialog, null));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.PopupAnimation;

        EditText user_id = dialog.findViewById(R.id.user_id);
        EditText user_password = dialog.findViewById(R.id.user_password);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(v -> {
            if (user_id.getText().toString().length()<1){
                Toasty.info(getApplicationContext(),"Enter a valid User Id", Toast.LENGTH_SHORT,true).show();
            }else if(user_password.getText().toString().length()<1){
                Toasty.info(getApplicationContext(),"Enter a valid Password",Toast.LENGTH_SHORT,true).show();
            }else {
                TeacherLogin(user_id.getText().toString(),user_password.getText().toString());
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void TeacherLogin(String user_id, String pass) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("teacher_id").getValue(String.class), user_id)&& Objects.equals(ds.child("teacher_pass").getValue(String.class), pass)){

                            Log.e(TAG,ds.child("teacher_class").getValue(String.class));
                            Prefs.getInstance(context).SetValue("student_class",ds.child("teacher_class").getValue(String.class));
                            Intent intent = new Intent(getApplicationContext(), TeacherPanelActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }


                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
                progressDialog.dismiss();

            }
        };
        mDatabaseReference_teacher.addListenerForSingleValueEvent(eventListener);


    }
}
