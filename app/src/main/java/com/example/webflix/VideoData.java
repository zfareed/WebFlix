package com.example.webflix;

public class VideoData {
    String Video_title;
    String Video_image;



    public VideoData(String video_title, String video_image) {
        Video_title = video_title;
        Video_image = video_image;
    }

    public VideoData(){

    }

    public String getVideo_title() {
        return Video_title;
    }

    public String getVideo_image() {
        return Video_image;
    }


}
