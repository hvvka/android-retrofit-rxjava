package com.hania;

import com.hania.service.GitHubService;

public class Tmp {

    public static void main(String[] args) {
        String userName = "nokia-wroclaw";
        GitHubService gitHubService = new GitHubService();

        String result = gitHubService.getTopContributorName("d", "d", 1);
        System.out.println(result);

        System.out.println("---------------");

        gitHubService.getRepositories(userName).subscribe(System.out::println);

        System.out.println("---------------");

        System.out.println(gitHubService.getTopContributionsNumber("nokia-wroclaw", "innovative-project-jackdaw"));
        System.out.println(gitHubService.getTopContributorName("nokia-wroclaw", "innovative-project-jackdaw", 38));

        System.out.println("---------------");

        System.out.println(gitHubService.getTopContributionsNumber("nokia-wroclw", "innovative-project-jackdaw"));
        System.out.println(gitHubService.getTopContributorName("nokia-wroclw", "innovative-project-jackdaw", 38));
    }
}
