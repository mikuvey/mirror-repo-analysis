import React, { useState, useEffect } from "react";
import Search from "./Search";
import axios from "axios";

const App = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [repos, setRepos] = useState([]);
  const [filteredRepos, setFilteredRepos] = useState([]);

  //Fetch all repositories when the component loads
  useEffect(() => {
    const fetchRepos = async () => {
      try {
        const response = await axios.get("http://localhost:8080/fetch-repos/filter");
        setRepos(response.data);
        setFilteredRepos(response.data); //Initially display all repos
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchRepos();
  }, []);

  //Filter the repositories based on the search term
  const handleSearch = (term) => {
    setSearchTerm(term);
    if (term.trim() === "") {
      setFilteredRepos(repos); //Show all repos if the search term is empty
    } else {
      setFilteredRepos(
        repos.filter((repo) => repo.name.toLowerCase().includes(term.toLowerCase()))
      );
    }
  };

  return (
    <div>
      <h1>Fetch Repositories</h1>
      <Search onSearch={handleSearch} />
      <div>
        {filteredRepos.map((repo) => (
          <div key={repo.id} style={{ margin: "10px 0", padding: "10px", border: "1px solid #ccc" }}>
            <h3>{repo.name}</h3>
            <p>Stars: {repo.stargazers_count}</p>
            <p>Forks: {repo.forks_count}</p>
            <p>Open issues: {repo.open_issues_count}</p>
            <p>Last activity: {repo.pushed_at}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default App;
