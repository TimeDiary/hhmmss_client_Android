package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class CalendarSetDialogFragment extends DialogFragment {

    CalendarEvent calendarEvent;
    ImageButton cancelButton;
    Button saveButton;
    private String title;
    private String account;
    private String other;
    private String location;
    private String memo;
    private EditText titleText;
    private EditText otherText;
    private EditText locationText;
    private EditText memoText;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_set_calendar, container);
        cancelButton = view.findViewById(R.id.set_calendar_schedule_cancel);
        saveButton = view.findViewById(R.id.set_calendar_schedule_save);
        titleText = view.findViewById(R.id.set_calendar_title);
        otherText = view.findViewById(R.id.set_calendar_other);
        locationText = view.findViewById(R.id.set_calendar_location);
        memoText = view.findViewById(R.id.set_calendar_memo);

        calendarEvent = new CalendarEvent();

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //getDialog().cancel();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calendarEvent.setTitle(title);
                calendarEvent.setAccount(account = null);
                calendarEvent.setOther(other = otherText.getText().toString());
                calendarEvent.setLocation(location = locationText.getText().toString());
                calendarEvent.setMemo(memo = memoText.getText().toString());
            }
        });

        return view;
    }
}
