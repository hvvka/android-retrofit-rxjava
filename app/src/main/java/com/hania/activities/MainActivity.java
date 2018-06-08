package com.hania.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    }

    private void initListeners() {
        submitButton.setOnClickListener(view -> {
            String username = usernameText.getText().toString();
            setListView(username);
        });
    }

    private void setListView(String username) {
        repositories = new GitHubService().getRepositories(username).toList().toBlocking().single();
        List<String> names = new ArrayList<>();
        for (Repository repository : repositories) {
            String name = repository.getName();
            if (name != null) {
                names.add(name);
            } else {
                Toast.makeText(getBaseContext(), "Wrong username!", Toast.LENGTH_LONG).show();
                break;
            }
        }
        reposList.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, names));
        reposList.setOnItemClickListener(
                (adapterView, view, i, l) -> {
                    Intent intent = new Intent(this, RepositoryDetailsActivity.class)
                            .putExtra(REPO, repositories.get(i));
                    startActivity(intent);
                });
    }
}
