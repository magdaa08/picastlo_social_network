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
    const { posts, postsLoading } = useSelector((state: GlobalState) => state.posts);
  

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
        if (!usersLoading && users.length > 0 && profiles && posts) {
          // Find the user and profile with the corresponding userId
          const selectedUser = users.find((user: any) => user.id === Number(userId));
          const selectedProfile = profiles.find((profile: any) => profile.userId === Number(userId));
          const selectedPosts = posts.filter((post: any) => post.userId === Number(userId)) || [];
    
          if (selectedUser && selectedProfile) {
            setUserProfile({
              username: selectedUser.username,
              avatar: selectedProfile.avatar,
              bio: selectedProfile.bio,
              user_posts: selectedPosts,
            });
          }
        }
      }, [userId, users, usersLoading, profiles, posts]);
    
      if (usersLoading || profilesLoading || postsLoading) {
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
    
          {/* User's Posts Section */}
          <div className="mt-6 bg-white shadow-md rounded-lg p-6 space-y-4 w-full max-w-3xl mx-auto">
            <h3 className="text-xl font-semibold text-gray-800">Posts</h3>
            {postsLoading && <p className="text-center text-gray-500">Loading posts...</p>}
            
            {!postsLoading && userProfile.user_posts.length === 0 && (
              <p className="text-center text-gray-500">I currently don't have or don't want to show any of my posts. Sorry...</p>
            )}
      
            {!postsLoading && userProfile.user_posts.length > 0 && (
              <div className="space-y-4 flex flex-col items-center">
                {userProfile.user_posts.map((post: any) => (
                  <div
                  key = {post.id}
                    className="bg-white shadow-md rounded-lg p-4 space-y-2 w-full"
                  >
                    <div className="flex items-center space-x-4">
                      <img
                        src={
                          post.userProfilePicture ||
                          "https://api.dicebear.com/9.x/big-ears/svg?seed=" +
                            post.userId
                        }
                        alt="User Profile"
                        className="w-10 h-10 rounded-full shadow-md"
                      />
                    </div>
                    <p className="text-gray-700">{post.text}</p>
                    <div className="flex justify-center">
                      {post.image && (
                        <img
                          src={post.image}
                          alt="Post Image"
                          className="h-4/6 rounded-lg mt-2"
                        />
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
          </div>);
      };
      
export default UserProfile;