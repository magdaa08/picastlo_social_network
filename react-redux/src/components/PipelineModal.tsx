import React, { useState, useEffect } from "react";

interface CreatePipelineModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (pipeline: { id?: number; name: string; description: string; visibility: string }) => void;
  pipeline?: { id: number; name: string; description: string; visibility: string }; // For editing
}

const CreatePipelineModal: React.FC<CreatePipelineModalProps> = ({ isOpen, onClose, onSave, pipeline }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [visibility, setVisibility] = useState("public");

  useEffect(() => {
    if (pipeline) {
      setName(pipeline.name);
      setDescription(pipeline.description || "");
      setVisibility(pipeline.visibility);
    } else {
      setName("");
      setDescription("");
      setVisibility("public");
    }
  }, [pipeline]);

  const handleSave = () => {
    onSave({ id: pipeline?.id, name, description: description, visibility });
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md">
        <div className="border-b px-4 py-2 flex justify-between items-center">
          <h2 className="text-lg font-semibold">{pipeline ? "Edit Pipeline" : "Create Pipeline"}</h2>
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
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Pipeline name"
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
              required
            />
          </div>

          {/* Description Field */}
          <div>
            <label className="block text-gray-700 font-medium mb-1">Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Pipeline description"
              className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
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
              <option value="PUBLIC">Public</option>
              <option value="PRIVATE">Private</option>
              <option value="FRIENDS-ONLY">Friends Only</option>
              <option value="GROUPS-ONLY">Groups Only</option>
            </select>
          </div>

          {/* Save Button */}
          <div className="flex justify-end">
            <button
              onClick={handleSave}
              className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition duration-300"
            >
              {pipeline ? "Update Pipeline" : "Save Pipeline"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreatePipelineModal;
