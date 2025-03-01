// src/api.js
import axios from 'axios';

// Base URL for the backend
const API_BASE = 'http://localhost:8080';

// Function to register a user
export const registerUser = (username) =>
  axios.post(`${API_BASE}/api/user/register`, null, { params: { username } });

// Function to get user score
export const getUserScore = (username) =>
  axios.get(`${API_BASE}/api/user/${username}/score`);

// Function to generate an invitation
export const generateInvite = (username) =>
  axios.get(`${API_BASE}/api/user/invite`, { params: { username } });

// Function to start a game session
export const startGame = () =>
  axios.get(`${API_BASE}/game/start`);

// Function to submit an answer
export const submitAnswer = (sessionId, answer) =>
  axios.post(`${API_BASE}/game/submit`, null, { params: { sessionId, answer } });
