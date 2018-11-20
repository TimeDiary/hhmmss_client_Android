package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TimediaryFragment extends Fragment {

    // Firebase variables
    private DatabaseReference mTimediaryRef; // DatabaseReference for 'Timediary'
    private FirebaseUser firebaseUser; // Firebase User who currently signed in.
    private String uid; // uid for current user.

    // view variables(for test)
    private Button saveTdButton;
    private TextView tdDateView;
    private TextView tdTimeView;
    private TextView tdRatingView;
    private TextView tdCommentView;
    private final String TAG = TimediaryFragment.class.getSimpleName();

    public TimediaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        View view = inflater.inflate(R.layout.frag_timediary, container, false);

        // Get current user's uid
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        // Set reference of 'current user's timediary database'.
        mTimediaryRef = FirebaseDatabase.getInstance().getReference().child("Timediary").child(uid);

        // Set view variables.
        saveTdButton = (Button) view.findViewById(R.id.tdSaveButton);
        tdDateView = (EditText) view.findViewById(R.id.tdDateDBView);
        tdTimeView = (EditText) view.findViewById(R.id.tdTimeDBView);
        tdRatingView = (EditText) view.findViewById(R.id.tdRatingDBView);
        tdCommentView = (EditText) view.findViewById(R.id.tdCommentDBView);

        // [START Set_OnClickListener_to_'SaveButton']
        saveTdButton.setOnClickListener(buttonOnClickListener);
        // [END Set_OnClickListener_to_'SaveButton']


        // Inflate the layout for this fragment
        return view;
    }


    // [START write_Timediary_with_generatedKey]
    private void writeTimediary(String date, String time, String rating, String comment) {
        String key = mTimediaryRef.push().getKey();
        Log.i(TAG, "key: " + key);

        Timediary timediary = new Timediary(date, time, rating, comment);
        Map<String, Object> timediaryValues = timediary.toMap();






//        mTimediaryRef.updateChildren(childUpdates);

        mTimediaryRef.push().setValue(timediary.toMap());


    }
    // [END write_Timediary_with_generatedKey]

    /* [START] MyOnClickListener */
    Button.OnClickListener buttonOnClickListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tdSaveButton:
                    String date = tdDateView.getText().toString();
                    String time = tdTimeView.getText().toString();
                    String rating = tdRatingView.getText().toString();
                    String comment = tdCommentView.getText().toString();
                    writeTimediary(date, time, rating, comment);
                    break;
                default:
                    break;
            }

        }
    };

    /* [END] MyOnClickListener */
    @IgnoreExtraProperties
    public class Timediary implements Serializable {

        public String _date;
        public String _time;
        public String _rating;
        public String _comment;


        public Timediary() {
            // Default constructor required for calls to DataSnapshot.getValue(Timediary.class)

        }

        public Timediary(String date, String time, String rating, String comment) {
            this._date = date;
            this._time = time;
            this._rating = rating;
            this._comment = comment;
        }

        public void setDate(String date) {
            this._date = date;
        }

        public String getDate() {
            return this._date;
        }

        public void setTime(String time) {
            this._time = time;
        }

        public String getTime() {
            return this._time;
        }

        public void setComment(String comment) {
            this._comment = comment;
        }

        public String getComment() {
            return _comment;
        }

        public void setRating(String rating) {
            this._rating = rating;
        }

        public String getRating() {
            return _rating;
        }


        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("date", _date);
            result.put("time", _time);
            result.put("comment", _comment);
            result.put("rating", _rating);


            return result;
        }


    }


}
