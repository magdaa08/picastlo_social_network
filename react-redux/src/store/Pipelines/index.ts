import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";

type Visibility = "public" | "private" | "friends-only" | "groups-only";

export interface Pipeline {
  id: number;
  name: string;
  transformations: string; // JSON string representing the transformations
  visibility: Visibility;
  description: string;
  ownerId: number;
}

export interface PipelineState {
  pipelines: Pipeline[];
  pipelinesLoading: boolean;
  error: string | null;
}

const initialState: PipelineState = {
  pipelines: [],
  pipelinesLoading: false,
  error: null,
};

const pipelineSlice = createSlice({
  name: "pipelines",
  initialState,
  reducers: {
    setPipelines: (state, action: PayloadAction<Pipeline[]>) => {
      state.pipelines = action.payload;
      state.pipelinesLoading = false;
    },
    addPipeline: (state, action: PayloadAction<Pipeline>) => {
      state.pipelines = [action.payload, ...state.pipelines];
    },
    updatePipeline: (state, action: PayloadAction<Pipeline>) => {
      const index = state.pipelines.findIndex((pipeline) => pipeline.id === action.payload.id);
      if (index !== -1) {
        state.pipelines[index] = action.payload;
      }
    },
    deletePipeline: (state, action: PayloadAction<number>) => {
      state.pipelines = state.pipelines.filter((pipeline) => pipeline.id !== action.payload);
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.pipelinesLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

export const {
  setPipelines,
  addPipeline,
  updatePipeline,
  deletePipeline,
  setLoading,
  setError,
} = pipelineSlice.actions;

// Fetch pipelines for a specific user
export const fetchUserPipelines =
  (user: string): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get(`/pipelines/owner/${user}`);
      dispatch(setPipelines(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to fetch pipelines."));
    } finally {
      dispatch(setLoading(false));
    }
  };

// Create a new pipeline
export const createPipeline =
  (
    name: string,
    transformations: string,
    visibility: Visibility,
    description?: string
  ): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch, getState) => {
    const { currentUser } = getState().users;

    if (!currentUser || !currentUser.id) {
      dispatch(setError("User information is incomplete. Cannot create pipeline."));
      return;
    }

    dispatch(setLoading(true));
    try {
      const response = await axios.post("/pipelines", {
        name,
        transformations,
        visibility,
        description: description || "",
        ownerId: currentUser.id,
      });
      dispatch(addPipeline(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to create pipeline."));
    } finally {
      dispatch(setLoading(false));
    }
  };

// Update an existing pipeline
export const updatePipelineThunk =
  (
    id: number,
    name: string,
    transformations: string,
    visibility: Visibility,
    description: string
  ): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.put(`/pipelines/${id}`, {
        name,
        transformations,
        visibility,
        description,
      });
      dispatch(updatePipeline(response.data)); // Update the Redux state with the updated pipeline
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to update pipeline."));
    } finally {
      dispatch(setLoading(false));
    }
  };

// Delete a pipeline
export const deletePipelineThunk =
  (id: number): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      await axios.delete(`/pipelines/${id}`);
      dispatch(deletePipeline(id));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to delete pipeline."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export default pipelineSlice.reducer;
