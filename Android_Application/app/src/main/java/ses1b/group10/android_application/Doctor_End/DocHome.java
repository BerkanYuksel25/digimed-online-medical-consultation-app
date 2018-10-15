package ses1b.group10.android_application.Doctor_End;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import ses1b.group10.android_application.DisplayDoctors;
import ses1b.group10.android_application.EditProfileActivity;
import ses1b.group10.android_application.HomeActivity;
import ses1b.group10.android_application.R;

import static ses1b.group10.android_application.R.id.doctorsHome;

public class DocHome extends AppCompatActivity {

    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private CircleImageView profilePicture;
    private TextView userName;
    private android.support.v7.widget.Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference doctorProfile;
    private String currentDoctorID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_home);


        mAuth = FirebaseAuth.getInstance();
        currentDoctorID = mAuth.getCurrentUser().getUid();

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Home");

        doctorProfile = FirebaseDatabase.getInstance().getReference().child("Doctors");

        drawerLayout = findViewById(R.id.drawable_layout);
        drawerToggle = new ActionBarDrawerToggle(DocHome.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        final View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        profilePicture = headerView.findViewById(R.id.profile_image);
        userName = headerView.findViewById(R.id.username);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuOption(item);
                return false;
            }
        });

    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item){

            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        private void menuOption(MenuItem item) {


            switch (item.getItemId()){

                case R.id.doctorsHome:
                    Toast.makeText(this,"Home Activity",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.doctorsProfile:
                    Intent homeIntent= new Intent(this,EditDocProfileActivity.class);
                    startActivity(homeIntent);
                    break;

//                case R.id.patientRequests:
//                    Intent pairtIntent = new Intent(this,DisplayPotentialPatients.class);
//                    startActivity(pairtIntent);
//                    break;
            }


        }




}
