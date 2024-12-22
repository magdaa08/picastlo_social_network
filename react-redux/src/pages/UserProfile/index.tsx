import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState, AppDispatch } from "../../store";
import { fetchUsers } from "../../store/Users";
import { fetchProfiles } from "../../store/Profiles";

const UserProfile = () => {

    const { profiles, profilesLoading } = useSelector((state: GlobalState) => state.profiles);
    const [userProfile, setUserProfile] = useState<any | null>(null);

    const { userId } = useParams<{ userId: string }>(); // Get userId from URL params
    const dispatch = useDispatch<AppDispatch>();
    const { users, usersLoading } = useSelector((state: GlobalState) => state.users);
  
    const [user, setUser] = useState<any | null>(null);

    useEffect(() => {
      dispatch(fetchProfiles());
    }, [dispatch]);
  
    useEffect(() => {
        if (users.length === 0 && !usersLoading) {
          // If users are not loaded yet, fetch them
          dispatch(fetchUsers());
        }
      }, [dispatch, users, usersLoading]);
    
      useEffect(() => {
        if (!usersLoading && users.length > 0 && profiles) {
          // Find the user and profile with the corresponding userId
          const selectedUser = users.find((user: any) => user.id === Number(userId));
          const selectedProfile = profiles.find((profile: any) => profile.userId === Number(userId));
    
          if (selectedUser && selectedProfile) {
            setUserProfile({
              username: selectedUser.username,
              avatar: selectedProfile.avatar,
              bio: selectedProfile.bio,
            });
          }
        }
      }, [userId, users, usersLoading, profiles]);
    
      if (usersLoading || profilesLoading) {
        return <p className="text-center text-gray-500">Loading user profile...</p>;
      }
    
      if (!userProfile) {
        return <p className="text-center text-gray-500">User profile not found</p>;
      }
    
      return (
        <div className="min-h-screen bg-gray-100 p-4">
          <div className="bg-white shadow-md rounded-lg p-4 space-y-2 w-2/5 mx-auto">
            <div className="flex items-center space-x-4">
              <img
                src={userProfile.avatar || "https://api.dicebear.com/9.x/big-ears/svg?seed=" + userId}
                alt="User Avatar"
                className="w-20 h-20 rounded-full shadow-md"
              />
              <h2 className="text-2xl font-semibold text-gray-800">{userProfile.username}</h2>
            </div>
            <p className="text-gray-600 mt-2">{userProfile.bio || "No bio available."}</p>
          </div>
        </div>
      );
    };
    
    export default UserProfile;