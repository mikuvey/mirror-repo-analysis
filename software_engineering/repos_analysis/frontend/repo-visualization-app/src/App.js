import React, { useState, useEffect } from 'react';
import Filters from './components/Filters';
import RepositoryList from './components/RepositoryList';
import { fetchRepositories } from './services/api';

const App = () => {
  const [repositories, setRepositories] = useState([]);
  const [search, setSearch] = useState('');
  const [archived, setArchived] = useState('');
  const [sortBy, setSortBy] = useState('stars_high_to_low');
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10);
  const [hasMore, setHasMore] = useState(true);

  const loadRepositories = async () => {
    try {
      const data = await fetchRepositories({ search, archived, sortBy, page, pageSize });
      if (data.length < pageSize) setHasMore(false);
      setRepositories((prev) => [...prev, ...data]);
    } catch (error) {
      console.error('Error fetching repositories:', error);
    }
  };

  useEffect(() => {
    setRepositories([]);
    setPage(0);
    setHasMore(true);
    loadRepositories();
  }, [search, archived, sortBy]);

  const handleLoadMore = () => {
    setPage((prev) => prev + 1);
  };

  useEffect(() => {
    if (page > 0) loadRepositories();
  }, [page]);

  return (
    <div>
      <h1>Repository Browser</h1>
      <Filters
        search={search}
        setSearch={setSearch}
        archived={archived}
        setArchived={setArchived}
        sortBy={sortBy}
        setSortBy={setSortBy}
      />
      <RepositoryList
        repositories={repositories}
        onLoadMore={handleLoadMore}
        hasMore={hasMore}
      />
    </div>
  );
};

export default App;
