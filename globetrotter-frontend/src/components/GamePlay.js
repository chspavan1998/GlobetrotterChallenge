// src/components/GamePlay.js
import React, { useState } from 'react';
import { startGame, submitAnswer } from '../api';

function GamePlay() {
  const [session, setSession] = useState(null);
  const [answer, setAnswer] = useState('');
  const [result, setResult] = useState(null);

  const handleStartGame = async () => {
    try {
      const response = await startGame();
      setSession(response.data);
      setResult(null);
    } catch (error) {
      console.error('Error starting game', error);
    }
  };

  const handleSubmitAnswer = async () => {
    if (!session) return;
    try {
      const response = await submitAnswer(session.sessionId, answer);
      setResult(response.data);
    } catch (error) {
      console.error('Error submitting answer', error);
    }
  };

  return (
    <div className="card mb-4">
      <div className="card-body">
        <h3 className="card-title">Game Play</h3>
        <button className="btn btn-primary mb-2" onClick={handleStartGame}>
          Start Game
        </button>
        {session && (
          <div>
            <p><strong>Clues:</strong> {session.clues}</p>
            <p><strong>Time Limit:</strong> {session.timeLimitSeconds} seconds</p>
            <div className="form-group">
              <input
                type="text"
                className="form-control mb-2"
                placeholder="Your answer"
                value={answer}
                onChange={(e) => setAnswer(e.target.value)}
              />
              <button className="btn btn-success" onClick={handleSubmitAnswer}>
                Submit Answer
              </button>
            </div>
          </div>
        )}
        {result && (
          <div className="mt-2">
            <h5>Result:</h5>
            <p>{result.message}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default GamePlay;
