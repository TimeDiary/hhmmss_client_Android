package kr.co.hhmmss.hhmmss.timediary.day;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kr.co.hhmmss.hhmmss.R;
import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;
import kr.co.hhmmss.hhmmss.timediary.TimediaryManager;

public class TimediaryDayFragment extends Fragment {

    // View
    private TimediaryDocListAdapter adapter;
    private RecyclerView timediaryDocRecyclerView;
    private Context context;
    //    private ArrayList<TimediaryDay> timediaryDocArrayList;
    private TimediaryDayDialogFragment timediaryDocDialogFragment;
    private Bundle args;

    // Timediary
    private TimediaryManager timediaryManager;
    private List<TimediaryDoc> timediaryDocs;

    // [START onCreate]
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timediaryManager = new TimediaryManager();
    }

    // [START onCreateView]
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag_timediary_day, container, false);
        context = rootView.getContext();
        args = new Bundle();

        timediaryDocRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_day_list);
        timediaryDocRecyclerView.setHasFixedSize(true);

        // [START set_recyclerView]
        // Initialize timediaryDocList.
//        timediaryDocArrayList = new ArrayList<>();
        /* Test Data */
        timediaryDocs = new ArrayList<>();
        System.out.println("get");
        timediaryDocs = timediaryManager.get("181110");
        System.out.println(timediaryDocs);
//        timediaryDocArrayList = TimediaryDay.createSampleTimediaryDayList(9, 18, 1);
        /* Test Data */
        // Create adapter passing in the sample timediary data
//        adapter = new TimediaryDayListAdapter(timediaryDocs);
        adapter = new TimediaryDocListAdapter(timediaryDocs);
        // Set OnItemClickListener on the adapter
        adapter.setOnItemClickListener(new TimediaryDocListAdapter.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(View itemView, int position) {
                // [START set_arguments_from_recyclerview]
                if (position != RecyclerView.NO_POSITION) {
                    TimediaryDoc clickedTimediaryDay = timediaryDocs.get(position);
                    args.putString("date", "testDate");
                    args.putString("time", clickedTimediaryDay.getTime());
                    args.putString("comment", clickedTimediaryDay.getComment());
                    args.putFloat("rating", clickedTimediaryDay.getRating());
                }
                // [END set_arguments_from_recyclerview]

                // [START open_timediaryDocDialogFragment]
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                timediaryDocDialogFragment = new TimediaryDayDialogFragment();
                timediaryDocDialogFragment.setArguments(args);
                timediaryDocDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light);
                timediaryDocDialogFragment.show(fragmentManager, "TimediaryDayDialog");
                // [END open_timediaryDocDialogFragment]

            }
        });
        // Attach the adapter to the recyclerview to populate items
        timediaryDocRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        timediaryDocRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // [END set_recyclerView]


        return rootView;

    }
    // [END onCreateView]


}
