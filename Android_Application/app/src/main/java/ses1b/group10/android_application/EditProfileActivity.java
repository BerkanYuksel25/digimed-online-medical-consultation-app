package ses1b.group10.android_application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private TextView textFirstName,textFamilyName,textMedCon,textWeight,textHeight;
    private Button editProfile;
    private CircleImageView profileImage;
    private PatientProfile patientProfile;

    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;

    private String currentPatientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mAuth= FirebaseAuth.getInstance();

        currentPatientId= mAuth.getCurrentUser().getUid();
        profileRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(currentPatientId);

        textFirstName =findViewById(R.id.firstName);
        textFamilyName=findViewById(R.id.lastName);
        textHeight =findViewById(R.id.height);
        textWeight=findViewById(R.id.weight);
        textMedCon=findViewById(R.id.medcon);
        profileImage =findViewById(R.id.profileimage);
        editProfile=findViewById(R.id.editProfile);


        setpatientInfo();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setpatientInfo() {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String image = dataSnapshot.child("patientImage").getValue().toString();
                    String firstName= dataSnapshot.child("patientFirstName").getValue().toString();
                    String familyName = dataSnapshot.child("patientFamilyName").getValue().toString();
                    String MedCon = dataSnapshot.child("patientMedCon").getValue().toString();
                    String height = dataSnapshot.child("patientHeight").getValue().toString();
                    String weight= dataSnapshot.child("patientWeight").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImage);
                   // Glide.with(EditProfileActivity.this).load(image).into(profileImage);
                    textFirstName.setText("First Name:"+firstName);
                    textFamilyName.setText("Family Name:"+familyName);
                    textHeight.setText(height+"cm");
                    textWeight.setText(weight+"kg");
                    textMedCon.setText(MedCon);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
