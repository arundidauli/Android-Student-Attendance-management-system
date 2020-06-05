package com.techastrum.attendance.activities.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.model.Category;

import java.util.ArrayList;
import java.util.List;

public class BottomProductAdapter extends RecyclerView.Adapter<BottomProductAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    private static String TAG = BottomProductAdapter.class.getSimpleName();
    private Context context;
    private List<Category> categoryList;

    public BottomProductAdapter(Context context, List<Category> categoryList) {
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



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private void Set_Spinner(Spinner spinner, ArrayList<String> spinnerArray) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);


    }



    private void Spinner_Dialog(TextView textView) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("  1 Kg");
        arrayAdapter.add("  2 Kg");
        arrayAdapter.add("  3 Kg");
        arrayAdapter.add("  4 kg");
        arrayAdapter.add("  5 Kg");

        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                textView.setText(strName);
                Toast.makeText(context, strName, Toast.LENGTH_SHORT).show();
            }
        });
        builderSingle.show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quantity_text_view, qty, quantity;
        private ImageView plus, minus, product_image;
        private CardView card_select;


        MyViewHolder(View view) {
            super(view);


        }
    }

}
