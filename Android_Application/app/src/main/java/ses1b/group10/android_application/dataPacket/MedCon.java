package ses1b.group10.android_application.dataPacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ses1b.group10.android_application.R;

public class MedCon extends AppCompatActivity {

    private EditText editMedCon;
    private Button nextButton;

    private  String docID,title,medCon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_con);


        editMedCon = findViewById(R.id.messageText);
        nextButton =findViewById(R.id.nextRecordVideo);

        docID =getIntent().getStringExtra("doc_id");
        title =getIntent().getStringExtra("title");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToRecordVideo();
            }
        });

    }

    private void moveToRecordVideo() {
        medCon = editMedCon.getText().toString();

        if(medCon.isEmpty()){
            editMedCon.setError("Medical condition is required");
        }

        Intent recordVideoIntent = new Intent(MedCon.this, RecordActivity.class);
        recordVideoIntent.putExtra("doc_id",docID);
        recordVideoIntent.putExtra("title",title);
        recordVideoIntent.putExtra("medCon",medCon);
        startActivity(recordVideoIntent);
    }
}
