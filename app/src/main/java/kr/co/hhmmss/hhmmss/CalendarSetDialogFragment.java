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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ImageButton cancelButton = getActivity().findViewById(R.id.set_calendar_schedule_cancel);
        Button saveButton = getActivity().findViewById(R.id.set_calendar_schedule_save);
        EditText titleText = getActivity().findViewById(R.id.set_calendar_title);
        EditText otherText = getActivity().findViewById(R.id.set_calendar_other);
        EditText locationText = getActivity().findViewById(R.id.set_calendar_location);
        EditText memoText = getActivity().findViewById(R.id.set_calendar_memo);

        calendarEvent = new CalendarEvent();

        final String title = titleText.getText().toString();
        final String account = null;
        final String other = otherText.getText().toString();
        final String location = locationText.getText().toString();
        final String memo = memoText.getText().toString();

        cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                calendarEvent.setTitle(title);
                calendarEvent.setAccount(account);
                calendarEvent.setOther(other);
                calendarEvent.setLocation(location);
                calendarEvent.setMemo(memo);
            }
        });

        return inflater.inflate(R.layout.frag_set_calendar, container);
    }

}
