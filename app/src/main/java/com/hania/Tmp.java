package com.hania;

import com.hania.model.Repository;
import com.hania.service.GitHubService;

import java.util.List;

public class Tmp {

    public static void main(String[] args) {
        String userName = "hvvka";
        GitHubService gitHubService = new GitHubService();

        gitHubService.getRepositories(userName).subscribe(System.out::println);

        System.out.println("---------------");

        List<Repository> repos = gitHubService.getRepositories(userName).toList().toBlocking().single();
        for (Repository repo : repos)
            System.out.println(repo);
    }
}
