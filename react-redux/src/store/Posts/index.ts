import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";

export interface PostState {
  posts: any[];
  loading: boolean;
  error: string | null;
}

const initialState: PostState = {
  posts: [],
  loading: false,
  error: null,
};

const postSlice = createSlice({
  name: "posts",
  initialState,
  reducers: {
    setPosts: (state, action: PayloadAction<any[]>) => {
      state.posts = action.payload;
      state.loading = false;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.loading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

const { setPosts, setLoading, setError } = postSlice.actions;

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
