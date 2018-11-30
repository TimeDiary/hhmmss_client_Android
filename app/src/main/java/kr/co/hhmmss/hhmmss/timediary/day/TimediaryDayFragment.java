package kr.co.hhmmss.hhmmss.timediary.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.hhmmss.hhmmss.R;

public class TimediaryDayFragment extends Fragment {

    // View
    private TimediaryDayListAdapter adapter;
    private RecyclerView timediaryDayRecyclerView;
    private Context context;
    private ArrayList<TimediaryDay> timediaryDayArrayList;
    private TimediaryDayDialogFragment timediaryDayDialogFragment;


    // [START onCreateView]
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag_timediary_day, container, false);
        context = rootView.getContext();

        timediaryDayRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_day_list);
        timediaryDayRecyclerView.setHasFixedSize(true);

        // [START set_recyclerView]
        // Initialize timediaryDayList.
        timediaryDayArrayList = new ArrayList<>();
        /* Test Data */
        timediaryDayArrayList = TimediaryDay.createSampleTimediaryDayList(9, 18, 1);
        /* Test Data */
        // Create adapter passing in the sample timediary data
        adapter = new TimediaryDayListAdapter(timediaryDayArrayList);
        // Set OnItemClickListener on the adapter
        adapter.setOnItemClickListener(new TimediaryDayListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // [START open_timediaryDayDialogFragment]
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                timediaryDayDialogFragment = new TimediaryDayDialogFragment();
                timediaryDayDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light);
                timediaryDayDialogFragment.show(fragmentManager, "TimediaryDayDialog");

                // [END open_timediaryDayDialogFragment]
                TextView timeView = (TextView) itemView.findViewById(R.id.textview_recyclerview_time);
                String time = (String) timeView.getText();
                Snackbar.make(itemView, "time: " + time + " clicked...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Attach the adapter to the recyclerview to populate items
        timediaryDayRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        timediaryDayRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // [END set_recyclerView]


        return rootView;

    }
    // [END onCreateView]




    public void addTimediaryDayList(ArrayList<TimediaryDay> list, TimediaryDayListAdapter adapter, TimediaryDay timediaryDay) {
        list.add(timediaryDay);
        adapter.notifyDataSetChanged();

    }

    public void addTimediaryDayList(ArrayList<TimediaryDay> list, TimediaryDayListAdapter adapter, TimediaryDay timediaryDay, int position) {
        list.add(position, timediaryDay);
        adapter.notifyDataSetChanged();
    }


}
