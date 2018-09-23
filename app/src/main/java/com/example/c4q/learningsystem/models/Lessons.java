package com.example.c4q.learningsystem.models;

import android.net.Uri;

import java.util.List;

/**
 * Created by c4q on 8/31/18.
 */

public class Lessons {
    private String title;
    private String date;
    private String time;
    private String lectureUrl;
    private String homeworkUrl;
    private String lessonId;

    public Lessons(){

    }

    public Lessons(String title, String date, String time, String lectureUrl, String homeworkUrl, String lessonId) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.lectureUrl = lectureUrl;
        this.homeworkUrl = homeworkUrl;
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public String getHomeworkUrl() {
        return homeworkUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }

    public void setHomeworkUrl(String homeworkUrl) {
        this.homeworkUrl = homeworkUrl;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
}
