// import React, { useState, useEffect } from "react";
// import Chart from "./components/BarChart";
// import MetricSelector from "./components/MetricSelector";
// import Search from "./components/Search";
// import SortFilter from "./components/SortFilter";
// import RepoList from "./components/RepoList";
// import { fetchRepositories } from "./services/api";
// import "./styles/App.css";

// const App = () => {
//   const [repos, setRepos] = useState([]);
//   const [searchTerm, setSearchTerm] = useState("");
//   const [sortField, setSortField] = useState("stars");
//   const [order, setOrder] = useState("desc");
//   const [archived, setArchived] = useState("");
//   const [page, setPage] = useState(0);
//   const [pageSize, setPageSize] = useState(6);
//   const [hasMore, setHasMore] = useState(true);
//   const [loading, setLoading] = useState(false);
//   const [selectedMetrics, setSelectedMetrics] = useState(["stars", "forks", "issues"]);

//   // Sample chart data
//   const chartData = repos.map((repo) => ({
//     name: repo.name,
//     stars: repo.stargazers_count,
//     forks: repo.forks_count,
//     issues: repo.open_issues_count,
//   }));

//   const fetchRepos = async () => {
//     setLoading(true);

//     const params = {
//       search: searchTerm || undefined,
//       sortBy: sortField,
//       order,
//       archived: archived || undefined,
//       page,
//       pageSize,
//     };

//     try {
//       const data = await fetchRepositories(params);
//       if (data.length < pageSize) {
//         setHasMore(false);
//       }

//       setRepos((prevRepos) => {
//         const newRepos = data.filter(
//           (repo) => !prevRepos.some((existing) => existing.id === repo.id)
//         );
//         return [...prevRepos, ...newRepos];
//       });
//     } catch (error) {
//       console.error("Error fetching repositories:", error);
//     } finally {
//       setLoading(false);
//     }
//   };

//   useEffect(() => {
//     if (page > 0) {
//       fetchRepos();
//     }
//   }, [page]);

//   useEffect(() => {
//     setRepos([]);
//     setPage(0);
//     setHasMore(true);
//     fetchRepos();
//   }, [searchTerm, sortField, order, archived]);

//   return (
//     <div className="app-container">
//       <header>Brown CCV Repo Analysis</header>

//       {/* Chart Section */}
//       <MetricSelector
//         selectedMetrics={selectedMetrics}
//         onChange={setSelectedMetrics}
//       />
//       <Chart data={chartData} selectedMetrics={selectedMetrics} />

//       {/* Search and filters*/}
//       <main className="content-container">
//         <div className="filters">
//         <Search onSearch={setSearchTerm} />
//           <SortFilter
//             onSort={setSortField}
//             onOrderChange={setOrder}
//             onArchivedChange={setArchived}
//           />
//         </div>
//         <RepoList repos={repos} />
//         <div style={{ textAlign: "center", padding: "20px" }}>
//           {loading && <p>Loading...</p>}
//           {!loading && hasMore && (
//             <button onClick={() => setPage((prevPage) => prevPage + 1)}>Load More</button>
//           )}
//           {!hasMore && <p>No more repositories to load.</p>}
//         </div>
//       </main>
//       <footer>Made with ❤️ by mikuvey</footer>
//     </div>
//   );
// };

// export default App;

import React, { useState, useEffect } from "react";
import Chart from "./components/BarChart";
import MetricSelector from "./components/MetricSelector";
import Search from "./components/Search";
import SortFilter from "./components/SortFilter";
import RepoList from "./components/RepoList";
import { fetchRepositories } from "./services/api";
import "./styles/App.css";

const App = () => {
  const [repos, setRepos] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortField, setSortField] = useState("stars");
  const [order, setOrder] = useState("desc");
  const [archived, setArchived] = useState("");
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(6); // Now dynamic
  const [customPageSize, setCustomPageSize] = useState(6); // Input box value
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);

  const [selectedMetrics, setSelectedMetrics] = useState(["stars", "forks", "issues"]);

  const chartData = repos.map((repo) => ({
    name: repo.name,
    stars: repo.stargazers_count,
    forks: repo.forks_count,
    issues: repo.open_issues_count,
  }));

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
  }, [searchTerm, sortField, order, archived, pageSize]);

  const handlePageSizeChange = () => {
    if (customPageSize > 0) {
      setPageSize(customPageSize);
      setRepos([]); // Clear existing repositories
      setPage(0); // Reset pagination
      setHasMore(true); // Reset loading state
    }
  };

  return (
    <div className="app-container">
      <header>Brown CCV Repo Analysis</header>
      <MetricSelector
        selectedMetrics={selectedMetrics}
        onChange={setSelectedMetrics}
      />
      <Chart data={chartData} selectedMetrics={selectedMetrics} />
      
      <main className="content-container">
        <div className="filters">
        <Search onSearch={setSearchTerm} />
          <SortFilter
            onSort={setSortField}
            onOrderChange={setOrder}
            onArchivedChange={setArchived}
          />

          {/* Add input and load button for custom page size */}
          <div className="page-size-container">
            <input
              type="number"
              min="1"
              placeholder="#repos"
              value={customPageSize}
              onChange={(e) => setCustomPageSize(Number(e.target.value))}
            />
            <button onClick={handlePageSizeChange}>Load</button>
          </div>
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
