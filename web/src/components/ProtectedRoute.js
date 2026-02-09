import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ProtectedRoute({ user, children }) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate('/login', { replace: true });
    }
  }, [user, navigate]);

  if (!user) {
    return (
      <div className="error-page">
        <h2>Access Denied</h2>
        <p>You need to be logged in to access this page.</p>
        <p>Redirecting to login...</p>
      </div>
    );
  }

  return children;
}

export default ProtectedRoute;
