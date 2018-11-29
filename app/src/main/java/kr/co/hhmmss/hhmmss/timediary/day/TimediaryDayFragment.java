package kr.co.hhmmss.hhmmss.timediary.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.hhmmss.hhmmss.R;

public class TimediaryDayFragment extends Fragment {

    // View
    private TimediaryDayListAdapter adapter;
    private RecyclerView timediaryDayRecyclerView;
    private Context context;
    private ArrayList<TimediaryDay> timediaryDayArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag_timediary_day, container, false);
        context = rootView.getContext();

        timediaryDayRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_day_list);

        // [START set_recyclerView]
        // Initialize timediaryDayList.
        timediaryDayArrayList = new ArrayList<>();
        /* Test Data */
        timediaryDayArrayList = TimediaryDay.createSampleTimediaryDayList(9, 18, 1);
        /* Test Data */
        // Create adapter passing in the sample timediary data
        adapter = new TimediaryDayListAdapter(timediaryDayArrayList);
        // Attach the adapter to the recyclerview to populate items
        timediaryDayRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        timediaryDayRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // [END set_recyclerView[

//        timediaryDayArrayList.add(new TimediaryDay("09", "Test0", (float) 3.5));

        // [END set_recyclerView]


        return rootView;

    }


    public void addTimediaryDayList(ArrayList<TimediaryDay> list, TimediaryDayListAdapter adapter, TimediaryDay timediaryDay) {
        list.add(timediaryDay);
        adapter.notifyDataSetChanged();

    }

    public void addTimediaryDayList(ArrayList<TimediaryDay> list, TimediaryDayListAdapter adapter, TimediaryDay timediaryDay, int position) {
        list.add(position, timediaryDay);
        adapter.notifyDataSetChanged();
    }


}
