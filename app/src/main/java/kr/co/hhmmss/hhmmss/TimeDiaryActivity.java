package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimeDiaryActivity extends Fragment {
    public TimeDiaryActivity(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState){
        return inflater.inflate(R.layout.frag_timediary, container, false);
    }
}
