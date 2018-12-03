package kr.co.hhmmss.hhmmss.timediary.day;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.co.hhmmss.hhmmss.R;
import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;

public class TimediaryDocListAdapter extends RecyclerView.Adapter<TimediaryDocListAdapter.TimediaryDocViewHolder> {
    private List<TimediaryDoc> timediaryDocs;
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


    public TimediaryDocListAdapter(List<TimediaryDoc> timediaryDocs) {
        this.timediaryDocs = timediaryDocs;

    }

    public class TimediaryDocViewHolder extends RecyclerView.ViewHolder {

        protected TextView timeView;
        protected TextView commentView;
        protected TextView ratingView;

        TimediaryDocViewHolder(View view) {
            super(view);
            System.out.println("New TimediaryDocViewHolder");

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
    public TimediaryDocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");


        timediaryDayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timediary_day, parent, false);

        return new TimediaryDocViewHolder(timediaryDayView);
    }


    @Override
    public void onBindViewHolder(@NonNull TimediaryDocViewHolder viewHolder, int position) {

        TimediaryDoc timediaryDoc = timediaryDocs.get(position);

        // [START set_views_into_list]
        // DateView
        System.out.println(timediaryDoc.getDate());
//        TextView dateView = viewHolder.dateView;
//        dateView.setText(timediaryDoc.getDate());
//        dateView.setGravity(Gravity.CENTER);
        // TimeView
        System.out.println(timediaryDoc.getTime());
        TextView timeView = viewHolder.timeView;
        timeView.setText(timediaryDoc.getTime());
        timeView.setGravity(Gravity.CENTER);

        // CommentView
        System.out.println(timediaryDoc.getComment());
        TextView commentView = viewHolder.commentView;
        commentView.setText(timediaryDoc.getComment());
        commentView.setGravity(Gravity.CENTER);

        // RatingView
        System.out.println(timediaryDoc.getRating());
        TextView ratingView = viewHolder.ratingView;
        ratingView.setText(timediaryDoc.getRating().toString());
        ratingView.setGravity(Gravity.CENTER);
        // [END set_views_into_list]


        //TODO
    }

    @Override
    public int getItemCount() {
        return timediaryDocs.size();
    }


}
