import React from "react";

const MetricSelector = ({ selectedMetrics, onChange }) => {
  const handleCheckboxChange = (e) => {
    const { name, checked } = e.target;
    if (checked) {
      onChange([...selectedMetrics, name]);
    } else {
      onChange(selectedMetrics.filter((metric) => metric !== name));
    }
  };

  return (
    <div className="metric-selector">
      <label>
        <input
          type="checkbox"
          name="stars"
          checked={selectedMetrics.includes("stars")}
          onChange={handleCheckboxChange}
        />
        Stars
      </label>
      <label>
        <input
          type="checkbox"
          name="forks"
          checked={selectedMetrics.includes("forks")}
          onChange={handleCheckboxChange}
        />
        Forks
      </label>
      <label>
        <input
          type="checkbox"
          name="issues"
          checked={selectedMetrics.includes("issues")}
          onChange={handleCheckboxChange}
        />
        Open Issues
      </label>
    </div>
  );
};

export default MetricSelector;
