import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState, AppDispatch } from "../../store";
import { fetchPublicFeed, fetchPostsByUsername } from "../../store/Posts";
import { Link } from "react-router-dom";

export const Homepage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { posts, postsLoading } = useSelector((state: GlobalState) => state.posts);
  const { users, usersLoading, isAuthenticated, currentUser } = useSelector(
    (state: GlobalState) => state.users
  );

  useEffect(() => {
    if (isAuthenticated && currentUser?.username) {
      // Fetch user-specific posts
      dispatch(fetchPostsByUsername(currentUser.username));
    } else {
      // Fetch public feed
      dispatch(fetchPublicFeed());
    }
  }, [dispatch, isAuthenticated, currentUser]);

  const getUsername = (userId: string) => {
    const user = users.find((user: any) => user.id === userId);
    return user ? user.username : "Anonymous User";
  };

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      <div className="flex justify-between items-center mb-6 mx-5">

        {/* Left side - List Of Users button */}
        <div className="flex-shrink-0">
          <Link to="/users">
            <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
              List Of Users
            </button>
          </Link>
        </div>

        {/* Center - Welcome Title */}
        <div className="flex-grow text-center">
          <h1 className="text-4xl font-extrabold text-blue-600">
            Welcome to Picastlo Social Network
          </h1>
        </div>

        {/* Right side - User Profile and Logout/Login */}
        <div className="flex items-center space-x-4 flex-shrink-0">
          {isAuthenticated ? (
            <>
              <span className="text-gray-700 font-semibold">
                {currentUser?.username || "User"}
              </span>
              <button
                className="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700"
                onClick={() => {
                  // Add logout functionality here
                  console.log("Logged out");
                }}
              >
                Logout
              </button>
            </>
          ) : (
            <Link to="/login">
              <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                Login
              </button>
            </Link>
          )}
        </div>
      </div>

      {postsLoading && <p className="text-center text-gray-500">Loading posts...</p>}

      {!postsLoading && posts.length === 0 && (
        <p className="text-center text-gray-500">No posts available.</p>
      )}

      {!postsLoading && posts.length > 0 && (
        <div className="space-y-4 flex flex-col items-center">
          {posts.map((post, index) => (
            <div
              key={post.id || index}
              className="bg-white shadow-md rounded-lg p-4 space-y-2 w-2/5"
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
                <h2 className="text-lg font-semibold text-gray-800">
                  {getUsername(post.userId)}
                </h2>
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
              <div className="flex justify-between text-gray-500 text-sm pt-2">
                <p>{post.likes || 0} Likes</p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Homepage;
