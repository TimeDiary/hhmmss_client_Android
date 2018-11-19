package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimeDiaryActivity extends Fragment {
    public static final String TAG = TimeDiaryActivity.class.getSimpleName();
    private FirebaseUser _user;
    private String _uid;
    private List<String> dateList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        return inflater.inflate(R.layout.frag_timediary, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();


        /* */
        _uid = _user.getUid();
        TimediaryManager tdManager = new TimediaryManager(_uid);

        tdManager.setmDateDiaryRef(dateList);


        /* */

    }


}

/* Timediary DB */
class TimediaryManager {
    /* TODO: Add Javadoc */

    private static final String TAG = TimediaryManager.class.getSimpleName();
    // Current User

    // Timediary DB Reference
    private DatabaseReference mTimediaryRef;
    private List<DatabaseReference> mDateDiaryRefList;

    TimediaryManager(String uid) {
        mTimediaryRef = FirebaseDatabase.getInstance().getReference().child("Timediary").child(uid);

    }

    void setmDateDiaryRef(List<String> dateList) {

        for (String date : dateList) {

            mDateDiaryRefList.add(mTimediaryRef.child(date));
        }

        for (DatabaseReference mDateDiaryRef : mDateDiaryRefList) {

            mDateDiaryRef.addValueEventListener(timediaryListener);
        }


    }

    void writeTimediary(String date, String time, Timediary td) {
        // TODO: Need test.
        DatabaseReference tdRef = mTimediaryRef.child(date).child(time);
        tdRef.setValue(td);

    }

    private ValueEventListener timediaryListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            Timediary timediary = dataSnapshot.getValue(Timediary.class);
            // ...
            Log.w(TAG, "loadTimediary:onDatachange : " + dataSnapshot.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };


    /* */
    protected class Timediary {
        String rating;
        String comment;

        public Timediary() {
            this.rating = null;
            this.comment = null;

        }

        public Timediary(String comment) {
            this.rating = null;
            this.comment = comment;
        }

        public Timediary(String rating, String comment) {
            this.rating = rating;
            this.comment = comment;
        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("rating", rating);
            result.put("comment", comment);

            return result;
        }

    }
}
