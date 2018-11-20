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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

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
        Timediary timediary = new Timediary(date, time, rating, comment);
//        Timediary timediary = new Timediary("181120", "1900", "4", "밥먹.");

        Log.d(TAG, "date: " + date);
        Log.d(TAG, "time: " + time);
        Log.d(TAG, "rating: " + rating);
        Log.d(TAG, "comment: " + comment);

        mTimediaryRef.push().setValue(timediary);
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
    public class Timediary {

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
    }


}
