package com.techastrum.attendance.activities.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Helper;
import com.techastrum.attendance.activities.student_attandence.StudentActivities;
import com.techastrum.attendance.activities.activities.DetailActivity;
import com.techastrum.attendance.activities.activities.UpdatePost;
import com.techastrum.attendance.activities.activities.UserProfile;
import com.techastrum.attendance.activities.model.Post;

import java.util.List;

public class AdminPostAdapter extends RecyclerView.Adapter<AdminPostAdapter.MyViewHolder>  {
    private Context context;
    private List<Post> categoryList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth;

    public AdminPostAdapter(Context context, List<Post> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_post_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getPost_image())
                .placeholder(R.mipmap.sky)
                .into(holder.post_image);
        holder.txt_category_name.setText(categoryList.get(position).getPost_category());
        holder.txt_title.setText(categoryList.get(position).getPost_title());
        holder.txt_location.setText(categoryList.get(position).getPost_district());
        holder.txt_call.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.Post_Image,categoryList.get(position).getPost_image());
            intent.putExtra(Constants.Post_contact,categoryList.get(position).getContact());
            intent.putExtra(Constants.Post_category,categoryList.get(position).getPost_category());
            intent.putExtra(Constants.Post_date,categoryList.get(position).getPost_date());
            intent.putExtra(Constants.Post_location,categoryList.get(position).getPost_district());
            intent.putExtra(Constants.Post_details,categoryList.get(position).getPost_detail());
            intent.putExtra(Constants.Post_Title,categoryList.get(position).getPost_title());
            intent.putExtra(Constants.Post_view,categoryList.get(position).getViews());
            context.startActivity(intent);
        });
        holder.img_edit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdatePost.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.Post_Id,categoryList.get(position).getId());
            intent.putExtra(Constants.Post_Image,categoryList.get(position).getPost_image());
            intent.putExtra(Constants.Post_contact,categoryList.get(position).getContact());
            intent.putExtra(Constants.Post_category,categoryList.get(position).getPost_category());
            intent.putExtra(Constants.Post_location,categoryList.get(position).getPost_district());
            intent.putExtra(Constants.Post_details,categoryList.get(position).getPost_detail());
            intent.putExtra(Constants.Post_Title,categoryList.get(position).getPost_title());
            context.startActivity(intent);

        });

        holder.txt_delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("post").child(categoryList.get(position).getId());
                            dR.removeValue();
                            Toast.makeText(context,"Delete successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, UserProfile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", null).show();


        });


        if (categoryList.get(position).isIs_approve()){
            holder.txt_status.setText("Approved");
            holder.txt_status.setBackground(ContextCompat.getDrawable(context,R.drawable.approve_border));
            holder.txt_status.setTextColor(ContextCompat.getColor(context,R.color.green));

            holder.txt_status.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to do?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UpdatePost(position,false);
                            }
                        }).setNegativeButton("No", null).show();


            });
        }else {
            holder.txt_status.setText("Pending");
            holder.txt_status.setBackground(ContextCompat.getDrawable(context,R.drawable.orange_border));
            holder.txt_status.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to do?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UpdatePost(position,true);
                            }
                        }).setNegativeButton("No", null).show();


            });
        }



    }

    private void UpdatePost(int position,boolean is_true) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("post");
        Post user_post = new Post();
        user_post.setId(firebaseAuth.getUid());
        user_post.setUser_id(firebaseAuth.getUid());
        user_post.setPost_image(categoryList.get(position).getPost_image());
        try {
            user_post.setPost_id(categoryList.get(position).getPost_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_post.setPost_title(categoryList.get(position).getPost_title());
        user_post.setPost_detail(categoryList.get(position).getPost_detail());
        user_post.setPost_date(Helper.get_date());
        user_post.setPost_url("Ambulance Service");
        user_post.setVideo_url("Ambulance Service");
        user_post.setPost_district(categoryList.get(position).getPost_district());
        user_post.setPost_type(categoryList.get(position).getPost_type());
        user_post.setPost_category(categoryList.get(position).getPost_category());
        user_post.setContact(categoryList.get(position).getContact());
        user_post.setViews("10");
        user_post.setIs_approve(is_true);
        mDatabaseReference.child(categoryList.get(position).getId()).setValue(user_post);
        Toast.makeText(context, "Post Updated", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, StudentActivities.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return  categoryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_category_name, txt_title,txt_location,txt_call,txt_delete,txt_status;
        private ImageView post_image,img_edit;


        MyViewHolder(View view) {
            super(view);
            txt_status=view.findViewById(R.id.txt_status);
            txt_delete=view.findViewById(R.id.txt_delete);
            img_edit=view.findViewById(R.id.img_edit);
            post_image=view.findViewById(R.id.post_image);
            txt_category_name=view.findViewById(R.id.txt_category_name);
            txt_title=view.findViewById(R.id.txt_title);
            txt_location=view.findViewById(R.id.txt_location);
            txt_call=view.findViewById(R.id.txt_call);
            post_image=view.findViewById(R.id.post_image);


        }
    }

}
