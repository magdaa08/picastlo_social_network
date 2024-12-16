import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

// Define the Pipeline type
export interface Pipeline {
  id: number;
  name: string;
  ownerId: number;
  steps: string[]; // Sequence of transformation steps
  visibility: 'public' | 'private' | 'friends-only';
}

// Define the initial state for the slice
interface PipelineState {
  pipelines: Pipeline[]; // List of all pipelines
  currentPipeline: Pipeline | null; // Currently selected or viewed pipeline
  status: 'idle' | 'loading' | 'failed'; // Status of operations
  error: string | null; // Error messages
}

const initialState: PipelineState = {
  pipelines: [],
  currentPipeline: null,
  status: 'idle',
  error: null,
};

// Async Thunks for pipeline operations

// Fetch all pipelines by owner username
export const fetchPipelinesByUsername = createAsyncThunk(
  'pipelines/fetchPipelinesByUsername',
  async (username: string, { rejectWithValue }) => {
    try {
      const response = await axios.get<Pipeline[]>(`/api/pipelines/owner/${username}`);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to fetch pipelines for ${username}`);
    }
  }
);

// Fetch a specific pipeline by ID
export const fetchPipelineById = createAsyncThunk(
  'pipelines/fetchPipelineById',
  async (id: number, { rejectWithValue }) => {
    try {
      const response = await axios.get<Pipeline>(`/api/pipelines/${id}`);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to fetch pipeline with ID: ${id}`);
    }
  }
);

// Create a new pipeline
export const createPipeline = createAsyncThunk(
  'pipelines/createPipeline',
  async (pipeline: Omit<Pipeline, 'id'>, { rejectWithValue }) => {
    try {
      const response = await axios.post<Pipeline>('/api/pipelines', pipeline);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to create pipeline.');
    }
  }
);

// Update an existing pipeline
export const updatePipeline = createAsyncThunk(
  'pipelines/updatePipeline',
  async (pipeline: Pipeline, { rejectWithValue }) => {
    try {
      const response = await axios.put<Pipeline>(`/api/pipelines/${pipeline.id}`, pipeline);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to update pipeline.');
    }
  }
);

// Delete a pipeline by ID
export const deletePipeline = createAsyncThunk(
  'pipelines/deletePipeline',
  async (id: number, { rejectWithValue }) => {
    try {
      await axios.delete(`/api/pipelines/${id}`);
      return id;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to delete pipeline with ID: ${id}`);
    }
  }
);

// Pipeline slice
const pipelineSlice = createSlice({
  name: 'pipelines',
  initialState,
  reducers: {
    // Set the current pipeline manually
    setCurrentPipeline(state, action: PayloadAction<Pipeline | null>) {
      state.currentPipeline = action.payload;
    },
    // Clear the error state
    clearError(state) {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch pipelines by username
      .addCase(fetchPipelinesByUsername.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchPipelinesByUsername.fulfilled, (state, action: PayloadAction<Pipeline[]>) => {
        state.status = 'idle';
        state.pipelines = action.payload;
      })
      .addCase(fetchPipelinesByUsername.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Fetch pipeline by ID
      .addCase(fetchPipelineById.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchPipelineById.fulfilled, (state, action: PayloadAction<Pipeline>) => {
        state.status = 'idle';
        state.currentPipeline = action.payload;
      })
      .addCase(fetchPipelineById.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Create pipeline
      .addCase(createPipeline.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(createPipeline.fulfilled, (state, action: PayloadAction<Pipeline>) => {
        state.status = 'idle';
        state.pipelines.push(action.payload);
      })
      .addCase(createPipeline.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Update pipeline
      .addCase(updatePipeline.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(updatePipeline.fulfilled, (state, action: PayloadAction<Pipeline>) => {
        state.status = 'idle';
        const index = state.pipelines.findIndex((p) => p.id === action.payload.id);
        if (index !== -1) {
          state.pipelines[index] = action.payload;
        }
      })
      .addCase(updatePipeline.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Delete pipeline
      .addCase(deletePipeline.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(deletePipeline.fulfilled, (state, action: PayloadAction<number>) => {
        state.status = 'idle';
        state.pipelines = state.pipelines.filter((pipeline) => pipeline.id !== action.payload);
      })
      .addCase(deletePipeline.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      });
  },
});

// Export actions
export const { setCurrentPipeline, clearError } = pipelineSlice.actions;

// Export reducer
export default pipelineSlice.reducer;
