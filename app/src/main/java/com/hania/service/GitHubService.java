package com.hania.service;

import com.hania.controller.GitHubController;
import com.hania.model.Repository;
import com.hania.model.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GitHubService {

    private static final String GITHUB_API = "https://api.github.com/";

    private GitHubController gitHubController;

    public GitHubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gitHubController = retrofit.create(GitHubController.class);
    }

    public Observable<String> getTopContributionsNumber(String userName, String repositoryName) {
        return gitHubController.listRepos(userName)
                .flatMapIterable(x -> x)
                .filter(repo -> repo.getName().equals(repositoryName))
                .flatMap(repo -> gitHubController.listRepoContributors(userName, repo.getName()))
                .flatMapIterable(x -> x)
                .map(User::getContributions)
                .toSortedList()
                .flatMapIterable(x -> x)
                .map(String::valueOf)
                .last();
    }

    public Observable<String> getTopContributorName(String userName, String repositoryName, Integer contributions) {
        return gitHubController.listRepoContributors(userName, repositoryName)
                .flatMapIterable(x -> x)
                .filter(u -> u.getContributions().equals(contributions))
                .map(User::getLogin)
                .first();
    }

    public Observable<User> getUser(String userName) {
        return gitHubController.listUser(userName);
    }

    public Observable<Repository> getRepositories(String userName) {
        return gitHubController.listRepos(userName)
                .flatMapIterable(x -> x);
        // todo sort them by date
    }
}
