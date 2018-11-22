package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Spinner tdDateSpinner;
    private TableLayout tdTableLayout;
    private List<TableRow> tdTableRows;
    private int rowCount;
    private List<String> tdDateList;
    private List<Long> tdTimeList;
    private Map<Long, Timediary> timeTDMap; // <time, Timediary>
    private Map<Long, Map<Long, Timediary>> dateTimeTDMap; // <date, timeTDMap>
    private ArrayAdapter<String> tdDateSpinnerAdapter;

    // date for one day view.(Sample)
    private Long _date;


    private final String TAG = TimediaryFragment.class.getSimpleName();


    public TimediaryFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {

        view = inflater.inflate(R.layout.frag_timediary, container, false);


        // Get current user's uid
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        // Set Cloud Firestore References
        db = FirebaseFirestore.getInstance();
        timediaryCollectionRef = db.collection("timediary");
        settingsCollectionRef = db.collection("settings");


        // Read settings
        getSettings("timediary");

        //

        // Set views
        tdDateSpinner = view.findViewById(R.id.tdDateSpinner);
        tdTableLayout = view.findViewById(R.id.tdTableLayout);
        tdTableRows = new ArrayList<>();
        tdDateList = new ArrayList<>();
        tdTimeList = new ArrayList<>();
        dateTimeTDMap = new HashMap<>();
        timeTDMap = new HashMap<>();

        // [START put_rows_into_timediary]
//        Long start = timediarySettings.getStart();
//        Log.d(TAG, "StartTime: " + start.toString());
//        Long end = timediarySettings.getEnd();
//        Log.d(TAG, "EndTime: " + end.toString());
//        Long frequency = timediarySettings.getFrequency();
//        Log.d(TAG, "Frequency: " + frequency.toString());
        /* test_data */

        Long start = Long.valueOf(9);
        Long end = Long.valueOf(18);
        Long frequency = Long.valueOf(1);
        /* test_data */

        /* Sample Table */
        initTestTable(start, end, frequency);

        /* End of Sample Table */


        // [END put_rows_into_timediary]


        // Inflate the layout for this fragment
        return view;
    }

    void initTestTable(Long start, Long end, Long frequency) {

        for (Long time = start; time <= end; time += frequency) {

            tdTimeList.add(time);

            TableRow row = new TableRow(view.getContext());

            // row's data
            TextView timeTextView = new TextView(view.getContext());
            EditText commentEditText = new EditText(view.getContext());
            EditText ratingEditText = new EditText(view.getContext());

            // [START init_timeTextView]
            timeTextView.setGravity(Gravity.CENTER);
            timeTextView.setText(time.toString());
            timeTextView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 2.2f));
            row.addView(timeTextView);
            // [END init_timeTextView]

            // [START init_commentEditText]
            commentEditText.setGravity(Gravity.CENTER);
            // TODO: Write readTimediary(Long date, Long time, timediary) { return Timediary }
//            commentEditText.setText(readComment(date, time));
            commentEditText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 8f));
            row.addView(commentEditText);
            // [END init_commentEditText]

            // [START init_ratingEditText]
            ratingEditText.setGravity(Gravity.CENTER);
            // TODO: Write readTimediary(Long date, Long time, timediary) { return Timediary }
            commentEditText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 5f));
            row.addView(ratingEditText);
            // [END init_ratingEditText]

            tdTableRows.add(row); // [Remove..?]
            tdTableLayout.addView(row, tdTableRows.size());
            Log.d(TAG, "TableRow added.");


        }
    }

    // [START push_date_into_dateSpinner]
    private void pushDateIntoSpinner(String sDate) {
        tdDateList.add(sDate);
        tdDateSpinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, tdDateList);
        tdDateSpinner.setAdapter(tdDateSpinnerAdapter);
    }
    // [END push_date_into_dateSpinner]


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
