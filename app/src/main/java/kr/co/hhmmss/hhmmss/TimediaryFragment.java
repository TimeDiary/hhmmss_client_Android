package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimediaryFragment extends Fragment {
    public static final String TAG = TimediaryFragment.class.getSimpleName();
    private FirebaseUser user;
    private List<String> dateList;
    private View view;
    private DatabaseReference mTimediaryRef;
    private String uid;

    public TimediaryFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        dateList = new ArrayList<String>();
        mTimediaryRef = FirebaseDatabase.getInstance().getReference().child("Timediary").child(uid);

        view = inflater.inflate(R.layout.frag_timediary, container, false);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();


        /* */
        dateList.add("181118");
//        TimediaryManager tdManager = new TimediaryManager(uid);

//        tdManager.setmDateDiaryRef(dateList);


        /* */

    }

    /* ValueEvenetListener for timediary object */
    private ValueEventListener timediaryListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            Timediary timediary = dataSnapshot.getValue(Timediary.class);
            // ...
            Log.w(TAG, "loadTimediary:onDatachange : " + timediary.toMap());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };
    /* */

    // [START write_new_timediary]
    private void writeNewTimediary(String date, String time, String rating, String comment) {
        // Create new timediary at /Timediary/$uid/$timediaryid
        String key = mTimediaryRef.push().getKey();
        Timediary timediary = new Timediary(date, time, comment, rating);
        Map<String, Object> timediaryValues = timediary.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, timediaryValues);
        mTimediaryRef.updateChildren(childUpdates);
    }
    // [END write_new_timediary]


}

/* */
class Timediary {
    String date;
    String time;
    String rating;
    String comment;

    Timediary() {

    }

    Timediary(String date, String time, String comment) {
        this.date = date;
        this.time = time;
        this.rating = null;
        this.comment = comment;
    }

    Timediary(String date, String time, String comment, String rating) {
        this.time = time;
        this.rating = rating;
        this.comment = comment;
    }

    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Date", date);
        result.put("Time", time);
        result.put("Rating", rating);
        result.put("Comment", comment);

        return result;
    }

}


///* Timediary DB */
//class TimediaryManager {
//    /* TODO: Add Javadoc */
//
//    private static final String TAG = TimediaryManager.class.getSimpleName();
//    // Current User
//
//    // Timediary DB Reference
//    private DatabaseReference mTimediaryRef;
//    private List<DatabaseReference> mDateDiaryRefList;
//
//    TimediaryManager(String uid) {
//        mTimediaryRef = FirebaseDatabase.getInstance().getReference().child("Timediary").child(uid);
////        mDateDiaryRefList = new ArrayList<DatabaseReference>();
//
//    }
//
////    void setmDateDiaryRef(List<String> dateList) {
////
////        for (String date : dateList) {
////
////            mDateDiaryRefList.add(mTimediaryRef.child(date));
////        }
////
////        for (DatabaseReference mDateDiaryRef : mDateDiaryRefList) {
////
////            mDateDiaryRef.addValueEventListener(timediaryListener);
////        }
////
////
////    }
//
//
//
//}
