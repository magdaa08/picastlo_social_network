import { configureStore } from "@reduxjs/toolkit";
import { logger } from "redux-logger";
import postReducer, { fetchPublicFeed, PostState } from "./Posts";
import userReducer, { fetchUsers, UserState } from "./Users";

export interface GlobalState {
  posts: PostState;
  users: UserState;
}

export const store = configureStore({
  reducer: {
    posts: postReducer,
    users: userReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

store.dispatch(fetchPublicFeed());
store.dispatch(fetchUsers());

export type AppDispatch = typeof store.dispatch;
