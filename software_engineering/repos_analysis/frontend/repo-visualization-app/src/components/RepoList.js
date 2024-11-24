import React from "react";

const RepoList = ({repos}) => {
    return (
        <div className = "repo-grid">
            {repos.map((repo) => (
                <a 
                key={repo.id} 
                className="repo-card" 
                href={repo.html_url}
                target="_blank"
                rel="noopener noreferrer"
                style={{ textDecoration: "none", color: "inherit" }}
                >
                <h3>{repo.name}</h3>
                <p>Stars: {repo.stargazers_count}</p>
                <p>Forks: {repo.forks_count}</p>
                <p>Open Issues: {repo.open_issues_count}</p>
                <p>Last Updated: {new Date(repo.pushed_at).toLocaleDateString()}</p>
                </a>
            ))}
        </div>
    );
};

export default RepoList;