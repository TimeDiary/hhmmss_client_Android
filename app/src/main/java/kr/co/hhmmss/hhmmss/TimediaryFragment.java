package kr.co.hhmmss.hhmmss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TimediaryFragment extends Fragment {

    // Firebase variables
    private DatabaseReference mTimediaryRef; // DatabaseReference for 'Timediary'
    private FirebaseUser firebaseUser; // Firebase User who currently signed in.
    private String uid; // uid for current user.

    // view variables(for test)
    View view;
    private Spinner tdDateSpinner;
    private TableLayout tdTableLayout;

    private final String TAG = TimediaryFragment.class.getSimpleName();


    // date for one day view.(Sample)
    private String _date;


    public TimediaryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        view = inflater.inflate(R.layout.frag_timediary, container, false);

        // Get current user's uid
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        // Set reference of 'current user's timediary database'.
        mTimediaryRef = FirebaseDatabase.getInstance().getReference().child("timediary").child(uid);
        tdDateSpinner = (Spinner) view.findViewById(R.id.tdDateSpinner);
        tdTableLayout = (TableLayout) view.findViewById(R.id.tdTableLayout);


        // [START Setting_EventListener']
        tdDateSpinner.setOnItemSelectedListener(tdItemSelectedListener);
        // [END Setting_EventListener']

        mTimediaryRef.orderByChild("time").addValueEventListener(timediaryListener);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    // These will be saved to /timediary/<uid>/<key>/
    // [START write_Timediary_with_generatedKey]
    private void writeTimediary(String date, String time, String rating, String comment) {

        Timediary timediary = new Timediary(date, time, rating, comment);

        mTimediaryRef.push().setValue(timediary.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Success.
                        // TODO: Add reloading.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed....
                    }
                });

    }
    // [END write_Timediary_with_generatedKey]

    public void updateTimediary(String key, String value) {
        // TODO: write this...
    }


    /* [START] MyOnClickListener */
    Button.OnClickListener buttonOnClickListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.tdSaveButton:
//                    String date = tdDateView.getText().toString();
//                    String time = tdTimeView.getText().toString();
//                    String rating = tdRatingView.getText().toString();
//                    String comment = tdCommentView.getText().toString();
//                    writeTimediary(date, time, rating, comment);
//                    break;
                default:
                    break;
            }

        }
    };
    /* [END] MyOnClickListener */

    /* [START TdItemSelectedListener */
    Spinner.OnItemSelectedListener tdItemSelectedListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            _date = parent.getItemAtPosition(position).toString();
            Log.d(TAG, "spinner item selected: (" + position + ", " + _date + ")");

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.w(TAG, "spinner item selected: null");

        }

    };
    /* [END TdItemSelectedListener */

    /* [START Push_TimediaryTableRow(sample). */
    private void pushTableRowData(TableLayout timediaryTable, Timediary timediary) {

        EditText datas[] = new EditText[3];

        TableRow row = new TableRow(view.getContext());

        datas[0] = new EditText(view.getContext());
        datas[1] = new EditText(view.getContext());
        datas[2] = new EditText(view.getContext());

        for (EditText data : datas) {
            data.setGravity(Gravity.CENTER);
            row.addView(data);
        }


        datas[0].setText(timediary.getTime());
        datas[0].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 3f));
        datas[1].setText(timediary.getComment());
        datas[1].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 6f));
        datas[2].setText(timediary.getRating());
        datas[0].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 2f));


        timediaryTable.addView(row, 1);


    }

    /* [END Push_TimediaryTableRow(sample). */


    // [START TimediaryListener]
    ValueEventListener timediaryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                Timediary timediary = snapshot.getValue(Timediary.class);
                Log.d(TAG, "loadTimediary:onDataChange -> " + timediary.toMap().toString());

                pushTableRowData(tdTableLayout, timediary);


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

        }
    };
// [END TimediaryListener]

}

@IgnoreExtraProperties
class Timediary implements Serializable {

    public String date;
    public String time;
    public String rating;
    public String comment;


    public Timediary() {
        // Default constructor required for calls to DataSnapshot.getValue(Timediary.class)

    }

    public Timediary(String date, String time, String rating, String comment) {
        this.date = date;
        this.time = time;
        this.rating = rating;
        this.comment = comment;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
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



