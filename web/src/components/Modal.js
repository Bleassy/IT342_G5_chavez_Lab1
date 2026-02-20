import React from 'react';
import './Modal.css';

const Modal = ({ isOpen, title, message, onConfirm, onCancel }) => {
  if (!isOpen) return null;
  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>{title}</h2>
        <p>{message}</p>
        <div className="modal-actions">
          <button className="modal-btn confirm" onClick={onConfirm}>Yes</button>
          <button className="modal-btn cancel" onClick={onCancel}>No</button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
