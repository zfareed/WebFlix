package com.example.webflix;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    View view;
    Context context;
    ImageSlider imageSlider;
    ArrayList<VideoData> videoData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseRef1;
    DatabaseReference databaseRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer = findViewById(R.id.drawer_layout) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });
        //navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recyclerView = findViewById(R.id.rclrview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);



        /////////////////////////// content_main code ///////////////////////////////////
        imageSlider = findViewById(R.id.image_slider);
        final ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();

         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseRef2 = firebaseDatabase.getReference("Slider");
         databaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot ds: snapshot.getChildren())
                 {

                     imageList.add(new SlideModel(ds.child("url").getValue().toString(),ds.child("title").getValue().toString(),ScaleTypes.FIT));
                 }

                 imageSlider.setImageList(imageList);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        videoData = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////
        databaseRef1 = firebaseDatabase.getReference("Data");

        databaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    VideoData data = ds.getValue(VideoData.class);
                    videoData.add(data);
                }

                HomeAdapter homeAdapter = new HomeAdapter(context,videoData);
                recyclerView.setAdapter(homeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        ////////////////////////////////////////////////////////////////////////////////
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent myintent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(myintent);


                break;

            case R.id.nav_contact:
                Intent nintent = new Intent(MainActivity.this, ContactUs.class);
                startActivity(nintent);
                break;

            case R.id.nav_share:
                String mytext = "Please download Webflix App, https://play.google.com/store/apps/details?id=com.example.webflix";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(intent.EXTRA_TEXT, mytext);
                startActivity(intent);
                break;

            case R.id.nav_rate_app:
                break;

            case R.id.nav_privacy:
                String URL = "https://myprivacypolicy1122.blogspot.com/2021/02/webflix.html";
                Intent mintent = new Intent( Intent.ACTION_VIEW, Uri.parse(URL));
                Intent chooser = Intent.createChooser(mintent,"Choose from Appication");

                if (mintent.resolveActivity(getPackageManager())!=null){
                    startActivity(chooser);
                }
                break;

        }
    }


    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}