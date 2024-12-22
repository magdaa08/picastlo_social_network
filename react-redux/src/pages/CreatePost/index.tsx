import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { createPost } from "../../store/Posts";
import { GlobalState, AppDispatch } from "../../store";
import { fetchUserPipelines } from "../../store/Pipelines"; // Import pipeline fetcher

const CreatePost = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { currentUser } = useSelector((state: GlobalState) => state.users);
  const { pipelines, pipelinesLoading } = useSelector((state: GlobalState) => state.pipelines); // Access pipelines
  const { error, postsLoading } = useSelector((state: GlobalState) => state.posts);

  const [text, setText] = useState("");
  const [image, setImage] = useState("");
  const [pipelineId, setPipelineId] = useState<number | null>(null); // Store selected pipeline ID
  const [visibility, setVisibility] = useState("public");

  useEffect(() => {
    if (currentUser) {
      dispatch(fetchUserPipelines(currentUser.username));
    }
  }, [currentUser, dispatch]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!currentUser) {
      alert("You must be logged in to create a post.");
      return;
    }

    dispatch(createPost(text, visibility, pipelineId, image));
    setText("");
    setImage("");
    setPipelineId(null);
  };

  return (
    <div className="bg-white p-4 rounded-lg shadow-md mb-4">
      <h2 className="text-xl font-semibold mb-3">Create a New Post</h2>

      {error && <p className="text-red-500 mb-2">{error}</p>}

      <form onSubmit={handleSubmit}>
        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="What's on your mind?"
          className="w-full px-3 py-2 mb-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        ></textarea>

        <input
          type="text"
          value={image}
          onChange={(e) => setImage(e.target.value)}
          placeholder="Image URL (optional)"
          className="w-full px-3 py-2 mb-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />

        <div className="mb-3">
          <label className="block text-gray-700 font-medium mb-1">Pipeline</label>
          {pipelinesLoading ? (
            <p className="text-gray-500">Loading pipelines...</p>
          ) : (
            <select
              value={pipelineId || ""}
              onChange={(e) => setPipelineId(Number(e.target.value) || null)}
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              <option value="">Select a pipeline (optional)</option>
              {pipelines.map((pipeline: any) => (
                <option key={pipeline.id} value={pipeline.id}>
                  {pipeline.name}
                </option>
              ))}
            </select>
          )}
        </div>

        <div className="mb-3">
          <label className="block text-gray-700 font-medium mb-1">Visibility</label>
          <select
            value={visibility}
            onChange={(e) => setVisibility(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            required
          >
            <option value="PUBLIC">Public</option>
            <option value="PRIVATE">Private</option>
            <option value="FRIENDS-ONLY">Friends</option>
            <option value="GROUPS-ONLY">Groups</option>
          </select>
        </div>

        <button
          type="submit"
          disabled={postsLoading}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-300"
        >
          {postsLoading ? "Posting..." : "Post"}
        </button>
      </form>
    </div>
  );
};

export default CreatePost;
