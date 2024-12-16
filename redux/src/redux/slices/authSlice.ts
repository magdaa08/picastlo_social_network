import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

// Define the shape of the auth state
interface AuthState {
  token: string | null; // JWT token
  isAuthenticated: boolean; // User's logged-in status
  status: 'idle' | 'loading' | 'failed'; // Status of authentication actions
  error: string | null; // Error messages, if any
}

const initialState: AuthState = {
  token: null,
  isAuthenticated: false,
  status: 'idle',
  error: null,
};

// Async Thunks for authentication

// Login thunk
export const login = createAsyncThunk(
  '/login',
  async (credentials: { username: string; password: string }, { rejectWithValue }) => {
    try {
      const response = await axios.post('/api/login', credentials);
      return response.data; // Expecting { token: string } from backend
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Login failed');
    }
  }
);

// Logout thunk
export const logout = createAsyncThunk('/logout', async (_, { dispatch }) => {
  // Perform any server-side logout logic if necessary
  // For example: await axios.post('/api/auth/logout');
  return null; // Logout doesn't need a payload
});

// Auth slice
const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    // Optional: Provide a way to clear errors
    clearError(state) {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Login
      .addCase(login.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(login.fulfilled, (state, action: PayloadAction<{ token: string }>) => {
        state.token = action.payload.token;
        state.isAuthenticated = true;
        state.status = 'idle';
      })
      .addCase(login.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Logout
      .addCase(logout.fulfilled, (state) => {
        state.token = null;
        state.isAuthenticated = false;
      });
  },
});

// Export actions
export const { clearError } = authSlice.actions;

// Export reducer
export default authSlice.reducer;
