import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import axios from 'axios';

// Define the Post interface
export interface Post {
  id: number;
  title: string;
  content: string;
  imageUrl?: string;
  visibility: 'PUBLIC' | 'FRIENDS_ONLY' | 'GROUPS_ONLY';
  userId: number; // ID of the post's owner
  createdAt: string; // ISO string
}

// Define the initial state for the slice
interface PostState {
  posts: Post[]; // List of all posts
  currentPost: Post | null; // Currently selected or viewed post
  status: 'idle' | 'loading' | 'failed'; // Status of async operations
  error: string | null; // Error messages
}

const initialState: PostState = {
  posts: [],
  currentPost: null,
  status: 'idle',
  error: null,
};

// Async Thunks for post operations

// Fetch the public feed
export const fetchPublicFeed = createAsyncThunk('posts/fetchPublicFeed', async (_, { rejectWithValue }) => {
  try {
    const response = await axios.get<Post[]>('/api/posts/public_feed');
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || 'Failed to fetch public feed.');
  }
});

// Fetch posts by a specific username
export const fetchPostsByUsername = createAsyncThunk(
  'posts/fetchPostsByUsername',
  async (username: string, { rejectWithValue }) => {
    try {
      const response = await axios.get<Post[]>(`/api/posts/${username}`);
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || `Failed to fetch posts for ${username}.`);
    }
  }
);

// Create a new post
export const createPost = createAsyncThunk('posts/createPost', async (post: Omit<Post, 'id' | 'createdAt'>, { rejectWithValue }) => {
  try {
    const response = await axios.post<Post>('/api/posts/new', post);
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || 'Failed to create post.');
  }
});

// Update an existing post
export const updatePost = createAsyncThunk('posts/updatePost', async (post: Post, { rejectWithValue }) => {
  try {
    const response = await axios.put<Post>(`/api/posts/${post.id}`, post);
    return response.data;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || 'Failed to update post.');
  }
});

// Delete a post by ID
export const deletePost = createAsyncThunk('posts/deletePost', async (id: number, { rejectWithValue }) => {
  try {
    await axios.delete(`/api/posts/${id}`);
    return id;
  } catch (error: any) {
    return rejectWithValue(error.response?.data?.message || `Failed to delete post with ID: ${id}`);
  }
});

// Post slice
const postSlice = createSlice({
  name: 'posts',
  initialState,
  reducers: {
    setCurrentPost(state, action: PayloadAction<Post | null>) {
      state.currentPost = action.payload;
    },
    clearError(state) {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch public feed
      .addCase(fetchPublicFeed.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchPublicFeed.fulfilled, (state, action: PayloadAction<Post[]>) => {
        state.status = 'idle';
        state.posts = action.payload;
      })
      .addCase(fetchPublicFeed.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Fetch posts by username
      .addCase(fetchPostsByUsername.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchPostsByUsername.fulfilled, (state, action: PayloadAction<Post[]>) => {
        state.status = 'idle';
        state.posts = action.payload;
      })
      .addCase(fetchPostsByUsername.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Create post
      .addCase(createPost.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(createPost.fulfilled, (state, action: PayloadAction<Post>) => {
        state.status = 'idle';
        state.posts.push(action.payload);
      })
      .addCase(createPost.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Update post
      .addCase(updatePost.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(updatePost.fulfilled, (state, action: PayloadAction<Post>) => {
        state.status = 'idle';
        const index = state.posts.findIndex((post) => post.id === action.payload.id);
        if (index !== -1) {
          state.posts[index] = action.payload;
        }
      })
      .addCase(updatePost.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      })

      // Delete post
      .addCase(deletePost.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(deletePost.fulfilled, (state, action: PayloadAction<number>) => {
        state.status = 'idle';
        state.posts = state.posts.filter((post) => post.id !== action.payload);
      })
      .addCase(deletePost.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      });
  },
});

// Export actions
export const { setCurrentPost, clearError } = postSlice.actions;

// Export reducer
export default postSlice.reducer;
