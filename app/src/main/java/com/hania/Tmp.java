package com.hania;

import com.hania.service.GitHubService;

public class Tmp {

    public static void main(String[] args) {
        String userName = "nokia-wroclaw";
        GitHubService gitHubService = new GitHubService();

        gitHubService.getRepositories(userName).subscribe(System.out::println);

        System.out.println("---------------");

        gitHubService.getTopContributionsNumber("nokia-wroclaw", "innovative-project-jackdaw").subscribe(System.out::println);
        gitHubService.getTopContributorName("nokia-wroclaw", "innovative-project-jackdaw", 38).subscribe(System.out::println);
    }
}
