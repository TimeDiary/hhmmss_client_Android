package kr.co.hhmmss.hhmmss.timediary.day;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.co.hhmmss.hhmmss.R;

public class TimediaryDayDialogFragment extends DialogFragment {

    private ImageButton cancelButton;
    private ImageButton saveButton;
    private TextView dayTextView;
    private TextView timeTextView;
    private TextView commentTextView;
    private TextView ratingTextView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.frag_dialog_timediary_day, container);

        cancelButton = dialogView.findViewById(R.id.button_cancel);
        saveButton = dialogView.findViewById(R.id.button_save);
        dayTextView = dialogView.findViewById(R.id.textview_day);
        timeTextView = dialogView.findViewById(R.id.textview_time);
        commentTextView = dialogView.findViewById(R.id.textview_comment);
        ratingTextView = dialogView.findViewById(R.id.textview_rating);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: cancel..
                // getDialog().cancel();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: save..

            }
        });


        return dialogView;
    }
}
