import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { login } from "../../store/Users";
import { GlobalState, AppDispatch } from "../../store";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { isAuthenticated, usersLoading, error } = useSelector(
    (state: GlobalState) => state.users
  );

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(login(username, password));
  };

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/");
    }
  }, [isAuthenticated, navigate]);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Login</h2>

        {error && <div className="mb-4 text-red-500 text-center">{error}</div>}

        <form onSubmit={handleLogin}>
          <div className="mb-4">
            <label htmlFor="email" className="block text-gray-700 font-medium mb-2">
              Username
            </label>
            <input
              id="email"
              placeholder="Enter your username"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="mb-6">
            <label htmlFor="password" className="block text-gray-700 font-medium mb-2">
              Password
            </label>
            <input
              type="password"
              id="password"
              placeholder="Enter your password"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className={`w-full py-2 rounded-lg transition duration-300 ${
              usersLoading
                ? "bg-blue-300 cursor-not-allowed"
                : "bg-blue-500 hover:bg-blue-600 text-white"
            }`}
            disabled={usersLoading}
          >
            {usersLoading ? "Logging in..." : "Login"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;

