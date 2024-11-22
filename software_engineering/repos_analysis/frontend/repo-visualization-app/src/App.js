import React, { useState, useEffect } from "react";
import Search from "./Search";
import SortFilter from "./SortFilter";
import axios from "axios";
import "./styles/App.css";

const App = () => {
  const [repos, setRepos] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortField, setSortField] = useState("stars");
  const [order, setOrder] = useState("desc");
  const [archived, setArchived] = useState("");
  const [page, setPage] = useState(0);
  const [pageSize] = useState(6);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [reset, setReset] = useState(false); // New flag for initial fetch/reset

  const fetchRepos = async () => {
    setLoading(true);

    const params = {
      search: searchTerm || undefined,
      sortBy: sortField,
      order,
      archived: archived || undefined,
      page,
      pageSize,
    };

    try {
      const response = await axios.get("http://localhost:8080/fetch-repos/filter", { params });

      if (response.data.length < pageSize) {
        setHasMore(false); // No more data
      }

      setRepos((prevRepos) =>
        reset ? response.data : [...prevRepos, ...response.data]
      );

      setReset(false); // Reset flag after fetching
    } catch (error) {
      console.error("Error fetching repositories:", error);
    } finally {
      setLoading(false);
    }
  };

  // Fetch repositories when filters/search change (initial fetch)
  useEffect(() => {
    setRepos([]);
    setPage(0);
    setHasMore(true);
    setReset(true); // Indicate reset for initial fetch
  }, [searchTerm, sortField, order, archived]);

  // Fetch repositories when the page changes
  useEffect(() => {
    if (reset || page === 0) return; // Skip if it's an initial fetch
    fetchRepos();
  }, [page]);

  // Fetch initial repositories
  useEffect(() => {
    fetchRepos();
  }, [reset]);

  return (
    <div>
      <h1>Fetch Repositories</h1>
      <Search onSearch={setSearchTerm} />
      <SortFilter
        onSort={setSortField}
        onOrderChange={setOrder}
        onArchivedChange={setArchived}
      />
      <div className="repo-grid">
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
      {loading && <p>Loading...</p>}
      {!loading && hasMore && (
        <button onClick={() => setPage((prevPage) => prevPage + 1)} disabled={loading}>
          Load More
        </button>
      )}
      {!hasMore && <p>No more repositories to load.</p>}
    </div>
  );
};

export default App;


