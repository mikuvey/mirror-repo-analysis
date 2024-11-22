import React from "react";

const SortFilter = ({ onSort, onOrderChange, onArchivedChange}) => {
    const handleSortChange = (event) =>{
        onSort(event.target.value);
    };

    const handleOrderChange = (event) => {
        onOrderChange(event.target.value);
    };

    const handleArchivedChange = (event) => {
        onArchivedChange(event.target.value);
    };

    return (
        <div style = {{ margin: "20px 0"}}>
            <label>
                Sort by:
                <select onChange={handleSortChange} style={{margin: "0 10px"}}>
                    <option value = "stars"> Stars</option>
                    <option value ="forks">Forks</option>
                    <option value= "date">Date</option>
                </select>
            </label>
            <label>
                Order:
                <select onChange={handleOrderChange} style={{margin: "0 10px"}}>
                    <option value = "asc">Ascending</option>
                    <option value ="desc">Descending</option>
                </select>
            </label>
            <label>
                Archived:
                <select onChange={handleArchivedChange} style={{margin: "0 10px"}}>
                    <option value = "">All</option>
                    <option value ="true">Archived</option>
                    <option value= "false">Active</option>
                </select>
            </label>
        </div>
    );

};

export default SortFilter;