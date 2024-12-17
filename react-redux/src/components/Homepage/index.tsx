import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState } from "../../store";
import { actionDecrement, actionIncrement } from "../../store/Counter";
import { fetchPublicFeed } from "../../store/Posts";
import axios from 'axios';

export const Homepage = () => {
  let [loading, setLoading]=useState(true);
  let [posts, setPosts]=useState([] as any[]);
  let [users, setUsers]=useState([] as any[]);
  const counter = useSelector((state: GlobalState) => state.counter);
  //const {posts, loading} = useSelector((state:GlobalState) => state.posts)
  const dispatch = useDispatch();

  /*useEffect(() => {
    (dispatch as any)(fetchPublicFeed());
  }, [dispatch]);
  */

  axios.get('/posts/public_feed').then((response)=>{
    setPosts(response.data);
    setLoading(false);
  });

  axios.get('/users').then((response)=>{
    setUsers(response.data);
  });
   

   return (
    <div className="min-h-screen bg-gray-100 p-4">
      <h1 className="text-4xl font-extrabold text-center text-blue-600 mb-6">
        Welcome to Picastlo Social Network
      </h1>

      {loading && <p className="text-center text-gray-500">Loading posts...</p>}

      {!loading && posts.length === 0 && (
        <p className="text-center text-gray-500">No posts available.</p>
      )}

      {!loading && posts.length > 0 && (
        <div className="space-y-4">
          {posts.map((post, index) => (
            <div
              key={post.id || index}
              className="bg-white shadow-md rounded-lg p-4 space-y-2"
            >
              <div className="flex items-center space-x-4">
                <img
                  src={post.userProfilePicture || "https://api.dicebear.com/9.x/big-ears/svg?seed="+post.userId}
                  alt="User Profile"
                  className="w-10 h-10 rounded-full"
                />
                <h2 className="text-lg font-semibold text-gray-800">
                  {users[post.userId].username || "Anonymous User"}
                </h2>
              </div>
              <p className="text-gray-700">{post.text}</p>
              <div className="flex justify-between text-gray-500 text-sm pt-2">
                <p>{post.likes || 0} Likes</p>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="mt-8">
        <p className="text-center text-gray-700 text-lg mb-4">
          Counter: <span className="font-bold">{counter}</span>
        </p>
        <div className="flex justify-center space-x-4">
          <button
            onClick={() => dispatch(actionIncrement())}
            className="px-4 py-2 bg-green-500 text-white rounded-lg shadow hover:bg-green-600"
          >
            +
          </button>
          <button
            onClick={() => dispatch(actionDecrement())}
            className="px-4 py-2 bg-red-500 text-white rounded-lg shadow hover:bg-red-600"
          >
            -
          </button>
        </div>
      </div>
    </div>
  );
};

export default Homepage;
