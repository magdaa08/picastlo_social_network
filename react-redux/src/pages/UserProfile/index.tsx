import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState, AppDispatch } from "../../store";
import { fetchUsers } from "../../store/Users";

const UserProfile = () => {
    const { userId } = useParams<{ userId: string }>(); // Get userId from URL params
    const dispatch = useDispatch<AppDispatch>();
    const { users, usersLoading } = useSelector((state: GlobalState) => state.users);
  
    const [user, setUser] = useState<any | null>(null);
  
    useEffect(() => {
        if (users.length === 0 && !usersLoading) {
          // If users are not loaded yet, fetch them
          dispatch(fetchUsers());
        }
      }, [dispatch, users, usersLoading]);
    
      useEffect(() => {
        if (!usersLoading && users.length > 0) {
          // Find the user with the specific userId
          const selectedUser = users.find((user: any) => user.id === Number(userId));
          if (selectedUser) {
            setUser(selectedUser);
          }
        }
      }, [userId, users, usersLoading]);
    
      if (usersLoading) {
        return <p className="text-center text-gray-500">Loading users...</p>;
      }
    
      if (!user) {
        return <p className="text-center text-gray-500">User profile not found</p>;
      }

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      <div className="bg-white shadow-md rounded-lg p-4 space-y-2 w-2/5 mx-auto">
        <div className="flex items-center space-x-4">
          <img
            src={user.profilePicture || "https://api.dicebear.com/9.x/big-ears/svg?seed=" + user.id}
            alt="User Profile"
            className="w-20 h-20 rounded-full shadow-md"
          />
          <h2 className="text-2xl font-semibold text-gray-800">{user.username}</h2>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;
