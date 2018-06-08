package com.hania.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.hania.R;
import com.hania.model.Repository;
import com.hania.model.User;
import com.hania.service.GitHubService;

public class RepositoryDetailsActivity extends AppCompatActivity {

    private EditText nameText;  // todo change to textview

    private EditText descriptionText;

    private EditText ownerText;

    private EditText languageText;

    private EditText starsText;

    private EditText forksText;

    private EditText contributionsText;

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
        ownerText.setText(repository.getOwner().getLogin());
        languageText = findViewById(R.id.languageText);
        languageText.setText(repository.getLanguage());
        starsText = findViewById(R.id.starsText);
        starsText.setText(String.valueOf(repository.getStargazersCount())); //todo int field
        forksText = findViewById(R.id.forksText);
        forksText.setText(String.valueOf(repository.getForksCount()));

        contributionsText = findViewById(R.id.contributionsText);
        String username = repository.getOwner().getLogin();
        GitHubService gitHubService = new GitHubService();
        String topContributions = gitHubService.getTopContributionsNumber(username, repository.getName()).toBlocking().first();
        String topContributorName = gitHubService.getTopContributorName(
                username, repository.getName(), Integer.valueOf(topContributions)).toBlocking().first();
        contributionsText.setText(String.format("%s: %s", topContributorName, topContributions));

        contributionsText.setOnClickListener(view -> {
            User user = gitHubService.getUser(topContributorName).toBlocking().first();
            String userSummary = String.format("%s%n%s%n%s%n%s%n", user.getName(), user.getLocation(),
                    user.getEmail(), user.getCompany());
            Toast.makeText(getBaseContext(), userSummary, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        // todo return to the previous state
    }
}
