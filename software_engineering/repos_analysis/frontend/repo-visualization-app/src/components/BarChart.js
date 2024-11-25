import React from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from "recharts";

const Chart = ({ data, selectedMetrics }) => {
  //Animation delay configuration
  const animationConfig = {
    animationDuration: 1000, //in ms
    animationBegin: 300,
  };

  return (
    <div style={{ width: "100%", height: 400 }}>
      <ResponsiveContainer>
        <BarChart
          data={data}
          margin={{
            top: 20,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend />
            {/*Render bars */}
            {selectedMetrics.includes("stars") && (
              <Bar dataKey="stars" stackId="a" fill="#8884d8" name="Stars" {...animationConfig}/>
            )}
            {selectedMetrics.includes("forks") && (
              <Bar dataKey="forks" stackId="a" fill="#82ca9d" name="Forks" {...animationConfig}/>
            )}
            {selectedMetrics.includes("issues") && (
              <Bar dataKey="issues" stackId="a" fill="#ffc658" name="Open Issues" {...animationConfig}/>
            )}
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};



export default Chart;
