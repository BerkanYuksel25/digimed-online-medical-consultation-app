package ses1b.group10.android_application.Doctor_End;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ses1b.group10.android_application.DisplayDoctors;
import ses1b.group10.android_application.DoctorProfile;
import ses1b.group10.android_application.EditProfileActivity;
import ses1b.group10.android_application.HomeActivity;
import ses1b.group10.android_application.MapsActivity;
import ses1b.group10.android_application.PatientProfile;
import ses1b.group10.android_application.R;
import ses1b.group10.android_application.ViewDoctorDetails;

public class DocHome extends AppCompatActivity {

    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private CircleImageView profilePicture;
    private TextView userName;
    private Toolbar toolbar;

    private DatabaseReference patientProfile;
    private FirebaseAuth mAuth;

    private  String currentPatientId;

    PatientProfile patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home);

        mAuth=FirebaseAuth.getInstance();
        currentPatientId =mAuth.getCurrentUser().getUid();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home")
        ;
        patientProfile =FirebaseDatabase.getInstance().getReference().child("Doctors");


        drawerLayout = findViewById(R.id.drawable_layout);
        drawerToggle = new ActionBarDrawerToggle(DocHome.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView =findViewById(R.id.nav_view);

        final View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        profilePicture =headerView.findViewById(R.id.profile_image);
        userName =  headerView.findViewById(R.id.username);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuOption(item);
                return false;
            }
        });




        patientProfile.child(currentPatientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String fullname = dataSnapshot.child("docFirstName").getValue().toString()
                            +dataSnapshot.child("docFamilyName").getValue().toString();
                    userName.setText(fullname);



                    String profileImage = dataSnapshot.child("docImage").getValue().toString();
                    Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(profilePicture);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuOption(MenuItem item) {


        switch (item.getItemId()){




            case R.id.Home:
                Toast.makeText(this,"Home Activity",Toast.LENGTH_SHORT).show();
                break;

            case R.id.profile:
                sendUserToprofileActivity();
                break;

            case R.id.medicalcen:
                Intent homeIntent= new Intent(this,MapsActivity.class);
                startActivity(homeIntent);
                break;

        }


    }

    private void sendUserToprofileActivity() {

        Intent homeIntent= new Intent(this,EditDocProfileActivity.class);
        startActivity(homeIntent);
    }
}