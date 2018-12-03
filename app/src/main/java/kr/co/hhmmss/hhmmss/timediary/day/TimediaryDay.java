//package kr.co.hhmmss.hhmmss.timediary.day;
//
//import java.util.ArrayList;
//
//import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;
//
//public class TimediaryDay extends TimediaryDoc {
//
//    private String time;
//    private String comment;
//    private Float rating;
//
//    public TimediaryDay(String time, String comment, Float rating) {
//        this.time = time;
//        this.comment = comment;
//        this.rating = rating;
//    }
//
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public Float getRating() {
//        return rating;
//    }
//
//    public void setRating(Float rating) {
//        this.rating = rating;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//
//    public static ArrayList<TimediaryDay> createSampleTimediaryDayList(int start, int end, int frequency) {
//        ArrayList<TimediaryDay> timediaryDays = new ArrayList<>();
//
//        for (int i = start; i <= end; i = i + frequency) {
//            timediaryDays.add(new TimediaryDay(convertTimeToString(i), "TestlineTestTestTesthihihihihihihihihihihihihihihiwehfiewh" + i, (float) Math.random()));
//
//        }
//
//        return timediaryDays;
//    }
//
//    public static String convertTimeToString(int time) {
//        if (time < 10) {
//            return "0" + Integer.valueOf(time).toString();
//        } else {
//            return Integer.valueOf(time).toString();
//        }
//    }
//}
