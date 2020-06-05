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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.activities.DetailActivity;
import com.techastrum.attendance.activities.activities.UpdatePost;
import com.techastrum.attendance.activities.activities.UserProfile;
import com.techastrum.attendance.activities.model.Post;

import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.MyViewHolder>  {
    private Context context;
    private List<Post> categoryList;

    public UserPostAdapter(Context context, List<Post> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_post_row_item, parent, false);

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


    }


    @Override
    public int getItemCount() {
        return  categoryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_category_name, txt_title,txt_location,txt_call,txt_delete;
        private ImageView post_image,img_edit;


        MyViewHolder(View view) {
            super(view);
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
