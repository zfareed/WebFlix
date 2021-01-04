package com.example.webflix;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements View.OnClickListener {
    Context mcontext;
    ArrayList<VideoData> data;

    public HomeAdapter(Context context, ArrayList<VideoData> data) {
        this.mcontext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_data_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoData videodata = data.get(position);
        holder.Title.setText(videodata.video_title);
        Picasso.get().load(videodata.video_image).fit().into(holder.VideoImage);

        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(),VideoDetails.class);
               intent.putExtra("Position",position);
               v.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    @Override
    public void onClick(View v) {
       //////////////////////////////////

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView VideoImage;
        TextView Title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            VideoImage = itemView.findViewById(R.id.cardImage);
            Title = itemView.findViewById(R.id.video_title_text);


        }
    }

}
