package kr.co.hhmmss.hhmmss;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.hhmmss.hhmmss.timediary.day.TimediaryDayFragment;
import kr.co.hhmmss.hhmmss.timediary.stat.TimediaryStatFragment;
import kr.co.hhmmss.hhmmss.timediary.week.TimediaryWeekFragment;

public class TimediaryFragment extends Fragment {
    private FirebaseUser firebaseUser; // Firebase User who currently signed in.
    private String uid; // uid for current user.

    // Cloud Firestore
    private FirebaseFirestore db;
    private CollectionReference timediaryCollectionRef;
    private CollectionReference settingsCollectionRef;

    // var For update or remove with Firestore.
    private String _field;
    private Object _value;

    // Settings
    private TimediarySettings timediarySettings;

    // view variables(for test)
    private View view;
    private View onedayView;
    private Spinner tdDateSpinner;
    private TableLayout tdTableLayout;
    private List<String> tdDateList;
    private List<Long> tdTimeList;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;


    private final String TAG = TimediaryFragment.class.getSimpleName();

    /**
     * The number of pages to show in Timediary Fragment.
     */
    private static final int NUM_PAGES = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get current user's uid
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        // Set Cloud Firestore References
        db = FirebaseFirestore.getInstance();
        timediaryCollectionRef = db.collection("timediary");
        settingsCollectionRef = db.collection("settings");


        // [END set_actionbar]


    }

    // [START onCreateView]
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanseState) {
        super.onCreateView(inflater, container, savedInstanseState);

        view = inflater.inflate(R.layout.frag_timediary_main, container, false);

        // [START ..]
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "구현 예정...", Snackbar.LENGTH_LONG)
                        .setAction("Ac2tion", null).show();
            }
        });
        // [END ..]


        // Inflate the layout for this fragment
        return view;
    }
    // [END onCreateView]


//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds itmes to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.menu_timediary, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new TimediaryDayFragment();
                case 1:
                    return new TimediaryWeekFragment();
                case 2:
                    return new TimediaryStatFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUM_PAGES;
        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "TimediaryTab " + (position + 1);
