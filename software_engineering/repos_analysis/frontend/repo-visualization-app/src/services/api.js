import axios from "axios";

const BASE_URL = "http://localhost:8080/fetch-repos/filter";

export const fetchRepositories = async (params) => {
  try {
    const response = await axios.get(BASE_URL, { params });
    return response.data;
  } catch (error) {
    console.error("Error fetching repositories:", error);
    throw error;
  }
};
