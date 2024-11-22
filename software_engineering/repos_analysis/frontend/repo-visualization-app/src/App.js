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

    console.log("Fetching repositories with params:", params);

    try {
      const data = await fetchRepositories(params);
      console.log(`Fetched ${data.length} repositories for page ${page}`);

      if (data.length < pageSize) {
        setHasMore(false);
        console.log("No more repositories to fetch.");
      }

      // Append new data while avoiding duplicates
      setRepos((prevRepos) => {
        const newRepos = data.filter(
          (repo) => !prevRepos.some((existing) => existing.id === repo.id)
        );
        console.log("New repositories to append:", newRepos);
        return [...prevRepos, ...newRepos];
      });
    } catch (error) {
      console.error("Error fetching repositories:", error);
    } finally {
      setLoading(false);
    }
  };

  // Handle page changes (Load More functionality)
  useEffect(() => {
    if (page > 0) {
      console.log("Page changed:", page);
      fetchRepos();
    }
  }, [page]);

  // Handle changes to search, sorting, or filters
  useEffect(() => {
    console.log("Filters changed. Resetting repositories...");
    console.log({ searchTerm, sortField, order, archived });
    setRepos([]); // Clear existing repositories
    setPage(0);   // Reset to the first page
    setHasMore(true); // Reset the `hasMore` flag
    fetchRepos();
  }, [searchTerm, sortField, order, archived]);

  // Debugging Repos State
  useEffect(() => {
    console.log("Repositories updated:", repos);
  }, [repos]);

  return (
    <div>
      <h1>Fetch Repositories</h1>
      <Search onSearch={setSearchTerm} />
      <SortFilter
        onSort={setSortField}
        onOrderChange={setOrder}
        onArchivedChange={setArchived}
      />
      <RepoList repos={repos} />
      <Pagination hasMore={hasMore} loading={loading} setPage={setPage} />
    </div>
  );
};

export default App;
