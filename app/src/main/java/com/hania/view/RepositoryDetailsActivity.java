package com.hania.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hania.R;
import com.hania.model.Repository;
import com.hania.model.User;
import com.hania.service.GitHubService;

public class RepositoryDetailsActivity extends AppCompatActivity {

    private Repository repository;

    private ProgressBar progressBar;

    private TextView contributionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_details);
        Intent intent = getIntent();
        this.repository = (Repository) intent.getSerializableExtra(MainActivity.REPO);
        initComponents();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initThreads();
    }

    private void initThreads() {
        ProgressBarSetter progressBarSetter = new ProgressBarSetter(this, progressBar);
        Thread progressBarThread = new Thread(progressBarSetter);
        progressBarThread.start();

        RepositoryDetailsSetter repositoryDetailsSetter = new RepositoryDetailsSetter(this, repository);
        Thread repositoryDetailsThread = new Thread(repositoryDetailsSetter);
        repositoryDetailsThread.start();
    }

    private void initComponents() {
        TextView nameText = findViewById(R.id.nameText);
        nameText.setText(repository.getName());
        TextView descriptionText = findViewById(R.id.descriptionText);
        descriptionText.setText(repository.getDescription());
        TextView ownerText = findViewById(R.id.ownerText);
        ownerText.setText(repository.getOwner().getLogin());
        TextView languageText = findViewById(R.id.languageText);
        languageText.setText(repository.getLanguage());
        TextView starsText = findViewById(R.id.starsText);
        starsText.setText(String.valueOf(repository.getStargazersCount()));
        TextView forksText = findViewById(R.id.forksText);
        forksText.setText(String.valueOf(repository.getForksCount()));
        contributionsText = findViewById(R.id.contributionsText);
        progressBar = findViewById(R.id.progressBar);
    }

    private class RepositoryDetailsSetter implements Runnable {

        private RepositoryDetailsActivity parent;

        private Repository repository;

        RepositoryDetailsSetter(RepositoryDetailsActivity parent, Repository repository) {
            this.parent = parent;
            this.repository = repository;
        }

        @Override
        public void run() {
            parent.runOnUiThread(() -> {
                setTopContributions();
                parent.progressBar.setVisibility(View.GONE);
            });
        }

        private void setTopContributions() {
            String username = repository.getOwner().getLogin();
            GitHubService gitHubService = new GitHubService();
            String topContributions = gitHubService.getTopContributionsNumber(username, repository.getName());

            if (!topContributions.equals("")) {
                String topContributorName = gitHubService.getTopContributorName(
                        username, repository.getName(), Integer.valueOf(topContributions));
                parent.contributionsText.setText(String.format("%s: %s", topContributorName, topContributions));

                parent.contributionsText.setOnClickListener(view -> {
                    User user = gitHubService.getUser(topContributorName);
                    String userSummary = String.format("%s%n%s%n%s%n%s%n",
                            user.getName(), user.getLocation(),
                            user.getEmail(), user.getCompany());
                    parent.runOnUiThread(() ->
                            Toast.makeText(getBaseContext(), userSummary, Toast.LENGTH_LONG).show());
                });
            }
        }
    }
}
