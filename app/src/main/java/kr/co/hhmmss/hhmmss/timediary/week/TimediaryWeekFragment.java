package kr.co.hhmmss.hhmmss.timediary.week;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.hhmmss.hhmmss.R;

public class TimediaryWeekFragment extends Fragment {

    public static final String ARG_OBJECT = "week";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag_timediary_week, container, false);
        Bundle args = getArguments();
//        ((TextView) rootView.findViewById(R.id.oneday_text))
//                .setText(Integer.toString(args.getInt(ARG_OBJECT)));

        return rootView;

    }


}
