import logo from './logo.svg';
import './App.css';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

// export default App;

import React from "react";

function App() {
  return (
    <div className="App" style={{ width: "100vw", height: "100vh" }}>
      <iframe
        style={{
          background: "#F1F5F4",
          border: "none",
          borderRadius: "2px",
          boxShadow: "0 2px 10px 0 rgba(70, 76, 79, .2)",
          width: "100%",
          height: "100%",
        }}
        src="https://charts.mongodb.com/charts-project-0-woomymn/embed/dashboards?id=f748f615-b3cf-42da-961f-c248a56b360e&theme=light&autoRefresh=true&maxDataAge=3600&showTitleAndDesc=true&scalingWidth=fixed&scalingHeight=scale"
        title="MongoDB Chart"
      ></iframe>
    </div>
  );
}

export default App;
