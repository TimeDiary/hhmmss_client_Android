package kr.co.hhmmss.hhmmss.timediary.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import kr.co.hhmmss.hhmmss.R;
import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class TimediaryDayRecyclerViewAdapter extends RecyclerView.Adapter<TimediaryDayRecyclerViewAdapter.TimediaryDocViewHolder> {
    private FragmentManager fragmentManager;
    private TimediaryDayDialogFragment timediaryDayDialogFragment;

    private List<TimediaryDoc> timediaryList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    private View timediaryDayView;
    private Bundle args;

    private OnItemClickListener listener;

    public TimediaryDayRecyclerViewAdapter(List<TimediaryDoc> _timediaryList, FragmentManager _fragmentManager, Bundle bundle, Context context, FirebaseFirestore firestoreDB) {

        this.timediaryList = _timediaryList;
        this.fragmentManager = _fragmentManager;
        this.context = context;
        this.firestoreDB = firestoreDB;
        this.args = bundle;

        // Set OnItemClickListener on the adapter
//        this.setOnItemClickListener(new TimediaryDayRecyclerViewAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View itemView, int position) {
//                // [START set_arguments_from_recyclerview]
//                if (position != RecyclerView.NO_POSITION) {
//                    TimediaryDoc clickedTimediaryDay = timediaryList.get(position);
//                    args.putString("date", "testDate");
//                    args.putString("time", clickedTimediaryDay.getTime());
//                    args.putString("comment", clickedTimediaryDay.getComment());
//                    args.putFloat("rating", clickedTimediaryDay.getRating());
//                }
//                // [END set_arguments_from_recyclerview]
//
//                // [START open_timediaryDayDialogFragment]
//                timediaryDayDialogFragment = new TimediaryDayDialogFragment();
//                timediaryDayDialogFragment.setArguments(args);
//                timediaryDayDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light);
//                timediaryDayDialogFragment.show(fragmentManager, "TimediaryDayDialog");
//                // [END open_timediaryDayDialogFragment]
//
//            }
//        });

    }

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


    public TimediaryDayRecyclerViewAdapter(List<TimediaryDoc> timediaryDocs) {
        this.timediaryList = timediaryDocs;

    }

    public class TimediaryDocViewHolder extends RecyclerView.ViewHolder {

        protected TextView timeView;
        protected TextView commentView;
        protected MaterialRatingBar ratingView;

        TimediaryDocViewHolder(View view) {
            super(view);
            System.out.println("New TimediaryDocViewHolder");

            this.timeView = view.findViewById(R.id.textview_recyclerview_time);
            this.commentView = view.findViewById(R.id.textview_recyclerview_comment);
            this.ratingView = view.findViewById(R.id.ratingbar_recyclerview_rating);

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
        final int itemPosition = position;

        final TimediaryDoc timediaryDoc = timediaryList.get(itemPosition);

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
        MaterialRatingBar ratingView = viewHolder.ratingView;
        ratingView.setRating(timediaryDoc.getRating());
        // [END set_views_into_list]

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [START set_arguments_from_recyclerview]
                if (itemPosition != RecyclerView.NO_POSITION) {
                    TimediaryDoc clickedTimediaryDay = timediaryList.get(itemPosition);
                    args.putString("date", clickedTimediaryDay.getDate());
                    args.putString("time", clickedTimediaryDay.getTime());
                    args.putString("comment", clickedTimediaryDay.getComment());
                    args.putFloat("rating", clickedTimediaryDay.getRating());
                }
                // [END set_arguments_from_recyclerview]

                // [START open_timediaryDayDialogFragment]
                timediaryDayDialogFragment = new TimediaryDayDialogFragment();
                timediaryDayDialogFragment.setArguments(args);
                timediaryDayDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light);
                timediaryDayDialogFragment.show(fragmentManager, "TimediaryDayDialog");
                // [END open_timediaryDayDialogFragment]

            }
        });

        //TODO
    }

    @Override
    public int getItemCount() {
        return timediaryList.size();
    }


    private void updateTimediary(TimediaryDoc timediaryDoc) {

    }
}
