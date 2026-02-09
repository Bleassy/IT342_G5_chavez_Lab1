import React from 'react';

function Dashboard() {
  return (
    <div className="dashboard">
      <h2 style={{ marginBottom: '1.5rem', color: '#2c3e50', textAlign: 'center' }}>📊 Dashboard</h2>
      
      <div style={{ 
        minHeight: '400px', 
        padding: '2rem', 
        backgroundColor: '#f8f9fa', 
        borderRadius: '8px', 
        display: 'flex', 
        alignItems: 'center', 
        justifyContent: 'center',
        color: '#7f8c8d',
        fontSize: '1.1rem'
      }}>
        <p>Welcome! Navigate to Profile to view your details.</p>
      </div>
    </div>
  );
}

export default Dashboard;
