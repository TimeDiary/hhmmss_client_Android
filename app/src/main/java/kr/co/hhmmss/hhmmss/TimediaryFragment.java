package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimediaryFragment extends Fragment {
    public TimediaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_timediary, container, false);
    }
}
