package com.techastrum.attendance.activities.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.adapter.CategoryProductAdapter;
import com.techastrum.attendance.activities.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DetailActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private List<Post> postList;
    private RecyclerView recyclerView_post;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView txt_no_data;
    private String ContactNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context=DetailActivity.this;
        progressDialog=new ProgressDialog(context);
        postList = new ArrayList<>();
        recyclerView_post = findViewById(R.id.rv_related);
        txt_no_data = findViewById(R.id.txt_no_data);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("post");
        ImageView image_ad=findViewById(R.id.image_ad);
        TextView title=findViewById(R.id.title);
        TextView txt_category_name=findViewById(R.id.txt_category_name);
        TextView txt_date=findViewById(R.id.txt_date_time);
        TextView txt_location=findViewById(R.id.txt_location);
        TextView details=findViewById(R.id.details);
        TextView send_message=findViewById(R.id.send_message);
        TextView call_now=findViewById(R.id.call_now);

        if (getIntent()!=null){
            title.setText(getIntent().getStringExtra(Constants.Post_Title));
            txt_category_name.setText(getIntent().getStringExtra(Constants.Post_category));
            txt_date.setText(getIntent().getStringExtra(Constants.Post_date));
            txt_location.setText(getIntent().getStringExtra(Constants.Post_location));
            details.setText(getIntent().getStringExtra(Constants.Post_details));
            Glide.with(context).load(getIntent().getStringExtra(Constants.Post_Image)).into(image_ad);
            ContactNumber=getIntent().getStringExtra(Constants.Post_contact);

        }
        send_message.setOnClickListener(v -> {
            show_dialog(ContactNumber,"Verified");
        });
        call_now.setOnClickListener(v -> {
            show_dialog(ContactNumber,"Verified");
        });

        GetAllRelatedPost(txt_category_name.getText().toString());

    }
    private void GetAllRelatedPost(String Category) {
        progressDialog.show();
        progressDialog.setMessage(Constants.Please_wait);
        ValueEventListener eventListener = new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.exists()) {
                        if (Objects.equals(ds.child("is_approve").getValue(Boolean.class), true)) {
                            if (Objects.equals(ds.child(Constants.Post_category).getValue(String.class), Category)) {
                                if (Objects.equals(ds.child(Constants.Post_location).getValue(String.class), Prefs.getInstance(context).GetValue(Constants.Dist))) {
                                    Post post = ds.getValue(Post.class);
                                    postList.add(post);
                                }
                            }
                            //get post by user
                           /* if (Objects.equals(ds.child("user_id").getValue(String.class), firebaseAuth.getUid())){
                                if (Objects.equals(ds.child(Constants.Post_category).getValue(String.class), Category)){

                                    if (Objects.equals(ds.child(Constants.Post_location).getValue(String.class), Prefs.getInstance(context).GetValue(Constants.Dist))){
                                        Post post = ds.getValue(Post.class);
                                        postList.add(post);
                                    }

                                }

                            }*/


                        }

                    }

                }
                CategoryProductAdapter categoryProductAdapter = new CategoryProductAdapter(context, postList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView_post.setLayoutManager(mLayoutManager);
                recyclerView_post.setItemAnimator(new DefaultItemAnimator());
                recyclerView_post.setAdapter(categoryProductAdapter);
                if (postList.size()==0){
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
    private void show_dialog(String Number,String Is_verify) {
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
        dialog.setContentView(View.inflate(context, R.layout.fragment_call, null));
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.PopupAnimation;

        TextView txt_call_now = dialog.findViewById(R.id.txt_call_now);
        TextView number = dialog.findViewById(R.id.number);
        TextView is_verify = dialog.findViewById(R.id.is_verify);
        TextView cancel = dialog.findViewById(R.id.cancel);
        number.setText(Number);
        is_verify.setText(Is_verify);

        txt_call_now.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number.getText().toString()));
            startActivity(intent);
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }
}
