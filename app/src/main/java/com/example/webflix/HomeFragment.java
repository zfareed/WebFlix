package com.example.webflix;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class HomeFragment extends Fragment {

    View view;
    ViewFlipper flipper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        flipper = view.findViewById(R.id.flipper);

        int slideshowArray[] = {R.drawable.slider, R.drawable.webflix_logo, R.drawable.slider};

        for (int i = 0; i < slideshowArray.length; i++)
            slideShow(slideshowArray[i]);


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
}