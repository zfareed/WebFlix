package com.example.webflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    ArrayList<VideoData> data;

    public HomeAdapter(Context context, ArrayList<VideoData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_data_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoData videodata = data.get(position);
        holder.Title.setText(videodata.Video_title);
        holder.VideoImage.setBackgroundResource(videodata.Video_image);
        /*holder.Title.setText("Title");*/


    }

    @Override
    public int getItemCount() {

        return data.size();
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
