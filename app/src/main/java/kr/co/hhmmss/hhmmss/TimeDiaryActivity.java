package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TimeDiaryActivity extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        return inflater.inflate(R.layout.frag_timediary, container, false);
    }


    /* Timediary DB */
    protected class TimediaryManager {
        /* TODO: Add Javadoc */

        // Current User
        private FirebaseUser _user;

        // Timediary DB Reference
        private DatabaseReference timediaryDB;

        // Rating for that date and time
        private String _rating;

        // Comment for that date and time
        private String _comment;


        protected TimediaryManager(String uid) {
            _user = FirebaseAuth.getInstance().getCurrentUser();
            timediaryDB = FirebaseDatabase.getInstance().getReference("Timediary").child(uid);
        }

        protected void setRating(String rating) {
            _rating = rating;
        }

        protected void setComment(String comment) {
            _comment = comment;
        }

        private void save(String date, String time) {
            // TODO: Need test.
            DatabaseReference tdRef = timediaryDB.child(date).child(time);
            tdRef.child("Rating").setValue(_rating.getClass());
            tdRef.child("Comment").setValue(_comment.getClass());

        }

        // TODO: CRUD...

    }
}
