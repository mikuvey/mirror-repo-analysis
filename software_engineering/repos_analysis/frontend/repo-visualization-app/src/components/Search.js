import React from "react";

const Search = ({onSearch}) => {
    const handleSearch = (event) =>{
        onSearch(event.target.value);
    
    };

    return (
        <div>
            <input
                type = "text"
                placeholder="Search repositories"
                onChange={handleSearch}
                style={{padding:"10px", width: "300px", margin: "20px 0"}}
            />
        </div>
    );
};

export default Search;
