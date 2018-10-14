package ses1b.group10.android_application.Doctor_End;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ses1b.group10.android_application.EditProfileActivity;
import ses1b.group10.android_application.PatientProfile;
import ses1b.group10.android_application.ProfileActivity;
import ses1b.group10.android_application.R;

public class EditDocProfileActivity extends AppCompatActivity {
    private TextView textFirstName,textFamilyName,textOccupation,textWeight,textHeight;
    private Button editProfile;
    private CircleImageView profileImage;
    private PatientProfile patientProfile;

    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;

    private String currentDocId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc_profile);


        mAuth= FirebaseAuth.getInstance();

        currentDocId= mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(currentDocId);

        textFirstName =findViewById(R.id.docfirstName);
        textFamilyName=findViewById(R.id.doclastName);
        textHeight =findViewById(R.id.docheight);
        textWeight=findViewById(R.id.docweight);
        textOccupation=findViewById(R.id.dococcupation);
        profileImage =findViewById(R.id.docprofileimage);
        editProfile=findViewById(R.id.doceditProfile);


        setpatientInfo();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDocProfileActivity.this,DoctorProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setpatientInfo() {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String image = dataSnapshot.child("docImage").getValue().toString();
                    String firstName= dataSnapshot.child("docFirstName").getValue().toString();
                    String familyName = dataSnapshot.child("docFamilyName").getValue().toString();
                    String MedCon = dataSnapshot.child("docOccupation").getValue().toString();
                    String height = dataSnapshot.child("height").getValue().toString();
                    String weight= dataSnapshot.child("weight").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImage);
                    // Glide.with(EditProfileActivity.this).load(image).into(profileImage);
                    textFirstName.setText("First Name:"+firstName);
                    textFamilyName.setText("Family Name:"+familyName);
                    textHeight.setText(height+" cm");
                    textWeight.setText(weight+" kg");
                    textOccupation.setText(MedCon);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}