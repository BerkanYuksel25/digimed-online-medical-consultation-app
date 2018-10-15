package ses1b.group10.android_application;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Objects.requireNonNull;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFirstName, editTextFamilyName, editTextDOB, editTextWeight, editTextHeight, editTextMedCon;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private CircleImageView profileImageView;
    private String uriImage="No profile picture";
    private static final String TAG = "ProfileActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private int ACTIVITY_START_CAMERA_APP = 1;
    final static int gallery_pick =2;

    Uri selectedImageUri;

    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private PatientProfile patientProfile;
    private StorageReference patientProfileImage;

    ProgressBar progressBar;



    String FirstName, FamilyName, Weight, Height, MedCon, DOB, Gender;


    String currentUserId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            currentUserId = mAuth.getCurrentUser().getUid();
        }

        progressBar =  findViewById(R.id.progressbar);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Patients").child(currentUserId);

        patientProfileImage =FirebaseStorage.getInstance().getReference().child("PatientImage");



        initViews();
        initListeners();


    }
    private void initViews() {

        editTextFirstName = findViewById(R.id.editTextFName);
        editTextFamilyName = findViewById(R.id.editFamilyName);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextMedCon = findViewById(R.id.editMedCon);
        radioGroup = findViewById(R.id.radioGender);
        mDisplayDate = findViewById(R.id.tvDate);
        profileImageView = findViewById(R.id.profile_pic);

    }


    private void initListeners() {

        findViewById(R.id.buttonSaveDet).setOnClickListener(this);


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
                requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOption();
            }
        });


    }



    private void selectOption() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    Intent callVideoAppIntent = new Intent();
                    callVideoAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(callVideoAppIntent, ACTIVITY_START_CAMERA_APP);

                } else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, gallery_pick);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == ACTIVITY_START_CAMERA_APP) {

                Bitmap mImageUri = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                profileImageView.setImageBitmap(mImageUri);


                selectedImageUri = data.getData();

            }

            else if (requestCode == gallery_pick) {

                selectedImageUri = data.getData();
                Picasso.get().load(selectedImageUri).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.profile_pic));
            }

        }


    }

    private void UserData() {

        FirstName = editTextFirstName.getText().toString();
        FamilyName = editTextFamilyName.getText().toString();
        Weight = editTextWeight.getText().toString();
        Height = editTextHeight.getText().toString();
        MedCon = editTextMedCon.getText().toString();
        DOB = mDisplayDate.getText().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Gender = radioButton.getText().toString();


        if (FirstName.isEmpty()) {
            editTextFirstName.setError("First Name  is required.");
            editTextFirstName.requestFocus();

        }


        if (FamilyName.isEmpty()) {
            editTextFamilyName.setError("Family name is required.");
            editTextFamilyName.requestFocus();

        }
        if (Height.isEmpty()) {
            editTextHeight.setError("Height is required");
            editTextHeight.requestFocus();

        }


        if (Weight.isEmpty()) {
            editTextWeight.setError("Weight is required");
            editTextWeight.requestFocus();

        }

        if (DOB.isEmpty()) {
            editTextDOB.setError("Date of birth  is required");
            editTextDOB.requestFocus();

        }





        if (selectedImageUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference filePath = patientProfileImage.child(currentUserId + ".jpg");

            filePath.putFile(selectedImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri uri =task.getResult();
                    uriImage =uri.toString();
                    saveUserData();
                }
            });


        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            saveUserData();
        }

}



    private void saveUserData () {

        patientProfile = new PatientProfile(FirstName, FamilyName, Weight, Height, MedCon, DOB, Gender, uriImage);

        progressBar.setVisibility(View.GONE);
        databaseReference.setValue(patientProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Profile has been created", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSaveDet:
                UserData();
                break;

        }

    }
}



