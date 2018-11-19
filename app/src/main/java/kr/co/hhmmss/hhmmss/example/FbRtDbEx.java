package kr.co.hhmmss.hhmmss.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kr.co.hhmmss.hhmmss.R;

public class FbRtDbEx extends AppCompatActivity {

    public static final String TAG = FbRtDbEx.class.getSimpleName();
    private TextView exTextView;
    private EditText exEditText;
    private Button exButton;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");
    DatabaseReference usersRef = mRootRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbexample);

        exTextView = (TextView) findViewById(R.id.exTextView);
        exEditText = (EditText) findViewById(R.id.exEditText);
        exButton = (Button) findViewById(R.id.exButton);


    }

    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
//                String uID = mRootRef.child("users").child("RqOaCfmcdeZUz6b0dpIsyZjzuOR2").getKey();
                exTextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                exTextView.setText(text);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
            }
        });

        exButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionRef.setValue(exEditText.getText().toString());
            }
        });
    }

}
