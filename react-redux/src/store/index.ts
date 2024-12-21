import { configureStore } from "@reduxjs/toolkit";
import { logger } from "redux-logger";
import postReducer, { fetchPublicFeed, PostState } from "./Posts";

export interface GlobalState {
  posts: PostState;
}

export const store = configureStore({
  reducer: {
    posts: postReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

store.dispatch(fetchPublicFeed());

export type AppDispatch = typeof store.dispatch;
