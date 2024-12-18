import axios from "axios";

const url = process.env.REACT_APP_API_URL;
const BASE_URL = url+"fetch-repos/filter";
const REFRESH_URL = url+"load-github-repos";

export const fetchRepositories = async (params) => {
  try {
    const response = await axios.get(BASE_URL, { params });
    return response.data;
  } catch (error) {
    console.error("Error fetching repositories:", error);
    throw error;
  }
};

export const refreshRepositories = async () => {
  try {
    const response = await axios.post(REFRESH_URL); // POST request to refresh endpoint
    return response.data;
  } catch (error) {
    console.error("Error refreshing repositories:", error);
    throw error;
  }
};
