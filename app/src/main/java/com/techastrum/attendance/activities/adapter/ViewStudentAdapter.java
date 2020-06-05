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
import com.techastrum.attendance.activities.model.Attendance;
import com.techastrum.attendance.activities.student_attandence.ViewAllStudentActivity;

import java.util.List;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentAdapter.MyViewHolder>  {
    private Context context;
    private List<Attendance> studentList;

    public ViewStudentAdapter(Context context, List<Attendance> studentList) {
        this.context = context;
        this.studentList = studentList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_student_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(studentList.get(position).getStudent_photo())
                .placeholder(R.mipmap.sky)
                .into(holder.student_image);
        holder.txt_student_class.setText(String.format("Class: %s", studentList.get(position).getStudent_class()));
        holder.txt_student_name.setText(studentList.get(position).getStudent_name());
        holder.txt_roll_no.setText(String.format("Roll No: %s", studentList.get(position).getStudent_roll_no()));


        holder.txt_delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("student").child(studentList.get(position).getId());
                            dR.removeValue();
                            Toast.makeText(context,"Delete successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ViewAllStudentActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", null).show();


        });


    }


    @Override
    public int getItemCount() {
        return  studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_student_name, txt_student_class,txt_roll_no,txt_delete;
        private ImageView student_image,img_edit;


        MyViewHolder(View view) {
            super(view);
            txt_delete=view.findViewById(R.id.txt_delete);
            img_edit=view.findViewById(R.id.img_edit);
            student_image=view.findViewById(R.id.student_image);
            txt_student_name=view.findViewById(R.id.txt_student_name);
            txt_student_class=view.findViewById(R.id.txt_student_class);
            txt_roll_no=view.findViewById(R.id.txt_roll_no);


        }
    }

}
