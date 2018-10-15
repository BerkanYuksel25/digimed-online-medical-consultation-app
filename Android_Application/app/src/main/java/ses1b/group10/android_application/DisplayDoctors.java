package ses1b.group10.android_application;


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
import com.squareup.picasso.Picasso;

import ses1b.group10.android_application.dataPacket.Title;


public class DisplayDoctors extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String doc_id;
    DatabaseReference databaseReference;
    RecyclerView displayDoctors;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_doctors);
        mAuth= FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Doctor");



        databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.keepSynced(true);


        displayDoctors = findViewById(R.id.recylcerView);
        displayDoctors.setHasFixedSize(true);
        displayDoctors.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<DoctorProfile,DoctorViewHolder> adapter = new FirebaseRecyclerAdapter<DoctorProfile, DoctorViewHolder>(
                DoctorProfile.class, R.layout.recycle_view,DoctorViewHolder.class, databaseReference
        ) {
            @Override
            protected void populateViewHolder(DoctorViewHolder viewHolder, DoctorProfile model, final int position) {
                viewHolder.setImage(getApplicationContext(),model.getDocImage());
                viewHolder.setTextFirstName(model.getDocFirstName());
                viewHolder.setLastName(model.getDocFamilyName());
                viewHolder.setTextOccupation(model.getDocOccupation());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doc_id=getRef(position).getKey().toString();
                        Intent intent = new Intent(DisplayDoctors.this,ViewDoctorDetails.class);
                        intent.putExtra("doc_id",doc_id);
                        startActivity(intent);
                    }
                });
            }
        };
        displayDoctors.setAdapter(adapter);
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public DoctorViewHolder(View itemView){
            super(itemView);
            mView=itemView;
        }

        public void setImage(Context context, String image){
            ImageView post_image = mView.findViewById(R.id.imageView);

            Picasso.get().load(image).into(post_image);

        }

        public void setTextFirstName(String name){
            TextView docName = mView.findViewById(R.id.imageName);
            docName.setText(name);

        }

        public void setLastName(String name){
            TextView docLastName = mView.findViewById(R.id.lastName);
            docLastName.setText(name);
        }

        public void setTextOccupation(String occupation){
            TextView docOppcupation = mView.findViewById(R.id.docOcc);
            docOppcupation.setText(occupation);
        }



    }
}
