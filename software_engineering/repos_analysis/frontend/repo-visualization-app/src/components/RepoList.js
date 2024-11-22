import React from "react";

const RepoList = ({repos}) => {
    return (
        <div className = "repo-grid">
            {repos.map((repo) => (
                <div key={repo.id} className="repo-card">
                <h3>{repo.name}</h3>
                <p>Stars: {repo.stargazers_count}</p>
                <p>Forks: {repo.forks_count}</p>
                <p>Open Issues: {repo.open_issues_count}</p>
                <p>Last Updated: {new Date(repo.pushed_at).toLocaleDateString()}</p>
                </div>
            ))}
        </div>
    );
};

export default RepoList;