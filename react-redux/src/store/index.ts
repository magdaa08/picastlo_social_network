import { configureStore } from "@reduxjs/toolkit";
import { logger } from "redux-logger";
import postReducer, { fetchPublicFeed, PostState } from "./Posts";
import userReducer, { fetchUsers, UserState } from "./Users";
import profileReducer, { fetchProfiles, ProfileState } from "./Profiles";
import pipelineReducer, { fetchUserPipelines, PipelineState } from "./Pipelines"; // Import pipelineSlice

export interface GlobalState {
  posts: PostState;
  users: UserState;
  profiles: ProfileState;
  pipelines: PipelineState; // Add pipelines to GlobalState
}

export const store = configureStore({
  reducer: {
    posts: postReducer,
    users: userReducer,
    profiles: profileReducer,
    pipelines: pipelineReducer, // Add pipeline reducer
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
});

store.dispatch(fetchPublicFeed());
store.dispatch(fetchUsers());
store.dispatch(fetchProfiles());

export type AppDispatch = typeof store.dispatch;

