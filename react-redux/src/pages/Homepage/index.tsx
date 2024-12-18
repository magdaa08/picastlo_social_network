import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState } from "../../store";
import { actionDecrement, actionIncrement } from "../../store/Counter";
import { fetchPublicFeed } from "../../store/Posts";
import axios from 'axios';
import { Link } from 'react-router-dom';

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

  useEffect(() => {
    const fetchData = async () => {
      try {
        const postsResponse = await axios.get('/posts/public_feed');
        setPosts(postsResponse.data);

        const usersResponse = await axios.get('/users');
        setUsers(usersResponse.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []); // Empty dependency array ensures this runs only once when the component mounts

   

   return (
    <div className="min-h-screen bg-gray-100 p-4">
      <div className="flex justify-between items-center mb-6 mx-5">
        <div className="ml-auto">
        <h1 className="text-4xl font-extrabold text-blue-600 pl-20">
            Welcome to Picastlo Social Network
          </h1>
        </div>
 
        <Link to="/login" className=" ml-auto">
          <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            Login
          </button>
        </Link>
      </div>
      {loading && <p className="text-center text-gray-500">Loading posts...</p>}

      {!loading && posts.length === 0 && (
        <p className="text-center text-gray-500">No posts available.</p>
      )}

      {!loading && posts.length > 0 && (
        <div className="space-y-4 flex flex-col items-center">
          {posts.map((post, index) => (
            <div
              key={post.id || index}
              className="bg-white shadow-md rounded-lg p-4 space-y-2 w-2/5"
            >
              <div className="flex items-center space-x-4">
                <img
                  src={post.userProfilePicture || "https://api.dicebear.com/9.x/big-ears/svg?seed="+post.userId}
                  alt="User Profile"
                  className="w-10 h-10 rounded-full shadow-md " 
                />
                <h2 className="text-lg font-semibold text-gray-800">
                  {(users[post.userId]).username || "Anonymous User"}
                </h2>
              </div>
                   <p className="text-gray-700">{post.text}</p>
                   <div
                   className="flex justify-center">
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
