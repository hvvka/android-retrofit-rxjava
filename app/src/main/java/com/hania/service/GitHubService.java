package com.hania.service;

import com.hania.controller.GitHubController;
import com.hania.model.Repository;
import com.hania.model.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GitHubService {

    private GitHubController gitHubController;

    public GitHubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gitHubController = retrofit.create(GitHubController.class);
    }

    public Observable<String> getTopContributors(String userName) {
        return gitHubController.listRepos(userName)
                .flatMapIterable(x -> x)
                .flatMap(repo -> gitHubController.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x)
                .filter(c -> c.getContributions() > 10)
                .map(User::getName).distinct();
        // todo filter one top contributor
    }

    public Observable<Repository> getRepositories(String userName) {
        return gitHubController.listRepos(userName)
                .flatMapIterable(x -> x);
        // todo sort them by date
    }
}
