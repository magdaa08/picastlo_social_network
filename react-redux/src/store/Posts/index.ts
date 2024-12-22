import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";

export interface PostState {
  posts: any[];
  postsLoading: boolean;
  error: string | null;
}

const initialState: PostState = {
  posts: [],
  postsLoading: false,
  error: null,
};

const postSlice = createSlice({
  name: "posts",
  initialState,
  reducers: {
    setPosts: (state, action: PayloadAction<any[]>) => {
      state.posts = action.payload;
      state.postsLoading = false;
    },
    addPost: (state, action: PayloadAction<any>) => {
      state.posts = [action.payload, ...state.posts];
    },
    updatePost: (state, action: PayloadAction<{ id: number; text: string }>) => {
      const index = state.posts.findIndex((post) => post.id === action.payload.id);
      if (index !== -1) {
        state.posts[index] = {
          ...state.posts[index],
          text: action.payload.text,
        };
      }
    },    
    deletePost: (state, action: PayloadAction<number>) => {
      state.posts = state.posts.filter((post) => post.id !== action.payload);
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.postsLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

export const { setPosts, addPost, updatePost, deletePost, setLoading, setError } =
  postSlice.actions;

export const fetchPostsByUsername =
  (username: string): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get(`/posts/${username}`);
      dispatch(setPosts(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to fetch posts."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export const fetchPostsByUserId =
  (userId: number): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get(`/posts/all/${userId}`);
      dispatch(setPosts(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to fetch posts."));
    } finally {
      dispatch(setLoading(false));
    }
  }; 

export const fetchPublicFeed =
  (): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get("/posts/public_feed");
      dispatch(setPosts(response.data));
    } catch (error: any) {
      dispatch(setError(error.message || "Failed to fetch posts."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export const createPost =
  (
    text: string,
    visibility: string,
    pipelineId: number | null,
    image?: string
  ): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch, getState) => {
    const { currentUser } = getState().users;

    if (!currentUser || !currentUser.id) {
      dispatch(setError("User information is incomplete. Cannot create post."));
      return;
    }

    dispatch(setLoading(true));
    try {
      const response = await axios.post("/posts/new", {
        text,
        image: image || null,
        pipelineId,
        visibility,
        userId: currentUser.id,
      });

      dispatch(addPost(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to create post."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export const updatePostThunk =
  (
    id: number,
    text: string,
    visibility: string,
    pipelineId: number | null,
    image: string | null
  ): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.put(`/posts/${id}`, {
        text,
        visibility,
        pipelineId,
        image,
      });
      dispatch(updatePost(response.data));
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to update post."));
    } finally {
      dispatch(setLoading(false));
    }
  };

// Delete a post
export const deletePostThunk =
  (postId: number): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      await axios.delete(`/posts/${postId}`);
      dispatch(deletePost(postId)); // Remove the post from Redux state
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Failed to delete post."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export default postSlice.reducer;

