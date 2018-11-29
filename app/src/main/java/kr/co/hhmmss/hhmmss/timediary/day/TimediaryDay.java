package kr.co.hhmmss.hhmmss.timediary.day;

import java.util.ArrayList;

class TimediaryDay {

    private String time;
    private String comment;
    private String rating;

    public TimediaryDay(String time, String comment, String rating) {
        this.time = time;
        this.comment = comment;
        this.rating = rating;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public static ArrayList<TimediaryDay> createSampleTimediaryDayList(int start, int end, int frequency) {
        ArrayList<TimediaryDay> timediaryDays = new ArrayList<>();

        for (int i = start; i <= end; i = i + frequency) {
            timediaryDays.add(new TimediaryDay(convertTimeToString(i), "TestlineTestTestTesthihihihihihihihihihihihihihihiwehfiewh" + i, String.valueOf(Math.random() + '\n')));
        }

        return timediaryDays;
    }

    public static String convertTimeToString(int time) {
        if (time < 10) {
            return "0" + Integer.valueOf(time).toString();
        } else {
            return Integer.valueOf(time).toString();
        }
    }
}
