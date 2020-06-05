package com.techastrum.attendance.activities.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.model.Student_C;
import com.techastrum.attendance.activities.student_attandence.AddStudent;
import com.techastrum.attendance.activities.student_attandence.AddTeacherActivity;
import com.techastrum.attendance.activities.student_attandence.ViewAllClasses;

import java.util.List;

public class ViewAllClassAdapter extends RecyclerView.Adapter<ViewAllClassAdapter.MyViewHolder> {
    private static String TAG = ViewAllClassAdapter.class.getSimpleName();
    private Context context;
    private List<Student_C> categoryList;


    public ViewAllClassAdapter(Context context, List<Student_C> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_category_name.setText(categoryList.get(position).getClass_name());

        holder.rl_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Prefs.getInstance(context).GetValue(Constants.IsTeacher).equals("true")) {
                    Intent intent = new Intent(context, AddTeacherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", categoryList.get(position).getId());
                    intent.putExtra("name", categoryList.get(position).getClass_name());
                    context.startActivity(intent);
                } else if (Prefs.getInstance(context).GetValue(Constants.IsTeacher).equals("false")) {
                    Intent intent = new Intent(context, AddStudent.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", categoryList.get(position).getId());
                    intent.putExtra("name", categoryList.get(position).getClass_name());
                    context.startActivity(intent);
                }
            }
        });


        holder.rl_category.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("class").child(categoryList.get(position).getId());
                            dR.removeValue();
                            Toast.makeText(context,"Delete successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ViewAllClasses.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", null).show();

            return false;
        });



    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_category_name;
        private ImageView category_icon;
        private RelativeLayout rl_category;

        MyViewHolder(View view) {
            super(view);
            rl_category = view.findViewById(R.id.rl_category);
            category_icon = view.findViewById(R.id.category_icon);
            txt_category_name = view.findViewById(R.id.txt_category_name);
        }
    }
}
