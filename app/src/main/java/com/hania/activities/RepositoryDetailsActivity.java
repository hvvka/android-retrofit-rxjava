package com.hania.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.hania.R;
import com.hania.model.Repository;

public class RepositoryDetailsActivity extends AppCompatActivity {

    private EditText nameText;

    private EditText descriptionText;

    private EditText ownerText;

    private EditText languageText;

    private EditText starsText;

    private EditText forksText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_details);
        Intent intent = getIntent();
        Repository repository = (Repository) intent.getSerializableExtra(MainActivity.REPO);
        initComponents(repository);
    }

    private void initComponents(Repository repository) {
        nameText = findViewById(R.id.nameText);
        nameText.setText(repository.getName());
        descriptionText = findViewById(R.id.descriptionText);
        descriptionText.setText(repository.getDescription());
        ownerText = findViewById(R.id.ownerText);
        ownerText.setText(repository.getOwner().getName());
        languageText = findViewById(R.id.languageText);
        languageText.setText(repository.getLanguage());
        starsText = findViewById(R.id.starsText);
        starsText.setText(String.valueOf(repository.getStargazersCount())); //todo int field
        forksText = findViewById(R.id.forksText);
        forksText.setText(String.valueOf(repository.getForksCount()));
    }

    // todo top contributor field
    // todo Toast with contributor info after click

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
