package com.techastrum.merajilaa.activities.activities.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techastrum.merajilaa.R;
import com.techastrum.merajilaa.activities.activities.MainActivity;
import com.techastrum.merajilaa.activities.adapter.CategoryAdapter;
import com.techastrum.merajilaa.activities.adapter.LatestAdapter;
import com.techastrum.merajilaa.activities.model.Category;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private static String TAG = HomeFragment.class.getSimpleName();
    private Context context;
    private List<Category> categoryList;
    private List<Category> latestList;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();
        setRecyclerView_Category(root);
        setRecyclerView_Latest(root);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    private void setRecyclerView_Category(View view) {
        categoryList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rv_category);
        CategoryAdapter ridesAdapter = new CategoryAdapter(context, categoryList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ridesAdapter);

        Category_data();
    }

    private void Category_data(){
        categoryList.add(new Category("","","Ambulance Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        categoryList.add(new Category("","","Bank & ATM","https://i.ibb.co/Hq8PHsJ/icons8-bank-building-100.png"));
        categoryList.add(new Category("","","Fire Bridge","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Police","https://i.ibb.co/qyZntv2/icons8-police-100.png"));
        categoryList.add(new Category("","","Advocates","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Doctors","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Politician","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","BLO","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Complaint Service","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Customer Care Service","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Courier Service","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
        categoryList.add(new Category("","","Loans","https://i.ibb.co/BcDWc03/icons8-fire-100.png"));
    }

    private void setRecyclerView_Latest(View view) {
        latestList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.rv_latest);
        LatestAdapter latestAdapter = new LatestAdapter(context, latestList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(latestAdapter);

        Latest();
    }
    private void Latest(){
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));
        latestList.add(new Category("","https://i.ibb.co/MZsG807/icons8-ambulance-100.png","Car Service","https://i.ibb.co/MZsG807/icons8-ambulance-100.png"));

    }

}
