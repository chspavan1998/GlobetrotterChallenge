// src/App.js
import React from 'react';
import UserRegistration from './components/UserRegistration';
import GamePlay from './components/GamePlay';

function App() {
  return (
    <div className="container mt-4">
      {/* Navigation Bar */}
      <nav className="navbar navbar-expand-lg navbar-light bg-light mb-4">
        <a className="navbar-brand" href="/">Globetrotter Challenge</a>
      </nav>

      {/* Main Content */}
      <div className="row">
        <div className="col-md-6">
          <UserRegistration />
        </div>
        <div className="col-md-6">
          <GamePlay />
        </div>
      </div>
    </div>
  );
}

export default App;
