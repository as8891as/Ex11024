package com.example.ex11024;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
{
    private EditText usernameEditText;
    private Button resetPointsButton;

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String KEY_HIGHSCORE = "highScore";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        usernameEditText = findViewById(R.id.etUsername);
        resetPointsButton = findViewById(R.id.btnResetPoints);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedName = prefs.getString(KEY_USERNAME, "");
        usernameEditText.setText(savedName);

        usernameEditText.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                String name = usernameEditText.getText().toString().trim();
                prefs.edit().putString(KEY_USERNAME, name).apply();
                if (!name.isEmpty()) Toast.makeText(this, "שם משתמש נשמר: " + name, Toast.LENGTH_SHORT).show();
            }
        });

        resetPointsButton.setOnClickListener(v ->
        {
            prefs.edit().putInt(KEY_HIGHSCORE, 0).apply();
            Toast.makeText(this, "הניקוד האישי נמחק בהצלחה!", Toast.LENGTH_SHORT).show();
        });

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("הגדרות");
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}