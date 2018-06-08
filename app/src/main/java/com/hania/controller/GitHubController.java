package com.hania.controller;

import com.hania.model.Repository;
import com.hania.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubController {

    /**
     * List User account information
     *
     * @param user GitHub Account
     * @return GitHub user
     */
    @GET("users/{user}")
    Observable<User> listUser(@Path("user") String user);

    /**
     * List GitHub repositories of user
     *
     * @param user GitHub Account
     * @return GitHub repositories
     */
    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepos(@Path("user") String user);

    /**
     * List Contributors of a GitHub Repository
     *
     * @param user GitHub Account
     * @param repo GitHub Repository
     * @return GitHub Repository Contributors
     */
    @GET("repos/{user}/{repo}/contributors")
    Observable<List<User>> listRepoContributors(@Path("user") String user, @Path("repo") String repo);
}
