import React from "react";
import "./App.css";
import Homepage from "./pages/Homepage";
import Login from "./pages/Login";
import UsersList from "./pages/UsersList";
import UserProfile from "./pages/UserProfile";
import { Provider } from "react-redux";
import { store } from "./store";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/users" element={<UsersList />} />
        {/* Dynamic route for user profiles */}
        <Route path="/users/:userId" element={<UserProfile />} />
      </Routes>
    </Router>
  );
}

const RdxApp = () => (
  <Provider store={store}>
    <App />
  </Provider>
);

export default RdxApp;
