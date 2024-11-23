import React from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const TimeSeriesGraph = ({ data }) => {
  return (
    <div style={{ width: "100%", height: 400 }}>
      <ResponsiveContainer>
        <LineChart
          data={data}
          margin={{
            top: 20,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="time"
            tickFormatter={(timestamp) =>
              new Date(timestamp).toLocaleDateString()
            } // Format timestamp as readable date
          />
          <YAxis />
          <Tooltip
            labelFormatter={(timestamp) =>
              `Date: ${new Date(timestamp).toLocaleDateString()}`
            }
          />
          <Line
            type="monotone"
            dataKey="time"
            stroke="#8884d8"
            activeDot={{ r: 8 }}
            name="Created At"
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default TimeSeriesGraph;
