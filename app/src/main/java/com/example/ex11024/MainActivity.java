package com.example.ex11024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;
    private Button addQuestionButton;
    private TextView scoresTextView;
    private TextView questionTextView;
    private LinearLayout answersContainer;

    private final String BASE_QUESTIONS_FILE = "base_res.txt";
    private final String CUSTOM_QUESTIONS_FILE = "custom_questions.txt";

    private final ArrayList<String> dataList = new ArrayList<>();
    private int currentIndex = 0;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int score = 0;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "QuizPrefs";
    private static final String KEY_HIGHSCORE = "highScore";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoresTextView = findViewById(R.id.tvScores);
        questionTextView = findViewById(R.id.tvQuestion);
        answer1Button = findViewById(R.id.btnAnswer1);
        answer2Button = findViewById(R.id.btnAnswer2);
        answer3Button = findViewById(R.id.btnAnswer3);
        answer4Button = findViewById(R.id.btnAnswer4);
        answersContainer = findViewById(R.id.answersLayout);
        addQuestionButton = findViewById(R.id.btnAddQuestion);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        answer1Button.setOnClickListener(v ->
        {
            checkAnswer(answer1);
        });
        answer2Button.setOnClickListener(v ->
        {
            checkAnswer(answer2);
        });
        answer3Button.setOnClickListener(v ->
        {
            checkAnswer(answer3);
        });
        answer4Button.setOnClickListener(v ->
        {
            checkAnswer(answer4);
        });

        addQuestionButton.setOnClickListener(v ->
        {
            startActivity(new Intent(this, AddQuestionActivity.class));
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadAllQuestions();
        loadNextQuestion();
    }

    private void loadAllQuestions()
    {
        ArrayList<String> newDataList = new ArrayList<>();
        try
        {
            String rawFileName = BASE_QUESTIONS_FILE.replace(".txt", "");
            int resourceID = getResources().getIdentifier(rawFileName, "raw", getPackageName());
            InputStream inputStream = getResources().openRawResource(resourceID);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) newDataList.add(line);
            reader.close();
            inputStream.close();
        }
        catch (Exception e)
        {
        }

        try
        {
            FileInputStream fis = openFileInput(CUSTOM_QUESTIONS_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) newDataList.add(line);
            reader.close();
            fis.close();
        }
        catch (Exception e)
        {
        }

        ArrayList<String> cleanList = new ArrayList<>();
        for (String line : newDataList)
        {
            String[] parts = line.split("\\?");
            for (String s : parts)
            {
                if (!s.trim().isEmpty()) cleanList.add(s.trim());
            }
        }

        if (dataList.isEmpty()) dataList.addAll(cleanList);
        else if (cleanList.size() > dataList.size()) dataList.addAll(cleanList.subList(dataList.size(), cleanList.size()));
    }

    private void loadNextQuestion()
    {
        if (dataList == null || currentIndex + 4 >= dataList.size())
        {
            questionTextView.setText("Quiz Finished!");
            answersContainer.setVisibility(LinearLayout.GONE);
            scoresTextView.setText("Final Score: " + score + " | High: " + prefs.getInt(KEY_HIGHSCORE, 0));
            return;
        }

        question = dataList.get(currentIndex);
        answer1 = dataList.get(currentIndex + 1);
        answer2 = dataList.get(currentIndex + 2);
        answer3 = dataList.get(currentIndex + 3);
        answer4 = dataList.get(currentIndex + 4);

        questionTextView.setText(question);
        answer1Button.setText(answer1);
        answer2Button.setText(answer2);
        answer3Button.setText(answer3);
        answer4Button.setText(answer4);

        scoresTextView.setText("Score: " + score + " | High: " + prefs.getInt(KEY_HIGHSCORE, 0));
        answersContainer.setVisibility(LinearLayout.VISIBLE);
        currentIndex += 5;
    }

    private void checkAnswer(String selectedAnswer)
    {
        if (selectedAnswer.equals(answer1)) score++;
        int highScore = prefs.getInt(KEY_HIGHSCORE, 0);
        if (score > highScore) prefs.edit().putInt(KEY_HIGHSCORE, score).apply();
        loadNextQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else if (id == R.id.menu_credits)
        {
            startActivity(new Intent(this, CreditActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}