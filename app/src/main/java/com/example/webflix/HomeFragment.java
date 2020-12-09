package com.example.webflix;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    View view;
    ViewFlipper flipper;
    ArrayList<VideoData> videoData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        flipper = view.findViewById(R.id.flipper);

        int slideshowArray[] = {R.drawable.slider, R.drawable.slider, R.drawable.slider};

        for (int i = 0; i < slideshowArray.length; i++)
            slideShow(slideshowArray[i]);

        VideoData();

        RecyclerView recyclerView = view.findViewById(R.id.rclrview);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(),videoData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);


        return view;
    }

    public void slideShow(int img) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        flipper.setInAnimation(getContext(), android.R.anim.slide_out_right);


    }

    public void VideoData(){
        videoData = new ArrayList<VideoData>();
        videoData.add(new VideoData("Title 1",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 2",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 3",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 4",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 5",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 6",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 7",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 8",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 9",R.drawable.web_series_image));
        videoData.add(new VideoData("Title 10",R.drawable.web_series_image));

    }

}