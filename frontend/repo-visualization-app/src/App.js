import React, { useState, useEffect } from "react";
import Chart from "./components/BarChart";
import MetricSelector from "./components/MetricSelector";
import Search from "./components/Search";
import SortFilter from "./components/SortFilter";
import RepoList from "./components/RepoList";
import { fetchRepositories, refreshRepositories } from "./services/api";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./styles/App.css";

const App = () => {
  const [repos, setRepos] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortField, setSortField] = useState("stars");
  const [order, setOrder] = useState("desc");
  const [archived, setArchived] = useState("");
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(6); //Defaults to 6 repos per page
  const [customPageSize, setCustomPageSize] = useState(6);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [refreshing, setRefreshing] = useState(false);

  const [selectedMetrics, setSelectedMetrics] = useState(["stars", "forks", "issues"]);

  //Map repos data with reCharts elements
  const chartData = repos.map((repo) => ({
    name: repo.name,
    stars: repo.stargazers_count,
    forks: repo.forks_count,
    issues: repo.open_issues_count,
  }));

  //Asynchronously fetchRepos from db based on query parameters
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

  //Handle DB sync with Github
  const handleRefresh = async () => {
    setRefreshing(true);
    try {
      await refreshRepositories();
      setRepos([]);
      setPage(0);
      setHasMore(true);
      fetchRepos();
    } catch (error) {
      console.error("Error refreshing repositories:", error);
    } finally {
      setRefreshing(false);
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
  }, [searchTerm, sortField, order, archived, pageSize]);

  const handlePageSizeChange = async () => {
    if (customPageSize > 0) {
      setRepos([]); //Clear existing repos
      setPageSize(customPageSize);
      setPage(0); //Reset pagination
      setHasMore(true);
  
      //Fetch repositories immediately after updating page size
      //Future work: cut duplicating code
      try {
        setLoading(true);
        const params = {
          search: searchTerm || undefined,
          sortBy: sortField,
          order,
          archived: archived || undefined,
          page: 0,
          pageSize: customPageSize,
        };
  
        const data = await fetchRepositories(params);
  
        setRepos(data);
        if (data.length < customPageSize) {
          setHasMore(false);
        }
      } catch (error) {
        console.error("Error fetching repositories:", error);
      } finally {
        setLoading(false);
      }
    }
  };
  

  return (
    <div className="app-container">
      <header className="header-container">
        Brown CCV Repo Analysis
        <button className="refresh-button" onClick={handleRefresh} disabled={refreshing}>
          <i className={`bi bi-arrow-repeat ${refreshing ? "refreshing" : ""}`}></i>
          {refreshing ? "Syncing..." : ""}
        </button>
      </header>

      <main className="content-container">
        <div className="filters">
          <div className="search-elements">
            <Search onSearch={setSearchTerm} />
          </div>
          <div className="filter">
            <SortFilter
              onSort={setSortField}
              onOrderChange={setOrder}
              onArchivedChange={setArchived}
            />
            {/* Repo loader */}
            <div className="page-size-container">
              <label className="form-label">#repos</label>
              <input
                  type="number"
                  value={customPageSize}
                  onChange={(e) => setCustomPageSize(Number(e.target.value))}
                  className="form-control"
                  style={{"marginBottom": "2px"}}
                />
                <button onClick={handlePageSizeChange} disabled={loading} style={{"marginLeft":"10px","borderRadius":"90px", "backgroundColor":"#07182c"}}>
                  {loading ? "Loading..." : "Load"}
                </button>
            </div>
          </div>
        </div>

        {/* Select metrics to show on charts */}
        <MetricSelector
          selectedMetrics={selectedMetrics}
          onChange={setSelectedMetrics}
        />
        <Chart data={chartData} selectedMetrics={selectedMetrics} />

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
