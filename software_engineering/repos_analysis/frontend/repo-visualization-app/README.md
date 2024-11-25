# Repo-Visualization

Repo-Visualization is the frontend application for Brown CCV's repository analysis platform. It provides an intuitive interface for visualizing GitHub repository data.


## Table of Contents
- [Repo-Visualization](#repo-visualization)
- [Requirements](#requirements)
- [Setup](#setup)
- [Installation, Run, and Deploy](#installation-run-and-deploy)
- [Credits](#credits)
- [Troubleshooting Tips](#troubleshooting-tips)
- [License](#license)


## Requirements
For building and running the application you need:
1. [NodeJS v22.11.0](https://nodejs.org/en/download/source-code)

## Setup
The application requires a `.env` file to configure environment variables for connecting to the backend.

Example `.env` file:
```properties
REACT_APP_API_URL="http://localhost:8080/"
```


## Installation, Run, and Deploy

From the project root directory:

- **Install dependencies**:
  ```bash
  npm install
  ```
- **Run the application in development mode**:
  ```bash
  npm start
  ```
  - Open [http://localhost:3000](http://localhost:3000) to view it in your browser.
- **Build the application for production**:
  ```bash
  npm run build
  ```
  - This command bundles the app into the `build` folder, optimized for production.
  - For detailed deployment steps, refer to the [React deployment documentation](https://facebook.github.io/create-react-app/docs/deployment).


## Credits
- [React](https://reactjs.org/) for the frontend framework.
- [Node.js](https://nodejs.org/) for the runtime environment.
- [Create React App](https://create-react-app.dev/) for bootstrapping the project.
- [recharts](https://recharts.org/en-US/)
- [BootStrap](https://getbootstrap.com/)


## Troubleshooting Tips

- **`npm start` fails**:
  - Ensure NodeJS v22.11.0 or later is installed (`node -v` to check version).
  - Run `npm install` to ensure all dependencies are installed.
- **Environment variable issues**:
  - Verify that the `.env` file exists and contains the correct `REACT_APP_API_URL`.
- **Port conflicts**:
  - If `3000` is already in use, change the port by adding a `.env` variable:
    ```properties
    PORT=3001
    ```

## License

[MIT](https://choosealicense.com/licenses/mit/)