package com.example.ex11024;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CreditActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Credits");
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}