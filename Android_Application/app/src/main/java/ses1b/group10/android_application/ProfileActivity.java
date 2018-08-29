package ses1b.group10.android_application;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFirstName, editTextFamilyName, editTextDOB, editTextWeight, editTextHeight, editTextMedCon;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private static final String TAG = "ProfileActivity";
    //private Button save;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private PatientProfile patientProfile;
    String FirstName, FamilyName, Weight, Height, MedCon, DOB, Gender;
    private FirebaseUser firebaseUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextFirstName = findViewById(R.id.editTextFName);
        editTextFamilyName = findViewById(R.id.editFamilyName);
       // editTextDOB = findViewById(R.id.editTextDOB);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextMedCon = findViewById(R.id.editMedCon);
        radioGroup = findViewById(R.id.radioGender);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        findViewById(R.id.buttonSaveDet).setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSaveDet:
                saveUserData();
                break;



        }

    }


    private void saveUserData() {

        FirstName = editTextFirstName.getText().toString();
        FamilyName = editTextFamilyName.getText().toString();
        Weight = editTextWeight.getText().toString();
        Height = editTextHeight.getText().toString();
        MedCon = editTextMedCon.getText().toString();
        DOB = mDisplayDate.getText().toString();

        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Gender = radioButton.getText().toString();

        final String user = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Patients").child(user);

        PatientProfile patientProfile = new PatientProfile(FirstName, FamilyName, Weight, Height, MedCon, DOB, Gender);

        databaseReference.setValue(patientProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Profile has been created", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ProfileActivity.this, DataPacket.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(ProfileActivity.this, "Profile has been created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}



