package com.techastrum.merajilaa.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.techastrum.merajilaa.R;
import com.techastrum.merajilaa.activities.Util.Constants;
import com.techastrum.merajilaa.activities.adapter.CategoryAdapter;
import com.techastrum.merajilaa.activities.adapter.LatestAdapter;
import com.techastrum.merajilaa.activities.model.Category;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;



public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String TAG = MainActivity.class.getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private RelativeLayout progressDialog;
    private Context context;
    private List<Category> categoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = findViewById(R.id.rl_progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressDialog();
            }
        }, Constants.DELAY_TIME);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(), AddPost.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer , toolbar , R.string. navigation_drawer_open ,
                R.string. navigation_drawer_close ) ;
        drawer.addDrawerListener(toggle) ;
        toggle.syncState() ;
        NavigationView navigationView = findViewById(R.id. nav_view_first ) ;
        navigationView.setNavigationItemSelectedListener( this ) ;

        findViewById(R.id.txt_go_next).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        setRecyclerViewCategory();
        setRecyclerViewLatest();
    }

    private void setRecyclerViewCategory() {
        categoryList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_category);
        CategoryAdapter ridesAdapter = new CategoryAdapter(context, categoryList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ridesAdapter);
    }

    private void setRecyclerViewLatest() {
        categoryList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_latest);
        LatestAdapter latestAdapter = new LatestAdapter(context, categoryList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(latestAdapter);
    }

    private void setProgressDialog() {
        progressDialog.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        progressDialog.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat. START )) {
            drawer.closeDrawer(GravityCompat. START ) ;
        } else {
            super .onBackPressed() ;
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu. main_menu , menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId() ;
        if (id == R.id. action_settings ) {
            return true;
        }
        return super .onOptionsItemSelected(item) ;
    }
    @SuppressWarnings ( "StatementWithEmptyBody" )
    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId() ;
        if (id == R.id. nav_home ) {
            // Handle the camera action
           // Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
        } else if (id == R.id. nav_search ) {
            //Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), AdvanceSearch.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id. nav_login ) {
           // Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), UserLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id. nav_register ) {
          //  Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), UserLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id. nav_shop ) {
           // Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();
            Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id. nav_about_us ) {
            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();

        }
        else if (id == R.id. nav_privacy_policy ) {
            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();

        }else if (id == R.id. nav_share ) {
            Toasty.info(getApplicationContext(),"Clicked",Toasty.LENGTH_LONG,true).show();

        }
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        drawer.closeDrawer(GravityCompat. START ) ;
        return true;
    }

}
