package kr.co.hhmmss.hhmmss.timediary.day;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.hhmmss.hhmmss.R;

public class TimediaryDayListAdapter extends RecyclerView.Adapter<TimediaryDayListAdapter.TimediaryDayViewHolder> {
    private ArrayList<TimediaryDay> timediaryDays;
    private View timediaryDayView;

    private OnItemClickListener listener;

    // [START define_the_listener_interface]
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

    }
    // [END define_the_listener_interface]


    // [START define_the_method_that_allows_the_parent_activity_or_fragment_to_define_the_listener]
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // [END define_the_method_that_allows_the_parent_activity_or_fragment_to_define_the_listener]


    public TimediaryDayListAdapter(ArrayList<TimediaryDay> list) {

        this.timediaryDays = list;
    }


    public class TimediaryDayViewHolder extends RecyclerView.ViewHolder {

        protected TextView timeView;
        protected TextView commentView;
        protected TextView ratingView;

        TimediaryDayViewHolder(View view) {
            super(view);
            System.out.println("New TimediaryDayViewHolder");

            this.timeView = view.findViewById(R.id.textview_recyclerview_time);
            this.commentView = view.findViewById(R.id.textview_recyclerview_comment);
            this.ratingView = view.findViewById(R.id.textview_recyclerview_rating);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });


        }
    }


    @NonNull
    @Override
    public TimediaryDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");


        timediaryDayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timediary_day, parent, false);

        return new TimediaryDayViewHolder(timediaryDayView);
    }


    @Override
    public void onBindViewHolder(@NonNull TimediaryDayViewHolder viewHolder, int position) {

        TimediaryDay timediaryDay = timediaryDays.get(position);

        // [START set_views_into_list]
        // TimeView
        TextView timeView = viewHolder.timeView;
        timeView.setText(timediaryDay.getTime());
        timeView.setGravity(Gravity.CENTER);

        // CommentView
        TextView commentView = viewHolder.commentView;
        commentView.setText(timediaryDay.getComment());
        commentView.setGravity(Gravity.CENTER);

        // RatingView
        TextView ratingView = viewHolder.ratingView;
        ratingView.setText(timediaryDay.getRating());
        ratingView.setGravity(Gravity.CENTER);
        // [END set_views_into_list]


        //TODO
    }

    @Override
    public int getItemCount() {
        return timediaryDays.size();
    }


}