//        }
//
//        public void onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds itmes to the action bar if it is present.
//            getActivity().getMenuInflater().inflate(R.menu.menu_timediary, menu);
//        }
    }
    /* .. */


    // [START set_settings]

    /**
     * Set settings (@field, @value) into @category.
     *
     * @param category : category of settings. (e.g. "app", "timediary", ...)
     * @param field    : field for settings. (e.g. "main", "start", "frequency", ...)
     * @param value    : value for settings.
     */
    void setSettings(final String category, String field, Object value) {
        _field = field;
        _value = value;
        settingsCollectionRef
                .whereEqualTo("uid", uid)
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Set settings: " + _field + ":" + _value);
                                document.getReference().update(_field, _value);

                            }
                        } else {
                            Log.w(TAG, "Error setting settings: " + _field + ":" + _value);
                        }
                    }
                });

    }
    // [END set_settings]


    // [START get_settings]

    /**
     * Get settings from @category.
     *
     * @param category : category of settings.
     */
    void getSettings(String category) {
        settingsCollectionRef
                .whereEqualTo("uid", uid)
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Get Settings");
                                timediarySettings = document.toObject(TimediarySettings.class);
                            }
                        } else {
                            Log.w(TAG, "Error getting settings");
                        }
                    }
                });

    }
    // [END get_settings]


    // [START create_Timediary_into_Firestore]
    void createTimediary(Timediary timediary) {
        timediaryCollectionRef.add(timediary)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Timediary DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding Timediary document", e);
                    }
                });
    }
    // [END create_Timediary_into_Firestore]


    // [START read_Timediary]

    /**
     * Read @uid's all TimediaryDocuments, and do (something).
     */
    private void readTimediary() {
        getTimediaryQuery().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot timediaryDocument : task.getResult()) {
                        Log.d(TAG, timediaryDocument.getId() + " => " + timediaryDocument.getData());
                        // TODO: Write some feature.
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                }
            }
        });
    }

    /**
     * Read @uid's all TimediaryDocuments, and do (something).
     *
     * @param date
     */
    private void readTimediary(Long date) {
        getTimediaryQuery(date).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot timediaryDocument : task.getResult()) {
                        Log.d(TAG, timediaryDocument.getId() + " => " + timediaryDocument.getData());
                        // TODO: Write some feature.
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                }
            }
        });
    }

    /**
     * Read @uid's all TimediaryDocuments, and do (something).
     *
     * @param date
     * @param time
     */
    private void readTimediary(Long date, Long time) {

        getTimediaryQuery(date, time).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot timediaryDocument : task.getResult()) {
                                Log.d(TAG, timediaryDocument.getId() + " => " + timediaryDocument.getData());
                                // TODO: Write some feature.
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });
    }
    // [END read_Timediary]


    // [START update_Timediary]
    void updateTimediary(Long date, Long time, String field, Object value) {
        _field = field;
        _value = value;
        getTimediaryQuery(date, time).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot timediaryDocument : task.getResult()) {
                                Log.d(TAG, timediaryDocument.getId() + " => " + timediaryDocument.getData());
                                timediaryDocument.getReference().update(_field, _value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "TimediaryDocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents(to update): ", task.getException());

                        }
                    }
                });
    }
    // [END update_Timediary]

    // [START delete_Timediary]
    void deleteTimediary(Long date, Long time, String field, Object value) {
        _field = field;
        _value = value;
        getTimediaryQuery(date, time).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot timediaryDocument : task.getResult()) {
                                Log.d(TAG, timediaryDocument.getId() + " => " + timediaryDocument.getData());
                                timediaryDocument.getReference().update(_field, _value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "TimediaryDocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents(to update): ", task.getException());

                        }
                    }
                });
    }
    // [END delete_Timediary]

    // [START realtimeQuery_where_date&time=(...)_from_TimediaryDocuments]
    void addTimediaryListener(Long date) {
        timediaryCollectionRef
                .whereEqualTo("user", this.uid)
                .whereEqualTo("date", date)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot snapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listenTimediary:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New timediary: " + dc.getDocument().getData());
                                    break;
                                case MODIFIED:
                                    Log.d(TAG, "Modified timediary: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d(TAG, "Removed timediary: " + dc.getDocument().getData());
                                    break;
                            }

                        }
                    }
                });
    }
    // [END realtimeQuery_where_<key>_is_<value>_from_TimediaryDocuments]


    /* [START getTimediaryQuery] */

    /**
     * @return Query for "SELECT * FROM timediary WHERE uid=@uid;"
     */
    private Query getTimediaryQuery() {
        Query q = timediaryCollectionRef
                .whereEqualTo("uid", this.uid);
        return q;
    }


    /**
     * @param date: date for Timediary
     * @return Query for "SELECT * FROM timediary WHERE uid=@uid&date=@date;"
     */
    private Query getTimediaryQuery(Long date) {
        Query q = timediaryCollectionRef
                .whereEqualTo("uid", this.uid)
                .whereEqualTo("date", date);
        return q;
    }


    /**
     * @param date: date for Timediary
     * @param time: time for Timediary
     * @return Query for "SELECT * FROM timediary WHERE uid=@uid&date=@date&time=@time;
     */
    private Query getTimediaryQuery(Long date, Long time) {
        Query q = timediaryCollectionRef
                .whereEqualTo("uid", this.uid)
                .whereEqualTo("date", date)
                .whereEqualTo("time", time);
        return q;
    }

    /* [END getTimediaryQuery] */


    public static class TimediarySettings implements Serializable {

        private Long start;
        private Long frequency;
        private Long end;

        public TimediarySettings() {

        }

        public TimediarySettings(Long start, Long end, Long frequency) {
            this.start = start;
            this.end = end;
            this.frequency = frequency;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public Long getStart() {
            return start;
        }

        public Long getFrequency() {
            return frequency;
        }

        public void setFrequency(Long frequency) {
            this.frequency = frequency;
        }

        public Long getEnd() {
            return end;
        }

        public void setEnd(Long end) {
            this.end = end;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("start", start);
            result.put("end", end);
            result.put("frequency", frequency);


            return result;
        }

    }


    @IgnoreExtraProperties
    public static class Timediary implements Serializable {

        private String uid; // uid of FirebaseUser
        private Long date;
        private Long time;
        private Long rating;
        private String comment;


        public Timediary() {
            // Default constructor required for calls to DataSnapshot.getValue(Timediary.class)

        }

        public Timediary(String uid, Long date, Long time, Long rating, String comment) {
            this.uid = uid;
            this.date = date;
            this.time = time;
            this.rating = rating;
            this.comment = comment;
        }

        public void setUser(String uid) {
            this.uid = uid;
        }

        public String getUser() {
            return this.uid;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public Long getDate() {
            return this.date;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Long getTime() {
            return this.time;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment() {
            return comment;
        }

        public void setRating(Long rating) {
            this.rating = rating;
        }

        public Long getRating() {
            return rating;
        }


        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("date", date);
            result.put("time", time);
            result.put("comment", comment);
            result.put("rating", rating);


            return result;
        }
    }
}
