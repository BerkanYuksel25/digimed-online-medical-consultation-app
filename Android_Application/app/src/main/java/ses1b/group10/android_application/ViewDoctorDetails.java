package ses1b.group10.android_application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ses1b.group10.android_application.dataPacket.Title;

public class ViewDoctorDetails extends AppCompatActivity {

    private TextView textFirstName,textFamilyName,textOccupation;
    private Button createDataPacket, pairRequest;
    private CircleImageView profileImage;


    private DatabaseReference profileRef, pairRequestRef;
    private FirebaseAuth mAuth;

    private String currentDoctorId;
    private String currentPatientId;

    private String pairStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor_details);



        mAuth= FirebaseAuth.getInstance();

        currentPatientId =mAuth.getCurrentUser().getUid();


        currentDoctorId =getIntent().getStringExtra("doc_id");
        profileRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(currentDoctorId);
        pairRequestRef = FirebaseDatabase.getInstance().getReference().child("PairRequests");

        textFirstName =findViewById(R.id.docfirstName);
        textFamilyName=findViewById(R.id.doclastName);
        textOccupation=findViewById(R.id.occupation);

        profileImage =findViewById(R.id.docProfileImage);
        createDataPacket=findViewById(R.id.createpacket);
        pairRequest=findViewById(R.id.pairrequest);

        pairStatus = "not_friends";


        showDocInfo();


        createDataPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDoctorDetails.this,Title.class);
                intent.putExtra("doc_id",currentDoctorId);
                startActivity(intent);
            }
        });

        pairRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(pairStatus.equals("not_friends")){
                        SendPairRequest();
                    }
                    else if(pairStatus.equals("request_sent")){
                        Toast.makeText(ViewDoctorDetails.this, "Awaiting Doctor Response...", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }

    private void SendPairRequest() {
        pairRequestRef.child(currentPatientId).child(currentDoctorId).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pairRequestRef.child(currentDoctorId).child(currentPatientId).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pairStatus = "request_sent";
                                pairRequest.setText("Pair Request Sent");
                            }
                        }
                    });
                }
            }
        });
    }

    private void showDocInfo() {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String image = dataSnapshot.child("docImage").getValue().toString();
                    String firstName= dataSnapshot.child("docFirstName").getValue().toString();
                    String familyName = dataSnapshot.child("docFamilyName").getValue().toString();
                    String Occupation = dataSnapshot.child("docOccupation").getValue().toString();


                    Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImage);
                    textFirstName.setText("First Name: "+firstName);
                    textFamilyName.setText("Family Name: "+familyName);
                    textOccupation.setText(Occupation);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

