import React from "react";

const Pagination = ({ hasMore, loading, setPage }) => {
  return (
    <div style={{ margin: "20px 0", textAlign: "center" }}>
      {loading && <p>Loading...</p>}
      {!loading && hasMore && (
        <button onClick={() => setPage((prevPage) => prevPage + 1)}>
          Load More
        </button>
      )}
      {!hasMore && <p>No more repositories to load.</p>}
    </div>
  );
};

export default Pagination;
