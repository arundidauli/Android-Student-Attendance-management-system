package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.activities.DetailActivity;
import com.techastrum.attendance.activities.model.Post;

import java.util.List;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Post> categoryList;


    public LatestAdapter(Context context, List<Post> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latest_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.work_name.setText(categoryList.get(position).getPost_category());
        Glide.with(context).load(categoryList.get(position).getPost_image())
                .placeholder(R.mipmap.sky)
                .into(holder.image_latest);


        holder.r_item.setOnClickListener(v -> {
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


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView work_name;
        private ImageView image_latest;
        private RelativeLayout r_item;

        MyViewHolder(View view) {
            super(view);

            r_item=view.findViewById(R.id.r_item);
            image_latest=view.findViewById(R.id.image_latest);
            work_name=view.findViewById(R.id.work_name);
        }
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
