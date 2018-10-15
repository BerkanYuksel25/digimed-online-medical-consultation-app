package ses1b.group10.android_application.Doctor_End;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import ses1b.group10.android_application.DoctorProfile;
import ses1b.group10.android_application.PatientProfile;
import ses1b.group10.android_application.R;
import ses1b.group10.android_application.ViewDoctorDetails;


public class DisplayPotentialPatients extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String pat_id;
    DatabaseReference databaseReference,requestRef,PariedRef;
    Toolbar toolbar;

    RecyclerView displayPatients;

    private String currentDoctorID;

    String patient;
    private String pairStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_potential_patients);
        mAuth = FirebaseAuth.getInstance();
        currentDoctorID = mAuth.getCurrentUser().getUid();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Patient");



        databaseReference= FirebaseDatabase.getInstance().getReference().child("Patients");
        databaseReference.keepSynced(true);
        requestRef =FirebaseDatabase.getInstance().getReference("PairRequests");
        PariedRef=FirebaseDatabase.getInstance().getReference().child("Paired");



        displayPatients = findViewById(R.id.recyclerView);
        displayPatients.setHasFixedSize(true);
        displayPatients.setLayoutManager(new LinearLayoutManager(this));

        pairStatus = "not_friends";
    }

    @Override
    protected void onStart() {
        super.onStart();

















        FirebaseRecyclerAdapter<PatientProfile,PatientViewHolder> adapter = new FirebaseRecyclerAdapter<PatientProfile, PatientViewHolder>(
                PatientProfile.class, R.layout.patient_requests,PatientViewHolder.class, databaseReference
        ) {
            @Override
            protected void populateViewHolder(PatientViewHolder viewHolder, PatientProfile model, final int position) {
                viewHolder.setPatientImage(getApplicationContext(),model.getPatientImage());
                viewHolder.setPatientFirstName(model.getPatientFirstName());
                viewHolder.setPatientFamilyName(model.getPatientFamilyName());
                viewHolder.setPatientMedCon(model.getPatientMedCon());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pat_id = getRef(position).getKey().toString();
                        Intent intent = new Intent(DisplayPotentialPatients.this,ViewDoctorDetails.class);
                        intent.putExtra("doc_id",pat_id);
                        startActivity(intent);
                    }
                });
            }


        };
        displayPatients.setAdapter(adapter);
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public PatientViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }

        public void setPatientImage(Context context, String image){
            ImageView post_image = mView.findViewById(R.id.imageView);

            Picasso.get().load(image).into(post_image);

        }

        public void setPatientFirstName(String name){
            TextView docName = mView.findViewById(R.id.imageName);
            docName.setText(name);

        }

        public void setPatientFamilyName(String name){
            TextView docLastName = mView.findViewById(R.id.lastName);
            docLastName.setText(name);
        }

        public void setPatientMedCon(String occupation){
            TextView docOppcupation = mView.findViewById(R.id.medcon);
            docOppcupation.setText(occupation);
        }



    }
}
