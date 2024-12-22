import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { GlobalState, AppDispatch } from "../../store";
import { useNavigate } from "react-router-dom";
import {
  createPost,
  updatePostThunk,
  deletePostThunk,
  fetchPostsByUserId,
} from "../../store/Posts";
import { createPipeline, deletePipeline, fetchUserPipelines, updatePipelineThunk } from "../../store/Pipelines";
import { fetchProfiles } from "../../store/Profiles";
import { fetchUsers } from "../../store/Users";
import PostModal from "../../components/PostModal";
import PipelineModal from "../../components/PipelineModal";

type Visibility = "public" | "private" | "friends-only" | "groups-only";

const UserProfile = () => {
  const { userId } = useParams<{ userId: string }>();
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const { users, usersLoading } = useSelector((state: GlobalState) => state.users);
  const { profiles, profilesLoading } = useSelector((state: GlobalState) => state.profiles);
  const { posts, postsLoading } = useSelector((state: GlobalState) => state.posts);
  const { pipelines, pipelinesLoading } = useSelector((state: GlobalState) => state.pipelines);

  const [userProfile, setUserProfile] = useState<any | null>(null);
  const { currentUser } = useSelector((state: GlobalState) => state.users);

  // Modal State
  const [isPostModalOpen, setPostModalOpen] = useState(false);
  const [editingPost, setEditingPost] = useState<any | null>(null);
  const [isPipelineModalOpen, setPipelineModalOpen] = useState(false);
  const [editingPipeline, setEditingPipeline] = useState<any | null>(null);

  useEffect(() => {
    // Redirect to login if not authenticated
    if (!currentUser) {
      navigate("/login");
    }
  }, [currentUser, navigate]);

  useEffect(() => {
    dispatch(fetchProfiles());
    dispatch(fetchUsers());
  }, [dispatch]);

  useEffect(() => {
    if (!usersLoading && users.length > 0) {
      const selectedUser = users.find((user: any) => user.id === Number(userId));
      const selectedProfile = profiles.find((profile: any) => profile.userId === Number(userId));
      if (selectedUser) {
        setUserProfile({
          username: selectedUser.username,
          avatar: selectedProfile.avatar,
          bio: selectedProfile.bio,
        });
        dispatch(fetchPostsByUserId(selectedUser.id));
        dispatch(fetchUserPipelines(selectedUser.username));
      }
    }
  }, [dispatch, users, usersLoading, userId]);

  if (!currentUser) {
    return null; // Render nothing while redirecting
  }

  if (usersLoading || profilesLoading || postsLoading || pipelinesLoading) {
    return <p className="text-center text-gray-500">Loading user profile...</p>;
  }

  if (!userProfile) {
    return <p className="text-center text-gray-500">User profile not found</p>;
  }

  // Save Post Handler (Create or Edit)
  const handleSavePost = (post: {
    id?: number;
    text: string;
    visibility: string;
    pipelineId: number | null;
    image: string | null;
  }) => {
    if (post.id) {
      // Edit Existing Post
      dispatch(updatePostThunk(post.id, post.text, post.visibility, post.pipelineId, post.image));
    } else {
      // Create New Post
      dispatch(createPost(post.text, post.visibility, post.pipelineId, post.image || undefined));
    }
    setEditingPost(null);
    setPostModalOpen(false);
  };

  const handleSavePipeline = (pipeline: {
    id?: number;
    name: string;
    description: string;
    visibility: string;
  }) => {
    const isValidVisibility = (value: string): value is Visibility => {
      return ["PUBLIC", "PRIVATE", "FRIENDS-ONLY", "GROUPS-ONLY"].includes(value);
    };
  
    if (isValidVisibility(pipeline.visibility)) {
      if (pipeline.id) {
        // Edit Existing Pipeline
        dispatch(
          updatePipelineThunk(
            pipeline.id,
            pipeline.name,
            JSON.stringify([]),
            pipeline.visibility,
            pipeline.description
          )
        );
      } else {
        // Create New Pipeline
        dispatch(
          createPipeline(
            pipeline.name,
            JSON.stringify([]),
            pipeline.visibility,
            pipeline.description
          )
        );
      }
      setEditingPipeline(null);
      setPipelineModalOpen(false);
    } else {
      if (pipeline.id) {
        // Edit Existing Pipeline
        dispatch(
          updatePipelineThunk(
            pipeline.id,
            pipeline.name,
            JSON.stringify([]),
            "public",
            pipeline.description
          )
        );
      } else {
        // Create New Pipeline
        dispatch(
          createPipeline(
            pipeline.name,
            JSON.stringify([]),
            "public",
            pipeline.description
          )
        );
      }
      setEditingPipeline(null);
      setPipelineModalOpen(false);
    }
  };
  

  // Open Edit Modal
  const openEditPostModal = (post: any) => {
    setEditingPost(post);
    setPostModalOpen(true);
  };

  const openEditPipelineModal = (pipeline: any) => {
    setEditingPipeline(pipeline);
    setPipelineModalOpen(true);
  };

  // Delete Post Handler
  const handleDeletePost = (postId: number) => {
    if (window.confirm("Are you sure you want to delete this post?")) {
      dispatch(deletePostThunk(postId));
    }
  };

  // Delete Pipeline Handler
  const handleDeletePipeline = (pipelineId: number) => {
    if (window.confirm("Are you sure you want to delete this pipeline?")) {
      dispatch(deletePipeline(pipelineId));
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      {/* User Profile Header */}
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

      {/* Manage Posts */}
      <div className="mt-6 bg-white shadow-md rounded-lg p-6 space-y-4 w-full max-w-3xl mx-auto">
        <div className="flex justify-between items-center">
          <h3 className="text-xl font-semibold text-gray-800">Posts</h3>
          <button
            onClick={() => {
              setEditingPost(null); // Clear editing state for creating new post
              setPostModalOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-300"
          >
            Create Post
          </button>
        </div>
        {posts.length === 0 ? (
          <p className="text-center text-gray-500">No posts available.</p>
        ) : (
          posts.map((post: any) => (
            <div key={post.id} className="bg-gray-50 p-4 rounded-lg shadow-md">
              <p className="text-gray-700 text-lg font-semibold">{post.name}</p>
              <p className="text-gray-600">{post.text}</p>
              {post.image && (
                <div className="mt-4 flex justify-center">
                  <img
                    src={post.image}
                    alt="Post Image"
                    className="w-full max-w-md rounded-lg shadow-md"
                  />
                </div>
              )}
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditPostModal(post)}
                  className="text-blue-500 hover:underline"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDeletePost(post.id)}
                  className="text-red-500 hover:underline"
                >
                  Delete
                </button>
              </div>
            </div>
          ))
        )}
      </div>

      {/* Manage Pipelines */}
      <div className="mt-6 bg-white shadow-md rounded-lg p-6 space-y-4 w-full max-w-3xl mx-auto">
        <div className="flex justify-between items-center">
          <h3 className="text-xl font-semibold text-gray-800">Pipelines</h3>
          <button
            onClick={() => {
              setEditingPipeline(null); // Clear editing state for creating new pipeline
              setPipelineModalOpen(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-300"
          >
            Create Pipeline
          </button>
        </div>
        {pipelines.length === 0 ? (
          <p className="text-center text-gray-500">No pipelines available.</p>
        ) : (
          pipelines.map((pipeline: any) => (
            <div key={pipeline.id} className="bg-gray-50 p-4 rounded-lg shadow-md">
              <h4 className="text-lg font-semibold text-gray-800">{pipeline.name}</h4>
              <p className="text-gray-600">{pipeline.description || "No description provided."}</p>
              <div className="flex justify-between items-center mt-4">
                <button
                  onClick={() => openEditPipelineModal(pipeline)}
                  className="text-blue-500 hover:underline"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDeletePipeline(pipeline.id)}
                  className="text-red-500 hover:underline"
                >
                  Delete
                </button>
              </div>
            </div>
          ))
        )}
      </div>

      {/* Post Modal */}
      <PostModal
        isOpen={isPostModalOpen}
        onClose={() => {
          setPostModalOpen(false);
          setEditingPost(null);
        }}
        onSave={handleSavePost}
        pipelines={pipelines}
        post={editingPost} // Pass the post to edit, or null for creating a new one
      />

      {/* Pipeline Modal */}
      <PipelineModal
        isOpen={isPipelineModalOpen}
        onClose={() => {
          setPipelineModalOpen(false);
          setEditingPipeline(null);
        }}
        onSave={handleSavePipeline}
        pipeline={editingPipeline} // Pass the pipeline to edit, or null for creating a new one
      />
    </div>
  );
};

export default UserProfile;