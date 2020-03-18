package com.techastrum.merajilaa.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.techastrum.merajilaa.R;
import com.techastrum.merajilaa.activities.activities.DetailActivity;
import com.techastrum.merajilaa.activities.activities.MainActivity;
import com.techastrum.merajilaa.activities.model.Category;
import com.techastrum.merajilaa.activities.model.Post;

import java.util.List;

public class Postadapter extends RecyclerView.Adapter<Postadapter.MyViewHolder> {
    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Post> postList;


    public Postadapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       /* holder.work_name.setText(categoryList.get(position).getCategory_name());
        Glide.with(context).load(categoryList.get(position).getUrl())
                .placeholder(R.mipmap.sky)
                .into(holder.image_latest);*/
       holder.rl_post.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, DetailActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               context.startActivity(intent);
           }
       });


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView work_name;
        private ImageView image_latest;
        private RelativeLayout rl_post;

        MyViewHolder(View view) {
            super(view);

            rl_post=view.findViewById(R.id.rl_post);
            image_latest=view.findViewById(R.id.image_latest);
            work_name=view.findViewById(R.id.work_name);
        }
    }
    @Override
    public int getItemCount() {
        return 20;
    }
}
