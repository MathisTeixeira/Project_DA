package com.teixeira.mathis.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.teixeira.mathis.project.Leaderboard_Activity.SCORES_FILE_PATH;

public class Save_Activity extends AppCompatActivity {

    private int score = 0;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Bundle b = getIntent().getExtras();
        if(b != null)
            score = b.getInt("score");

        TextView textScore = findViewById(R.id.textScore2);
        textScore.setText(String.valueOf(score));
    }

    public void saveScore(View view){
        EditText eTName = findViewById(R.id.editText);
        name = eTName.getText().toString();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE_PATH, true));
            writer.write("\n"+name+";;"+score);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, Leaderboard_Activity.class);
        startActivity(intent);
    }
}