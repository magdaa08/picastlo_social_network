import React, { useState, useEffect } from "react";

interface CreatePostModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (post: { id?: number; text: string; visibility: string; pipelineId: number | null; image: string | null }) => void;
  pipelines: { id: number; name: string }[];
  post?: { id: number; text: string; visibility: string; pipelineId: number | null; image: string | null }; // For editing
}

const CreatePostModal: React.FC<CreatePostModalProps> = ({ isOpen, onClose, onSave, pipelines, post }) => {
  const [text, setText] = useState("");
  const [visibility, setVisibility] = useState("public");
  const [pipelineId, setPipelineId] = useState<number | null>(null);
  const [image, setImage] = useState("");

  useEffect(() => {
    if (post) {
      setText(post.text);
      setVisibility(post.visibility);
      setPipelineId(post.pipelineId);
      setImage(post.image || "");
    } else {
      setText("");
      setVisibility("public");
      setPipelineId(null);
      setImage("");
    }
  }, [post]);

  const handleSave = () => {
    onSave({ id: post?.id, text, visibility, pipelineId, image: image || null });
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md">
        <div className="border-b px-4 py-2 flex justify-between items-center">
          <h2 className="text-lg font-semibold">{post ? "Edit Post" : "Create Post"}</h2>
          <button onClick={onClose} className="text-gray-500 hover:text-gray-700 font-bold text-xl">
            &times;
          </button>
        </div>
        <div className="p-4 space-y-4">
          {/* Name Field */}
          <div>
            <label className="block text-gray-700 font-medium mb-1">Name</label>
            <input
              type="text"
              value={text}
              onChange={(e) => setText(e.target.value)}
              placeholder="Post name"
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              required
            />
          </div>

          {/* Visibility Dropdown */}
          <div>
            <label className="block text-gray-700 font-medium mb-1">Visibility</label>
            <select
              value={visibility}
              onChange={(e) => setVisibility(e.target.value)}
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              <option value="public">Public</option>
              <option value="private">Private</option>
              <option value="friends-only">Friends Only</option>
              <option value="groups-only">Groups Only</option>
            </select>
          </div>

          {/* Pipeline Dropdown */}
          <div>
            <label className="block text-gray-700 font-medium mb-1">Pipeline</label>
            <select
              value={pipelineId || ""}
              onChange={(e) => setPipelineId(e.target.value ? Number(e.target.value) : null)}
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            >
              <option value="">None</option>
              {pipelines.map((pipeline) => (
                <option key={pipeline.id} value={pipeline.id}>
                  {pipeline.name}
                </option>
              ))}
            </select>
          </div>

          {/* Image URL Field */}
          <div>
            <label className="block text-gray-700 font-medium mb-1">Image URL (Optional)</label>
            <input
              type="text"
              value={image}
              onChange={(e) => setImage(e.target.value)}
              placeholder="Enter image URL"
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
          </div>

          {/* Save Button */}
          <div className="flex justify-end">
            <button
              onClick={handleSave}
              className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-300"
            >
              {post ? "Update Post" : "Save Post"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreatePostModal;
