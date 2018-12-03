package kr.co.hhmmss.hhmmss.timediary.day;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kr.co.hhmmss.hhmmss.R;
import kr.co.hhmmss.hhmmss.timediary.TimediaryDoc;
import kr.co.hhmmss.hhmmss.timediary.TimediaryManager;

public class TimediaryDayFragment extends Fragment {

    private final static String TAG = "TimediaryDayFragment";

    // View
    private TimediaryDayRecyclerViewAdapter adapter;
    private RecyclerView timediaryDocRecyclerView;
    private Context context;
    //    private ArrayList<TimediaryDay> timediaryDocArrayList;
    private TimediaryDayDialogFragment timediaryDocDialogFragment;
    private Bundle args;

    // Timediary
    private TimediaryManager timediaryManager;
    private List<TimediaryDoc> timediaryDocs;
    private FirebaseFirestore firestoreDB;
    private CollectionReference timediaryCollectionRef;

    // [START onCreate]
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreDB = FirebaseFirestore.getInstance();
        timediaryManager = new TimediaryManager();
    }

    // [START onCreateView]
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frag_timediary_day, container, false);
        context = rootView.getContext();

        timediaryDocRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_day_list);
        timediaryDocRecyclerView.setHasFixedSize(true);

        timediaryCollectionRef = firestoreDB.collection("timediary");
        loadTimediaryList("181110");

//        // [START set_recyclerView2]
//        timediaryCollectionRef
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot snapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "listen:error", e);
//                            return;
//                        }
//
//                        List<TimediaryDoc> timediaryList = new ArrayList<>();
//                        for (DocumentSnapshot doc : snapshots) {
//                            TimediaryDoc timediaryDoc = doc.toObject(TimediaryDoc.class);
//                            timediaryDoc.setId(doc.getId());
//                            timediaryList.add(timediaryDoc);
//                        }
//
//
//                        // instead of simply using the entire query snapshot
//                        // see the actual changes to query results between query snapshots (added, removed, and modified)
//                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                            switch (dc.getType()) {
//                                case ADDED:
//                                    Log.d(TAG, "New timediary: " + dc.getDocument().getData());
//                                    break;
//                                case MODIFIED:
//                                    Log.d(TAG, "Modified timediary: " + dc.getDocument().getData());
//                                    break;
//                                case REMOVED:
//                                    Log.d(TAG, "Removed timediary: " + dc.getDocument().getData());
//                                    break;
//                            }
//                        }
//                    }
//                });
//
//        // [END set_recyclerView2]


//
//        // [START set_recyclerView]
//        // Initialize timediaryDocList.
////        timediaryDocArrayList = new ArrayList<>();
//        /* Test Data */
//        timediaryDocs = new ArrayList<>();
//        System.out.println("get");
//        timediaryDocs = timediaryManager.get("181110");
//        System.out.println(timediaryDocs);
////        timediaryDocArrayList = TimediaryDay.createSampleTimediaryDayList(9, 18, 1);
//        /* Test Data */
//        // Create adapter passing in the sample timediary data
////        adapter = new TimediaryDayListAdapter(timediaryDocs);
//        adapter = new TimediaryDayRecyclerViewAdapter(timediaryDocs);
//        // Set OnItemClickListener on the adapter
//        adapter.setOnItemClickListener(new TimediaryDayRecyclerViewAdapter.OnItemClickListener() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onItemClick(View itemView, int position) {
//                // [START set_arguments_from_recyclerview]
//                if (position != RecyclerView.NO_POSITION) {
//                    TimediaryDoc clickedTimediaryDay = timediaryDocs.get(position);
//                    args.putString("date", "testDate");
//                    args.putString("time", clickedTimediaryDay.getTime());
//                    args.putString("comment", clickedTimediaryDay.getComment());
//                    args.putFloat("rating", clickedTimediaryDay.getRating());
//                }
//                // [END set_arguments_from_recyclerview]
//
//                // [START open_timediaryDocDialogFragment]
//                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
//                timediaryDocDialogFragment = new TimediaryDayDialogFragment();
//                timediaryDocDialogFragment.setArguments(args);
//                timediaryDocDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Material_Light);
//                timediaryDocDialogFragment.show(fragmentManager, "TimediaryDayDialog");
//                // [END open_timediaryDocDialogFragment]
//
//            }
//        });
//        // Attach the adapter to the recyclerview to populate items
//        timediaryDocRecyclerView.setAdapter(adapter);
//        // Set layout manager to position the items
//        timediaryDocRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        // [END set_recyclerView]


        return rootView;

    }
    // [END onCreateView]


    void loadTimediaryList(String date) {
        timediaryCollectionRef
                .whereEqualTo("date", date)
                .orderBy("time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<TimediaryDoc> notesList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                TimediaryDoc timediaryDoc = doc.toObject(TimediaryDoc.class);
                                timediaryDoc.setId(doc.getId());
                                notesList.add(timediaryDoc);
                            }

                            adapter = new TimediaryDayRecyclerViewAdapter(notesList, getActivity().getSupportFragmentManager(), new Bundle(), context, firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            timediaryDocRecyclerView.setLayoutManager(mLayoutManager);
                            timediaryDocRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            timediaryDocRecyclerView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

//    void loadTimediaryList(String date) {
//        timediaryCollectionRef
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot snapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "listen:error", e);
//                            return;
//                        }
//
//                        List<TimediaryDoc> timediaryList = new ArrayList<>();
//                        for (DocumentSnapshot doc : snapshots) {
//                            TimediaryDoc timediaryDoc = doc.toObject(TimediaryDoc.class);
//                            timediaryDoc.setId(doc.getId());
//                            timediaryList.add(timediaryDoc);
//                        }
//
//
//                        // instead of simply using the entire query snapshot
//                        // see the actual changes to query results between query snapshots (added, removed, and modified)
//                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                            switch (dc.getType()) {
//                                case ADDED:
//                                    Log.d(TAG, "New timediary: " + dc.getDocument().getData());
//                                    break;
//                                case MODIFIED:
//                                    Log.d(TAG, "Modified timediary: " + dc.getDocument().getData());
//                                    break;
//                                case REMOVED:
//                                    Log.d(TAG, "Removed timediary: " + dc.getDocument().getData());
//                                    break;
//                            }
//                        }
//                    }
//                });
//    }
}
