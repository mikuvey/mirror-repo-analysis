import React, { useState, useEffect } from "react";
import Search from "./components/Search";
import SortFilter from "./components/SortFilter";
import RepoList from "./components/RepoList";
import Pagination from "./components/Pagination";
import { fetchRepositories } from "./services/api";
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
      const data = await fetchRepositories(params);
      if (data.length < pageSize) {
        setHasMore(false);
      }

      setRepos((prevRepos) => {
        const newRepos = data.filter(
          (repo) => !prevRepos.some((existing) => existing.id === repo.id)
        );
        return [...prevRepos, ...newRepos];
      });
    } catch (error) {
      console.error("Error fetching repositories:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (page > 0) {
      fetchRepos();
    }
  }, [page]);

  useEffect(() => {
    setRepos([]);
    setPage(0);
    setHasMore(true);
    fetchRepos();
  }, [searchTerm, sortField, order, archived]);

  return (
    <div className="app-container">
      <header>Fetch Repositories</header>
      <main className="content-container">
        <div className="filters">
          <Search onSearch={setSearchTerm} />
          <SortFilter
            onSort={setSortField}
            onOrderChange={setOrder}
            onArchivedChange={setArchived}
          />
        </div>
        <RepoList repos={repos} />
        <div style={{ textAlign: "center", padding: "20px" }}>
          {loading && <p>Loading...</p>}
          {!loading && hasMore && (
            <button onClick={() => setPage((prevPage) => prevPage + 1)}>Load More</button>
          )}
          {!hasMore && <p>No more repositories to load.</p>}
        </div>
      </main>
      <footer>Made with ❤️ by mikuvey</footer>
    </div>
  );  
};

export default App;
