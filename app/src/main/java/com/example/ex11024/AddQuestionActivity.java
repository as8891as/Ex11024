package com.example.ex11024;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class AddQuestionActivity extends AppCompatActivity
{
    private EditText questionEditText;
    private EditText answer1EditText;
    private EditText answer2EditText;
    private EditText answer3EditText;
    private EditText answer4EditText;
    private Button saveButton;
    private Button backButton;
    private final String CUSTOM_FILE = "custom_questions.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        questionEditText = findViewById(R.id.etQuestion);
        answer1EditText = findViewById(R.id.etAnswer1);
        answer2EditText = findViewById(R.id.etAnswer2);
        answer3EditText = findViewById(R.id.etAnswer3);
        answer4EditText = findViewById(R.id.etAnswer4);
        saveButton = findViewById(R.id.btnSave);
        backButton = findViewById(R.id.btnBack);

        saveButton.setOnClickListener(v ->
        {
            String question = questionEditText.getText().toString().trim();
            String answer1 = answer1EditText.getText().toString().trim();
            String answer2 = answer2EditText.getText().toString().trim();
            String answer3 = answer3EditText.getText().toString().trim();
            String answer4 = answer4EditText.getText().toString().trim();

            if (question.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty())
            {
                Toast.makeText(AddQuestionActivity.this, "כל השדות חייבים להיות מלאים", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(question).append("?");
            sb.append(answer1).append("?");
            sb.append(answer2).append("?");
            sb.append(answer3).append("?");
            sb.append(answer4).append("?");
            sb.append("\n");

            try
            {
                FileOutputStream fos = openFileOutput(CUSTOM_FILE, MODE_APPEND);
                fos.write(sb.toString().getBytes());
                fos.close();

                Toast.makeText(AddQuestionActivity.this, "השאלה נשמרה בהצלחה!", Toast.LENGTH_SHORT).show();
                finish();

            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(AddQuestionActivity.this, "שגיאה בשמירת השאלה", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v ->
        {
            finish();
        });

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("הוספת שאלה");
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}