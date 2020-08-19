import React from 'react';
import './App.scss';

import Routes from './routes';
import { AuthProvider } from './services/authProvider';

function App() {
  return (
    <AuthProvider>
      <Routes />
    </AuthProvider>
  );
}

export default App;
