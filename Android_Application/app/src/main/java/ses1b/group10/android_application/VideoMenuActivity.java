package ses1b.group10.android_application;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class VideoMenuActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(ContextCompat.checkSelfPermission(VideoMenuActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(VideoMenuActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(VideoMenuActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(VideoMenuActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        } else {
            doStuff();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_camera = new Intent( VideoMenuActivity.this, RecordActivity.class);
                startActivity(open_camera);
            }
        });
    }

    public void doStuff(){
        arrayList = new ArrayList<>();
        getVideos();
        for(String title: arrayList){
            System.out.println("Video title: " + title);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        //listView = findViewById(R.id.videoList);
        //listView.setAdapter(adapter);
    }

    public void getVideos(){
        ContentResolver contentResolver = getContentResolver();
        Uri videoUri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;
        Cursor videoCursor = contentResolver.query(videoUri, null, null, null, null);

        if(videoCursor != null && videoCursor.moveToFirst()){
            int videoTitle = videoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
            int videoDate = videoCursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN);


            do  {
                String currentTitle = videoCursor.getString(videoTitle);
                //String currentDate = videoCursor.getString(videoDate);
                arrayList.add(currentTitle /* + "\n" + currentDate */);
            } while (videoCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(VideoMenuActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                        doStuff();
                    }
                } else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }




}

