import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

// Define the shape of the user data (DTO)
export interface UserDTO {
  id: number;
  username: string;
  email?: string; // Optional field for additional user info
  roles?: string[]; // User roles, if applicable
}

// Define the initial state for the slice
interface UserState {
  currentUser: UserDTO | null; // The currently logged-in user's data
  users: UserDTO[]; // List of all users
  status: 'idle' | 'loading' | 'failed'; // Status of user-related actions
  error: string | null; // Error messages
}

const initialState: UserState = {
  currentUser: null,
  users: [],
  status: 'idle',
  error: null,
};

// Async Thunks for user-related operations

// Fetch the current user's details
export const fetchCurrentUser = createAsyncThunk('user/fetchCurrentUser', async (_, { rejectWithValue }) => {
  try {
    const response = await axios.get<UserDTO>('/api/users/current'); // Endpoint to fetch current user
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || 'Failed to fetch current user.');
  }
});

// Fetch a list of all users
export const fetchAllUsers = createAsyncThunk('user/fetchAllUsers', async (_, { rejectWithValue }) => {
  try {
    const response = await axios.get<UserDTO[]>('/api/users'); // Endpoint to fetch all users
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || 'Failed to fetch users.');
  }
});

// Fetch a specific user by their username
export const fetchUserByUsername = createAsyncThunk('user/fetchUserByUsername', async (username: string, { rejectWithValue }) => {
  try {
    const response = await axios.get<UserDTO>(`/api/users/${username}`);
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || `Failed to fetch user with username: ${username}`);
  }
});

// User slice
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    // Optionally clear the user state
    clearUserState(state) {
      state.currentUser = null;
      state.users = [];
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch current user
      .addCase(fetchCurrentUser.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchCurrentUser.fulfilled, (state, action: PayloadAction<UserDTO>) => {
        state.status = 'idle';
        state.currentUser = action.payload;
      })
      .addCase(fetchCurrentUser.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Fetch all users
      .addCase(fetchAllUsers.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchAllUsers.fulfilled, (state, action: PayloadAction<UserDTO[]>) => {
        state.status = 'idle';
        state.users = action.payload;
      })
      .addCase(fetchAllUsers.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Fetch user by username
      .addCase(fetchUserByUsername.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchUserByUsername.fulfilled, (state, action: PayloadAction<UserDTO>) => {
        state.status = 'idle';
        const existingUserIndex = state.users.findIndex((user) => user.id === action.payload.id);
        if (existingUserIndex !== -1) {
          state.users[existingUserIndex] = action.payload; // Update user in the list
        } else {
          state.users.push(action.payload); // Add user to the list
        }
      })
      .addCase(fetchUserByUsername.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      });
  },
});

// Export actions
export const { clearUserState } = userSlice.actions;

// Export reducer
export default userSlice.reducer;
