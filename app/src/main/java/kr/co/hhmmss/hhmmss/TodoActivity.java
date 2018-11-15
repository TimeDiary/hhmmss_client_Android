package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodoActivity extends Fragment {
    public TodoActivity(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState){
        return inflater.inflate(R.layout.frag_todo, container, false);
    }
}
