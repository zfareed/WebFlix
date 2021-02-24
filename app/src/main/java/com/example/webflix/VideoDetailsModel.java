package com.example.webflix;

import java.util.ArrayList;

public class VideoDetailsModel {
    String thumb_nail;
    ArrayList<String> episode_video_list;
    ArrayList<String> episode_title_list;

    public VideoDetailsModel(String thumb_nail, ArrayList<String> episode_video_list, ArrayList<String> episode_title_list) {
        this.thumb_nail = thumb_nail;
        this.episode_video_list = episode_title_list;
        this.episode_title_list = episode_title_list;
    }

    public String getThumb_nail() {
        return thumb_nail;
    }

    public void setThumb_nail(String thumb_nail) {
        this.thumb_nail = thumb_nail;
    }

    public ArrayList<String> getEpisode_video_list() {
        return episode_video_list;
    }

    public void setEpisode_video_list(ArrayList<String> episode_video_list) {
        this.episode_video_list = episode_video_list;
    }

    public ArrayList<String> getEpisode_title_list() {
        return episode_title_list;
    }

    public void setEpisode_title_list(ArrayList<String> episode_title_list) {
        this.episode_title_list = episode_title_list;
    }
}
