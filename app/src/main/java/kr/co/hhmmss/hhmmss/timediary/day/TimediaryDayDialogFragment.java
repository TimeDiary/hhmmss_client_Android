package kr.co.hhmmss.hhmmss.timediary.day;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.co.hhmmss.hhmmss.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class TimediaryDayDialogFragment extends DialogFragment {

    private Bundle getBundle;
    private ImageButton cancelButton;
    private ImageButton saveButton;
    private TextView dateTextView;
    private EditText timeEditText;
    private EditText commentEditText;
    private MaterialRatingBar ratingRatingBar;

    @Nullable
    private Bundle mArgs;
    @Nullable
    private String mDate;
    @Nullable
    private String mTime;
    @Nullable
    private String mComment;
    @Nullable
    private Float mRating;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.frag_dialog_timediary_day, container);

        // [START get_arguments_form_recyclerview]
        mArgs = getArguments();
        if (mArgs != null) {
            mDate = mArgs.getString("date");
            mTime = mArgs.getString("time");
            mComment = mArgs.getString("comment");
            mRating = mArgs.getFloat("rating");
        }
        // [END get_arguments_form_recyclerview]


        // [START set_views]
        cancelButton = dialogView.findViewById(R.id.button_cancel);
        saveButton = dialogView.findViewById(R.id.button_save);
        TextView dayTextView = dialogView.findViewById(R.id.textview_day);
        TextView timeTextView = dialogView.findViewById(R.id.textview_time);
        TextView commentTextView = dialogView.findViewById(R.id.textview_comment);
        TextView ratingTextView = dialogView.findViewById(R.id.textview_rating);

        dateTextView = dialogView.findViewById(R.id.textview_date_content);
        timeEditText = dialogView.findViewById(R.id.edittext_time);
        commentEditText = dialogView.findViewById(R.id.edittext_comment);
        ratingRatingBar = dialogView.findViewById(R.id.ratingbar_rating);
        // [END set_views]

        // [START set_data]
        dateTextView.setText(mDate);
        timeEditText.setText(mTime);
        commentEditText.setText(mComment);
        ratingRatingBar.setRating(mRating);
        // [END set_data]

        // [START set_OnClickListener]
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: save..

            }
        });
        // [END set_OnClickListener]


        return dialogView;
    }
}
