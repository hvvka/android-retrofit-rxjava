package com.hania.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hania.R;
import com.hania.model.Repository;
import com.hania.service.GitHubService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String REPO = "Repository";

    private ListView reposList;

    private EditText usernameText;

    private Button submitButton;

    private List<Repository> repositories;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        reposList = findViewById(R.id.reposList);
        usernameText = findViewById(R.id.usernameText);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initListeners() {
        submitButton.setOnClickListener(view -> {
            ProgressBarSetter progressBarSetter = new ProgressBarSetter(this, progressBar);
            Thread progressBarThread = new Thread(progressBarSetter);
            progressBarThread.start();

            ListSetter listSetter = new ListSetter(this);
            Thread listThread = new Thread(listSetter);
            listThread.start();
        });
    }

    private class ListSetter implements Runnable {

        private MainActivity parent;

        ListSetter(MainActivity parent) {
            this.parent = parent;
        }

        @Override
        public void run() {
            String username = usernameText.getText().toString();
            List<String> names = getData(username);
            runOnUiThread(new Thread(() -> {
                setListView(names);
                progressBar.setVisibility(View.GONE);
            }));
        }

        private List<String> getData(String username) {
            repositories = new GitHubService().getRepositories(username).toList().toBlocking().single();
            List<String> names = new ArrayList<>();
            for (Repository repository : repositories) {
                String name = repository.getName();
                if (name != null) {
                    names.add(name);
                } else {
                    parent.runOnUiThread(() -> Toast.makeText(parent.getBaseContext(),
                            "Invalid username!", Toast.LENGTH_LONG).show());
                    break;
                }
            }
            return names;
        }

        private void setListView(List<String> names) {
            reposList.setAdapter(new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, names));
            reposList.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent(getBaseContext(), RepositoryDetailsActivity.class)
                        .putExtra(REPO, repositories.get(i));
                startActivity(intent);
            });
        }
    }
}
