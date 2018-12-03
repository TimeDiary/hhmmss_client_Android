package kr.co.hhmmss.hhmmss.timediary;

import java.util.HashMap;
import java.util.Map;

public class TimediaryDoc {

    private String uid;
    private String date;
    private String time;
    private String comment;
    private Float rating;
    private String id;

    public TimediaryDoc() {
    }

    public TimediaryDoc(String uid, String date, String time, String comment, Float rating) {
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.rating = rating;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("uid", this.uid);
        data.put("date", this.date);
        data.put("time", this.time);
        data.put("comment", this.comment);
        data.put("rating", this.rating);

        return data;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
