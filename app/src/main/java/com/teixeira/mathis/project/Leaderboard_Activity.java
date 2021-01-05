package com.teixeira.mathis.project;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Leaderbaord shows best scores
 * Private class Score is for pratical uses
 */

public class Leaderboard_Activity extends AppCompatActivity {

    public static String TAG = "Leaderboard_Activity";
    public static final int PERMISSIONS_REQUEST = 0;
    public static final String REQUEST_URL = "url";
    // URL of the score file in the github repository
    public static final String URL_PATH = "https://raw.githubusercontent.com/MathisTeixeira/Project_DA/master/scores.txt";
    public static final String INTENT_FILTER = "filter";
    public static String SCORES_FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/scores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Check permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            // Permission already granted
            if (!new File(SCORES_FILE_PATH).exists()) {
                downloadScores();
            } else {
                listScores();
            }

        } else {

            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST);

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Register the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(scoresReceiver,
                new IntentFilter(INTENT_FILTER));

    }

    @Override
    public void onPause() {
        super.onPause();

        // Unregister the BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(scoresReceiver);

    }

    private BroadcastReceiver scoresReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Scores received...");

            listScores();

        }

    };

    @Override
    public void onRequestPermissionsResult(int request, String permissions[], int[] results) {
        if (request == PERMISSIONS_REQUEST) {// If request is cancelled, the result arrays are empty
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission was granted, yay! Do something useful
                downloadScores();
                listScores();
            } else {

                // Permission was denied, boo! Disable the
                // functionality that depends on this permission
                Toast.makeText(this, "Permission denied to access device's storage", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // Download the scores from the given url
    private void downloadScores() {
        Log.d(TAG, "Downloading scores...");

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(REQUEST_URL, URL_PATH);
        startService(intent);

    }

    // Parse the data from strings separated with ';;' to Score objects
    private List<Score> parseScores() {
        Log.d(TAG, "Parsing...");
        List<Score> list = new ArrayList<>();

        // Open and read file
        try {

            FileReader in = new FileReader(SCORES_FILE_PATH);
            BufferedReader br = new BufferedReader(in);

            String line;

            // Read the file line by line
            while ((line = br.readLine()) != null && line.contains(";;")) {
                String[] lineParts = line.split(";;");
                Log.d(TAG, "Name: "+lineParts[0]+"\nScore: "+lineParts[1]);
                Score score = new Score(lineParts[0], lineParts[1]);
                list.add(score);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Convert the parsed list of scores into a list view for the user to see
    private void listScores() {
        Log.d(TAG, "Listing...");

        List<Score> list = parseScores();

        // Create ListView
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(Leaderboard_Activity.this, android.R.layout.simple_list_item_1, list));
    }

    // A button uses this function to go back to the main menu
    public void goToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

// This class takes the name and score of a player
class Score{
    private String name;
    private String score;

    public Score(String name, String score){
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString(){
        return name + " : " + score + " points";
    }
}
