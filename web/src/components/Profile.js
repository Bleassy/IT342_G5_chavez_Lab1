import React from 'react';

function Profile({ user }) {
  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="profile-container">
      <h2 style={{ marginBottom: '1.5rem', color: '#2c3e50', textAlign: 'center' }}>👤 Your Profile</h2>

      <div className="user-info">
        <div className="info-row">
          <span className="info-label">User ID:</span>
          <span className="info-value">{user?.id}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Username:</span>
          <span className="info-value">{user?.username}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Email:</span>
          <span className="info-value">{user?.email}</span>
        </div>

        <div className="info-row">
          <span className="info-label">First Name:</span>
          <span className="info-value">{user?.firstName || 'Not provided'}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Last Name:</span>
          <span className="info-value">{user?.lastName || 'Not provided'}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Phone Number:</span>
          <span className="info-value">{user?.phoneNumber || 'Not provided'}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Member Since:</span>
          <span className="info-value">{formatDate(user?.createdAt)}</span>
        </div>

        <div className="info-row">
          <span className="info-label">Last Updated:</span>
          <span className="info-value">{formatDate(user?.updatedAt)}</span>
        </div>
      </div>

      <div style={{ marginTop: '2rem', padding: '1.5rem', backgroundColor: '#ecf0f1', borderRadius: '4px', textAlign: 'center' }}>
        <p style={{ marginBottom: '1rem', color: '#2c3e50', fontSize: '1rem' }}>
          ✅ You are successfully logged in!
        </p>
        <p style={{ marginBottom: '0', color: '#7f8c8d', fontSize: '0.9rem' }}>
          This is your profile information.
        </p>
      </div>
    </div>
  );
}

export default Profile;
