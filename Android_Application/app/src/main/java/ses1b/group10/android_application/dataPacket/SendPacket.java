package ses1b.group10.android_application.dataPacket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ses1b.group10.android_application.DataPacket;
import ses1b.group10.android_application.DisplayDoctors;
import ses1b.group10.android_application.R;

public class SendPacket extends AppCompatActivity {

    private TextView textTitle,textMedCon,textVideo,textHR;

    private Button submitButton;

    private String  title,medCon;
    String heartRate="No heart was recorded";
    DataPacket dataPacket;

    String videoLink ="No video was recorded";
    String noFile ="No file was uploaded";
    Uri videoUri, fileUri;

//    private int ACTIVITY_START_CAMERA_APP = 1;
//    final static int gallery_pick =2;
//
//    private  int ACTIVITY_START_FILE_APP =3;
//    private  int file_pick=4;



    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private StorageReference uploadVideo;
    private String rID, sID;
    private String saveCurrentDate;
    private String saveCurrentTime;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_packet);

        submitButton = findViewById(R.id.mBtn);

        textHR =findViewById(R.id.displayHeartRate);
        textMedCon=findViewById(R.id.displayMedCon);
        textTitle=findViewById(R.id.displayTitle);
        textVideo=findViewById(R.id.displayVideo);



        progressBar =findViewById(R.id.progressbar);



        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();




        if(mAuth.getCurrentUser() != null){
            sID = mAuth.getCurrentUser().getUid();
        }




        rID =getIntent().getStringExtra("doc_id");
        title=getIntent().getStringExtra("title");
        medCon=getIntent().getStringExtra("medCon");
        videoLink=getIntent().getStringExtra("video_uri");
        heartRate=getIntent().getStringExtra("HR");

        setTextView();

        videoUri =Uri.parse(videoLink);

        uploadVideo = FirebaseStorage.getInstance().getReference().child("PatientVideos");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVideoPacket();
            }
        });



    }

    private void setTextView() {
        textTitle.setText(title);
        textMedCon.setText(medCon);
        textHR.setText(heartRate);
        textVideo.setText(videoLink);
    }

    private void submitVideoPacket() {


        progressBar.setVisibility(View.VISIBLE);
        if (videoUri != null) {

            final StorageReference filePath = uploadVideo.child(sID + ".mp4");
            filePath.putFile(videoUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        String dowURL = task.getResult().getUploadSessionUri().toString();
                        uploadDataPacket(dowURL);

                    }


                }
            });


        }

        else{
            uploadDataPacket(videoLink);
        }
    }



    private void uploadDataPacket(String videoUrl){



        final String message_patient_id = "Data Packet/" + sID + "/" + rID;
        final String message_doc_id = "Data Packet/" + rID + "/" + sID;

        DatabaseReference patient_message_key = myRef.child("Data Packet").child(sID)
                .child(rID).push();
        final String patient_message_info = patient_message_key.getKey();

        Calendar calFordDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());


        Calendar caFordDate = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm");
        saveCurrentTime = currentTime.format(caFordDate.getTime());





        dataPacket = new DataPacket(title,medCon,saveCurrentDate,saveCurrentTime,videoUrl,heartRate);

        progressBar.setVisibility(View.GONE);
        Map messageDetails = new HashMap<>();

        messageDetails.put(message_patient_id + "/" + patient_message_info, dataPacket);
        messageDetails.put(message_doc_id + "/" + patient_message_info, dataPacket);
        progressBar.setVisibility(View.GONE);
        myRef.updateChildren(messageDetails).addOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {

                    Toast.makeText(SendPacket.this, "Data Packet has been sent successfully", Toast.LENGTH_LONG).show();

                    Intent intent =new Intent(SendPacket.this, DisplayDoctors.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SendPacket.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
