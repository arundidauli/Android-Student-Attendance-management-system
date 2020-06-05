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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Teacher;
import com.techastrum.attendance.activities.student_attandence.ViewAllTeacherActivity;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder>  {
    private Context context;
    private List<Teacher> teacherList;

    public TeacherAdapter(Context context, List<Teacher> teacherList    ) {
        this.context = context;
        this.teacherList = teacherList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_teacher_name.setText(teacherList.get(position).getTeacher_name());
        holder.txt_teacher_class.setText(String.format("Assign Class: %s", teacherList.get(position).getTeacher_class()));
        holder.txt_teacher_subject.setText(String.format("User Id %s, Password %s", teacherList.get(position).getTeacher_id(), teacherList.get(position).getTeacher_pass()));

        holder.img_edit.setOnClickListener(v -> {
          /*  Intent intent = new Intent(context, UpdatePost.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);*/

        });

        holder.txt_delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("teacher").child(teacherList.get(position).getId());
                            dR.removeValue();
                            Toast.makeText(context,"Delete successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ViewAllTeacherActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    }).setNegativeButton("No", null).show();


        });


    }


    @Override
    public int getItemCount() {
        return  teacherList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  txt_teacher_name, txt_teacher_subject,txt_teacher_class,txt_delete;
        private ImageView img_edit;


        MyViewHolder(View view) {
            super(view);
            txt_delete=view.findViewById(R.id.txt_delete);
            img_edit=view.findViewById(R.id.img_edit);
            txt_teacher_name=view.findViewById(R.id.txt_teacher_name);
            txt_teacher_subject=view.findViewById(R.id.txt_teacher_subject);
            txt_teacher_class=view.findViewById(R.id.txt_teacher_class);



        }
    }

}
