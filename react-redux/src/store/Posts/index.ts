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
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.postsLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

const { setPosts, setLoading, setError } = postSlice.actions;

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

export default postSlice.reducer;

