package kr.co.hhmmss.hhmmss.timediary;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimediaryManager {

    private final String TAG = TimediaryFragment.class.getSimpleName();

    // Google Firebase
    @Nonnull
    private FirebaseUser user;
    @Nonnull
    private String uID;

    // Cloud Firestore
    private FirebaseFirestore dbRoot;
    private final CollectionReference timediaryCollectionRef;

    public TimediaryManager() {
        // [START set_Firebase]
        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = user.getUid();
        // [END set_Firebase]

        // [START set_CloudFirestore]
        dbRoot = FirebaseFirestore.getInstance();
        timediaryCollectionRef = dbRoot.collection("timediary");


        // [END set_CloudFirestore]


    }


    public void add(TimediaryDoc doc) {
        Map<String, Object> data = doc.toMap();

        add(data);
    }

    public void add(Map<String, Object> data) {
        timediaryCollectionRef
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "TimediaryDocumentSnapshot written with ID: "
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding timediary document", e);
                    }
                });
    }


    public List<TimediaryDoc> get(String date) {
        System.out.println("get TimediaryDoc...");
        List<TimediaryDoc> timediaryDocs;
        MyOnCompleteListener listener = new MyOnCompleteListener();

        timediaryCollectionRef
                .whereEqualTo("date", date)
                .orderBy("time")
                .get()
                .addOnCompleteListener(listener);
        timediaryDocs = listener.getTimediaryDocs();
        System.out.println("return TimediaryDoc...");
        return timediaryDocs;
    }

    public TimediaryDoc get(String date, String time) {
        // TODO: Edit like other overloading method.
        final TimediaryDoc[] timediary = new TimediaryDoc[1];
        timediaryCollectionRef
                .whereEqualTo("date", date)
                .whereEqualTo("time", time)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                                timediary[0] = doc.toObject(TimediaryDoc.class);
                            }
                        } else {
                            Log.d(TAG, "Error getting Timediary docs: ", task.getException());
                        }
                    }
                });
        return timediary[0];
    }

    public void addRealTimeUpdate() {
        // TODO: NOt completed....
        // [START add_LocalChangeEventListener]
        timediaryCollectionRef
                .whereEqualTo("uid", uID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<String> days = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("date") != null) {
                                days.add(doc.getString("date"));
                            }
                        }
                        Log.d(TAG, "Current dates in currentUser: " + days);
                    }
                });

        // [END add_LocalChangeEventListener]
    }

    public void delete(final String date, final String time) {

        // TODO
    }


    protected class MyOnCompleteListener implements OnCompleteListener<QuerySnapshot> {

        List<TimediaryDoc> timediaryDocs = new ArrayList<>();

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    Log.d(TAG, doc.getId() + " ==> " + doc.getData());

                    timediaryDocs.add(doc.toObject(TimediaryDoc.class));
                }
            } else {
                Log.d(TAG, "Error getting Timediary docs: ", task.getException());
            }
        }

        public List<TimediaryDoc> getTimediaryDocs() {
            System.out.println("getTimediaryDocs called.");
            return this.timediaryDocs;
        }
    }
}
