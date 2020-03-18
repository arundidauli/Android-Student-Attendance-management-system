package com.techastrum.merajilaa.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techastrum.merajilaa.R;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class UserLogin extends AppCompatActivity {
    private static String TAG = UserLogin.class.getSimpleName();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context= UserLogin.this;
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            show_dialog();
        });
        findViewById(R.id.btn_register).setOnClickListener(v -> {
            Toasty.success(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT,true).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        findViewById(R.id.txt_term_conditions).setOnClickListener(v -> {
            Toasty.info(getApplicationContext(),"coming soon",Toast.LENGTH_SHORT,true).show();

        });
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
            if (user_id.getText().toString().length()<6){
                Toasty.error(getApplicationContext(),"Enter a valid User Id", Toast.LENGTH_SHORT,true).show();
            }else if(user_password.getText().toString().length()<6){
                Toasty.error(getApplicationContext(),"Enter a valid Password",Toast.LENGTH_SHORT,true).show();
            }else {
                dialog.dismiss();
                Toasty.success(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT,true).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}
