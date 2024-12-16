import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

// Define types for connections and groups
export interface FriendshipDTO {
  id1: number; // User ID 1
  id2: number; // User ID 2
}

export interface GroupDTO {
  name: string;
  description: string;
}

// Define the initial state for connections
interface ConnectionsState {
  friends: FriendshipDTO[]; // List of friendships
  groups: GroupDTO[]; // List of group memberships
  status: 'idle' | 'loading' | 'failed'; // Status of async actions
  error: string | null; // Error messages, if any
}

const initialState: ConnectionsState = {
  friends: [],
  groups: [],
  status: 'idle',
  error: null,
};

// Async Thunks for fetching data

// Fetch friendships for a user by username
export const fetchFriendsByUsername = createAsyncThunk(
  'connections/fetchFriendsByUsername',
  async (username: string, { rejectWithValue }) => {
    try {
      const response = await axios.get<FriendshipDTO[]>(`/api/connections/friendships/${username}`);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to fetch friends for ${username}`);
    }
  }
);

// Fetch group memberships for a user by username
export const fetchGroupsByUsername = createAsyncThunk(
  'connections/fetchGroupsByUsername',
  async (username: string, { rejectWithValue }) => {
    try {
      const response = await axios.get<GroupDTO[]>(`/api/connections/memberships/${username}`);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to fetch groups for ${username}`);
    }
  }
);

// Slice for managing connections state
const connectionsSlice = createSlice({
  name: 'connections',
  initialState,
  reducers: {
    // Clear all connection-related state (e.g., on logout)
    clearConnectionsState(state) {
      state.friends = [];
      state.groups = [];
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch friendships
      .addCase(fetchFriendsByUsername.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchFriendsByUsername.fulfilled, (state, action: PayloadAction<FriendshipDTO[]>) => {
        state.status = 'idle';
        state.friends = action.payload;
      })
      .addCase(fetchFriendsByUsername.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Fetch group memberships
      .addCase(fetchGroupsByUsername.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchGroupsByUsername.fulfilled, (state, action: PayloadAction<GroupDTO[]>) => {
        state.status = 'idle';
        state.groups = action.payload;
      })
      .addCase(fetchGroupsByUsername.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      });
  },
});

// Export actions
export const { clearConnectionsState } = connectionsSlice.actions;

// Export reducer
export default connectionsSlice.reducer;
