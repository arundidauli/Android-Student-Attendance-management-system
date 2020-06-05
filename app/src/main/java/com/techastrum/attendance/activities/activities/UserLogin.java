package com.techastrum.attendance.activities.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.AppController;
import com.techastrum.attendance.activities.Util.Config;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.student_attandence.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.techastrum.attendance.activities.Util.Constants.Email;
import static com.techastrum.attendance.activities.Util.Constants.Full_Name;
import static com.techastrum.attendance.activities.Util.Constants.IsLogin;
import static com.techastrum.attendance.activities.Util.Constants.Phone_No;

public class UserLogin extends AppCompatActivity {
    private static String TAG = UserLogin.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context= UserLogin.this;
        progressDialog=new ProgressDialog(this);
        EditText et_full_name=findViewById(R.id.et_name);
        EditText et_email=findViewById(R.id.et_email);
        EditText et_phone=findViewById(R.id.et_phone);
        EditText et_password=findViewById(R.id.et_password);
        findViewById(R.id.btn_register).setOnClickListener(v -> {

            if (et_full_name.getText().toString().length()<4){
                Toasty.info(getApplicationContext(),"Enter valid name",Toast.LENGTH_SHORT,true).show();
            }else if(et_email.getText().toString().length()<4){
                Toasty.info(getApplicationContext(),"Enter valid email",Toast.LENGTH_SHORT,true).show();
            }else if(et_phone.getText().toString().length()!=10){
                Toasty.info(getApplicationContext(),"Enter valid number",Toast.LENGTH_SHORT,true).show();
            }else if(et_password.getText().toString().length()<6){
                Toasty.info(getApplicationContext(),"Enter a minimum six digits password ",Toast.LENGTH_SHORT,true).show();
            }
            else {
                UserRegistration(et_full_name.getText().toString(),et_email.getText().toString(),et_phone.getText().toString(),et_password.getText().toString());

            }
        });
        findViewById(R.id.txt_term_conditions).setOnClickListener(v -> {
            Toasty.info(getApplicationContext(),"coming soon",Toast.LENGTH_SHORT,true).show();
        });
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            show_dialog();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Prefs.getInstance(context).GetValue(IsLogin)!=null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    private void UserRegistration(String f_name, String email, String phone, String password) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.Full_Name, f_name);
            jsonObject.put(Constants.Email, email);
            jsonObject.put(Constants.Phone_No, phone);
            jsonObject.put(Constants.Password, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest=new JsonObjectRequest(Request.Method.POST, Config.UserRegistration, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.i(TAG,response.toString());
                        try {
                            if (response.getBoolean("status")){
                                Log.i(TAG,response.getString("message"));

                                JSONObject object=response.getJSONObject("records");
                                Prefs.getInstance(context).SetValue(IsLogin,object.getString(Email));
                                Prefs.getInstance(context).SetValue(Full_Name,object.getString(Full_Name));
                                Prefs.getInstance(context).SetValue(Email,object.getString(Email));
                                Prefs.getInstance(context).SetValue(Phone_No,object.getString(Phone_No));
                                Log.i(TAG,object.getString("Full_Name"));
                                Log.i(TAG,object.getString("Email"));
                                Log.i(TAG,object.getString("Phone_No"));
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }else {
                                Toasty.info(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT,true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i(TAG, error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest,TAG);
    }
    private void UserLogin(String email,String password) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.Email, email);
            jsonObject.put(Constants.Password, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest stringRequest=new JsonObjectRequest(Request.Method.POST, Config.UserLogin, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.i(TAG,response.toString());
                        try {
                            if (response.getBoolean("status")){

                                Log.i(TAG,response.getString("message"));
                                Prefs.getInstance(context).SetValue("response",response.toString());
                                Prefs.getInstance(context).SetValue(IsLogin,response.getString("message"));
                                JSONArray profile=response.getJSONArray("profile");
                                JSONArray allpost=response.getJSONArray("allpost");
                                JSONArray allcategory=response.getJSONArray("allcategory");
                                JSONArray alldistrict=response.getJSONArray("alldistrict");
                                JSONArray allcomment=response.getJSONArray("allcomment");
                                JSONArray latestpost=response.getJSONArray("latestpost");
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();



                            }else {
                                Toasty.info(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT,true).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i(TAG, error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest,TAG);
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
                Toasty.info(getApplicationContext(),"Enter a valid User Id", Toast.LENGTH_SHORT,true).show();
            }else if(user_password.getText().toString().length()<6){
                Toasty.info(getApplicationContext(),"Enter a valid Password",Toast.LENGTH_SHORT,true).show();
            }else {
                UserLogin(user_id.getText().toString(),user_password.getText().toString());
                dialog.dismiss();

            }
        });

        dialog.show();
    }
}
