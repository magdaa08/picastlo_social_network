import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { PostsApi } from '../../api/posts'; // Your API import
import axios from 'axios';

// Define the initial state for the slice
export interface PostState {
  posts: any[]; // Type posts as any[]
  loading: boolean;
  uploading: boolean;
  error: string | null; // Keep track of errors as string or null
}

const initialState: PostState = {
  posts: [],
  loading: false,
  uploading: false,
  error: null, // Initially no error
};

const api = new PostsApi(); // Assuming you have an API class to fetch posts

// Slice with actions and reducers
const postSlice = createSlice({
  name: 'posts',
  initialState,
  reducers: {
    setPosts: (state, action: PayloadAction<any[]>) => {
      state.posts = action.payload;
      state.loading = false; // Set loading to false after fetching posts
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setUploading: (state, action: PayloadAction<boolean>) => {
      state.uploading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload; // Set the error message when it occurs
    },
  },
});

const { setPosts, setLoading, setUploading, setError } = postSlice.actions;

// Fetch all posts (this is your public feed)
export const fetchPublicFeed = () => async (dispatch: any) => {
  dispatch(setLoading(true)); // Set loading to true when starting to fetch posts

  try {
    const response = await api.getPublicFeed(); // Assuming you have a method for this
    dispatch(setPosts(response)); // Dispatch the posts after fetching them
  } catch (error: any) {
    dispatch(setError(error.message || 'Failed to fetch posts.')); // Set error message
  } finally {
    dispatch(setLoading(false)); // Set loading to false when done
  }
};

// Create a new post
export const createPost = (post: any) => async (dispatch: any) => {
  dispatch(setUploading(true)); // Set uploading to true when starting the upload

  try {
    const response = await axios.post('/posts/new', post); // Replace with your actual API endpoint
    dispatch(setPosts([...initialState.posts, response.data])); // Add new post to the state
  } catch (error: any) {
    dispatch(setError(error.message || 'Failed to create post.')); // Set error message
  } finally {
    dispatch(setUploading(false)); // Set uploading to false when done
  }
};

// Update an existing post
export const updatePost = (post: any) => async (dispatch: any) => {
  dispatch(setLoading(true)); // Set loading to true while updating

  try {
    const response = await axios.put(`/posts/${post.id}`, post);
    dispatch(setPosts(initialState.posts.map(p => p.id === post.id ? response.data : p)));
  } catch (error: any) {
    dispatch(setError(error.message || 'Failed to update post.')); // Set error message
  } finally {
    dispatch(setLoading(false)); // Set loading to false when done
  }
};

// Delete a post
export const deletePost = (id: number) => async (dispatch: any) => {
  dispatch(setLoading(true)); // Set loading to true while deleting

  try {
    await axios.delete(`/posts/${id}`);
    dispatch(setPosts(initialState.posts.filter(post => post.id !== id))); // Remove deleted post from state
  } catch (error: any) {
    dispatch(setError(error.message || 'Failed to delete post.')); // Set error message
  } finally {
    dispatch(setLoading(false)); // Set loading to false when done
  }
};

// Export actions and reducer
export default postSlice.reducer;