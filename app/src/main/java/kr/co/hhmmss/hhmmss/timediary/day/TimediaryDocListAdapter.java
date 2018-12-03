package kr.co.hhmmss.hhmmss.timediary.day;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.hhmmss.hhmmss.R;
import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;
import kr.co.hhmmss.hhmmss.timediary.TimediaryFragment;

public class TimediaryDocListAdapter extends RecyclerView.Adapter<TimediaryDocListAdapter.ViewHolder> {
    private List<TimediaryDoc> timediaryDocList;
    private Context context;
    private FirebaseFirestore firestoreDB;
    private View timediaryDayView;

    private OnItemClickListener listener;


    public TimediaryDocListAdapter(List<TimediaryDoc> timediaryDocs) {
        this.timediaryDocList = timediaryDocs;

    }

    // [START ..]
    private TimediaryDocListAdapter(List<TimediaryDoc> timediaryDocList, Context context, FirebaseFirestore firestoreDB) {
        this.timediaryDocList = timediaryDocList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    // [END ..]

    // [START define_the_listener_interface]
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

    }

    public interface OnTimediaryDaySelectedListener {
        void onTimediaryDaySelected(DocumentSnapshot snapshot);
    }
    // [END define_the_listener_interface]


    // [START define_the_method_that_allows_the_parent_activity_or_fragment_to_define_the_listener]
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // [END define_the_method_that_allows_the_parent_activity_or_fragment_to_define_the_listener]


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");


        timediaryDayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timediary_day, parent, false);

        return new ViewHolder(timediaryDayView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final TimediaryDoc timediaryDoc = timediaryDocList.get(position);

        // [START set_views_into_list]
        // DateView
        System.out.println(timediaryDoc.getDate());
//        TextView dateView = ViewHolder.dateView;
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
        return timediaryDocList.size();
    }

    // [START ViewHolder]
    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.textview_recyclerview_time)
        TextView timeView;

        @BindView(R.id.textview_recyclerview_comment)
        TextView commentView;

        @BindView(R.id.textview_recyclerview_rating)
        TextView ratingView;

        ViewHolder(View view) {
            super(view);
            System.out.println("New ViewHolder");
            ButterKnife.bind(this, itemView);


        }
//
//        public void bind(final DocumentSnapshot snapshot,
//                         final OnTimediaryDaySelectedListener listener) {
//            TimediaryDoc timediaryDoc = snapshot.toObject(TimediaryDoc.class);
//            Resources resources = itemView.getResources();
//
//            timeView.setText(timediaryDoc.getTime());
//            commentView.setText(timediaryDoc.getComment());
//            ratingView.setText(timediaryDoc.getRating().toString());
//
//            // Click listener
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        listener.onTimediaryDaySelected(snapshot);
//                    }
//                }
//            });
//
//
//        }

        private void updateTimediary(TimediaryDoc timediaryDoc) {
            Intent intent = new Intent(context, TimediaryFragment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("UpdateTimediaryDate", timediaryDoc.getDate());
            intent.putExtra("UpdateTimediaryTime", timediaryDoc.getTime());
            intent.putExtra("UpdateTimediaryComment", timediaryDoc.getComment());
            intent.putExtra("UpdateTimediaryRating", timediaryDoc.getRating());
        }

    }
    // [END ViewHolder]

}
