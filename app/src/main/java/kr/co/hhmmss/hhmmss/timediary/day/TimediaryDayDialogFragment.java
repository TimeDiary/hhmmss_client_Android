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

public class TimediaryDayDialogFragment extends DialogFragment {

    private Bundle getBundle;
    private ImageButton cancelButton;
    private ImageButton saveButton;
    private TextView dayTextView;
    private TextView timeTextView;
    private TextView commentTextView;
    private TextView ratingTextView;
    private EditText dayEditText;
    private EditText timeEditText;
    private EditText commentEditText;
    private EditText ratingEditText;

    @Nullable
    private Bundle mArgs;
    @Nullable
    private String mDate;
    @Nullable
    private String mTime;
    @Nullable
    private String mComment;
    @Nullable
    private String mRating;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.frag_dialog_timediary_day, container);

        // [START get_arguments_form_recyclerview]
        mArgs = getArguments();
        if (mArgs != null) {
            mDate = mArgs.getString("date");
            mTime = mArgs.getString("time");
            mComment = mArgs.getString("comment");
            mRating = mArgs.getString("rating");
        }
        // [END get_arguments_form_recyclerview]


        // [START set_views]
        cancelButton = dialogView.findViewById(R.id.button_cancel);
        saveButton = dialogView.findViewById(R.id.button_save);
        dayTextView = dialogView.findViewById(R.id.textview_day);
        timeTextView = dialogView.findViewById(R.id.textview_time);
        commentTextView = dialogView.findViewById(R.id.textview_comment);
        ratingTextView = dialogView.findViewById(R.id.textview_rating);

        dayEditText = dialogView.findViewById(R.id.edittext_day);
        timeEditText = dialogView.findViewById(R.id.edittext_time);
        commentEditText = dialogView.findViewById(R.id.edittext_comment);
        ratingEditText = dialogView.findViewById(R.id.edittext_rating);
        // [END set_views]

        // [START set_data]
        dayEditText.setText(mDate);
        timeEditText.setText(mTime);
        commentEditText.setText(mComment);
        ratingEditText.setText(mRating);
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
