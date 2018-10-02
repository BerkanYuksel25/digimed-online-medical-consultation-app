package ses1b.group10.android_application.dataPacket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ses1b.group10.android_application.R;
import ses1b.group10.android_application.heart_rate_monitor.HeartRateMonitor;

public class RecordActivity extends AppCompatActivity{

    private String videoUriCode="No Video was recorded",title,docID,medCon;
    private Button recordButton;
    private int ACTIVITY_START_CAMERA_APP = 0;
    private Uri videoUri;

    private int gallery_pick =1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);

        recordButton =findViewById(R.id.recordButton);






        docID =getIntent().getStringExtra("doc_id");
        title =getIntent().getStringExtra("title");
        medCon =getIntent().getStringExtra("medCon");


        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCamera();
            }
        });



    }


    private void releaseCamera() {


        final CharSequence[] options = {"Record Video", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);

        builder.setTitle("Add Video!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Record Video"))

                {
                    Intent callVideoAppIntent = new Intent();
                    callVideoAppIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(callVideoAppIntent, ACTIVITY_START_CAMERA_APP);

                } else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,gallery_pick);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == RESULT_OK) {

            if (requestCode == ACTIVITY_START_CAMERA_APP) {
                videoUri = data.getData();


            }

            else if (requestCode == gallery_pick) {
                videoUri = data.getData();

            }

            else{
                videoUri = data.getData();
            }

        }


        videoUriCode =videoUri.toString();

        Intent intent =  new Intent(RecordActivity.this,HeartRateMonitor.class);
        intent.putExtra("video_uri",videoUriCode);
        intent.putExtra("medCon",medCon);
        intent.putExtra("title",title);
        intent.putExtra("doc_id",docID);
        startActivity(intent);
    }



}