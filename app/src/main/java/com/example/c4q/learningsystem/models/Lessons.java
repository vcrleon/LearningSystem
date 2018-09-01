package com.example.c4q.learningsystem.models;

import java.util.List;

/**
 * Created by c4q on 8/31/18.
 */

public class Lessons {
    private String title;
    private String date;
    private String time;
    private List<String> lectureUrls;
    private List<String> homeworkUrls;

    public Lessons(String title, String date, String time, List<String> lectureUrls, List<String> homeworkUrls) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.lectureUrls = lectureUrls;
        this.homeworkUrls = homeworkUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getLectureUrls() {
        return lectureUrls;
    }

    public void setLectureUrls(List<String> lectureUrls) {
        this.lectureUrls = lectureUrls;
    }

    public List<String> getHomeworkUrls() {
        return homeworkUrls;
    }

    public void setHomeworkUrls(List<String> homeworkUrls) {
        this.homeworkUrls = homeworkUrls;
    }
}
