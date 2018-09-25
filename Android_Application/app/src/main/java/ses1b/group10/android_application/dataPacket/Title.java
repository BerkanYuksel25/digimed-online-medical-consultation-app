package ses1b.group10.android_application.dataPacket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ses1b.group10.android_application.R;

public class Title extends AppCompatActivity {

    private EditText editTitle;
    private Button buttonNext;
    String docID,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        editTitle =findViewById(R.id.packetTitle);
        buttonNext = findViewById(R.id.nextMedCon);


        docID =getIntent().getStringExtra("doc_id");
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMedCon();
            }
        });


    }

    private void moveToMedCon() {
        title = editTitle.getText().toString();

        if(title.isEmpty()){
            editTitle.setError("Title is required.");
            editTitle.requestFocus();
            return;
        }

        Intent intentMedCon = new Intent(Title.this,MedCon.class);
        intentMedCon.putExtra("title",title);
        intentMedCon.putExtra("doc_id",docID);
        startActivity(intentMedCon);
    }
}
