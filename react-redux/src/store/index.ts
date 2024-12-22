import { configureStore } from "@reduxjs/toolkit";
import { logger } from "redux-logger";
import postReducer, { fetchPublicFeed, PostState } from "./Posts";
import userReducer, { fetchUsers, UserState } from "./Users";
import profileReducer, { fetchProfiles, ProfileState } from "./Profiles";

export interface GlobalState {
  posts: PostState;
  users: UserState;
  profiles: ProfileState;
}

export const store = configureStore({
  reducer: {
    posts: postReducer,
    users: userReducer,
    profiles: profileReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

store.dispatch(fetchPublicFeed());
store.dispatch(fetchUsers());
store.dispatch(fetchProfiles());

export type AppDispatch = typeof store.dispatch;
