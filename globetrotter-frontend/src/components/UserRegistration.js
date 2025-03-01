// src/components/UserRegistration.js
import React, { useState } from 'react';
import { registerUser, getUserScore, generateInvite } from '../api';

function UserRegistration() {
  const [username, setUsername] = useState('');
  const [score, setScore] = useState(null);
  const [inviteLink, setInviteLink] = useState('');

  const handleRegister = async () => {
    try {
      await registerUser(username);
      const response = await getUserScore(username);
      setScore(response.data);
    } catch (error) {
      console.error('Registration error', error);
    }
  };

  const handleGenerateInvite = async () => {
    try {
      const response = await generateInvite(username);
      setInviteLink(response.data.whatsappLink);
    } catch (error) {
      console.error('Invite generation error', error);
    }
  };

  return (
    <div className="card mb-4">
      <div className="card-body">
        <h3 className="card-title">User Registration</h3>
        <div className="form-group">
          <input
            type="text"
            className="form-control mb-2"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <button className="btn btn-primary mb-2" onClick={handleRegister}>
            Register
          </button>
        </div>
        {score && (
          <div>
            <h5>User Score:</h5>
            <p>Correct: {score.correct}</p>
            <p>Incorrect: {score.incorrect}</p>
          </div>
        )}
        <button className="btn btn-success" onClick={handleGenerateInvite}>
          Generate Invite Link
        </button>
        {inviteLink && (
          <div className="mt-2">
            <h5>WhatsApp Invite Link:</h5>
            <a href={inviteLink} target="_blank" rel="noopener noreferrer">
              Share via WhatsApp
            </a>
          </div>
        )}
      </div>
    </div>
  );
}

export default UserRegistration;
