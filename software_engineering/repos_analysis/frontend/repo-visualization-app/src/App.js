import React, { useState, useEffect } from "react";
import Search from "./Search";
import SortFilter from "./SortFilter";
import axios from "axios";

const App = () => {
  const [repos, setRepos] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortField, setSortField] = useState("stars");
  const [order, setOrder] = useState("desc");
  const [archived, setArchived] = useState("");
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10); // Fixed page size for now

  const fetchRepos = async () => {
    try {
      // Build the query parameters dynamically
      const params = {
        search: searchTerm || undefined,
        sortBy: sortField,
        order,
        archived: archived || undefined,
        page,
        pageSize,
      };

      // Fetch data from the backend
      const response = await axios.get("http://localhost:8080/fetch-repos/filter", { params });
      setRepos(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Fetch repositories whenever the relevant state changes
  useEffect(() => {
    fetchRepos();
  }, [searchTerm, sortField, order, archived, page]);

  return (
    <div>
      <h1>Fetch Repositories</h1>
      <Search onSearch={setSearchTerm} />
      <SortFilter
        onSort={setSortField}
        onOrderChange={setOrder}
        onArchivedChange={setArchived}
      />
      <div>
        {repos.map((repo) => (
          <div
            key={repo.id}
            style={{
              margin: "10px 0",
              padding: "10px",
              border: "1px solid #ccc",
            }}
          >
            <h3>{repo.name}</h3>
            <p>Stars: {repo.stargazers_count}</p>
            <p>Forks: {repo.forks_count}</p>
            <p>Last Updated: {new Date(repo.pushed_at).toLocaleDateString()}</p>
          </div>
        ))}
      </div>
      {/* Pagination */}
      <div style={{ margin: "20px 0" }}>
        <button
          onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
          disabled={page === 0}
        >
          Previous
        </button>
        <span style={{ margin: "0 10px" }}>Page: {page + 1}</span>
        <button onClick={() => setPage((prev) => prev + 1)}>Next</button>
      </div>
    </div>
  );
};

export default App;
