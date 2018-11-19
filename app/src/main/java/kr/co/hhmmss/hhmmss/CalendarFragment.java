package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CalendarFragment extends Fragment {
    private FirebaseUser _user;
    private View view;

    public CalendarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {

        _user = FirebaseAuth.getInstance().getCurrentUser();

        view = inflater.inflate(R.layout.frag_calendar, container, false);

        return view;

    }
}
