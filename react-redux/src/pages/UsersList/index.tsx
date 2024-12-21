import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState, AppDispatch } from "../../store";
import { fetchUsers } from "../../store/Users";
import { Link } from "react-router-dom";

const UsersList = () => {

  const dispatch = useDispatch<AppDispatch>();
  const { users, usersLoading } = useSelector((state: GlobalState) => state.users);

  const [searchTerm, setSearchTerm] = useState<string>("");
  const [filteredUsers, setFilteredUsers] = useState<any[]>([]);

  useEffect(() => {
    dispatch(fetchUsers());
  }, [dispatch]);

  useEffect(() => {
    // Filter users based on the search term
    setFilteredUsers(
      users.filter((user: any) =>
        user.username.toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }, [searchTerm, users]);

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      {/* Search Bar */}
      <div className="flex justify-center mb-4">
        <input
          type="text"
          placeholder="Search users by username"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="p-2 border rounded-md w-1/2"
        />
      </div>

      {/* Users List */}
      <div className="space-y-4 flex flex-col items-center">
        {usersLoading ? (
          <p className="text-center text-gray-500">Loading users...</p>
        ) : filteredUsers.length === 0 ? (
          <p className="text-center text-gray-500">No users found.</p>
        ) : (
          filteredUsers.map((user: any) => (
            <div key={user.id} className="bg-white shadow-md rounded-lg p-4 space-y-2 w-2/5">
              <div className="flex items-center space-x-4">
                <img
                  src={user.profilePicture || "https://api.dicebear.com/9.x/big-ears/svg?seed=" + user.id}
                  alt="User Profile"
                  className="w-10 h-10 rounded-full shadow-md"
                />
                <h2 className="text-lg font-semibold text-gray-800">{user.username}</h2>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default UsersList;
