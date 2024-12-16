import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import userReducer from './slices/userSlice';
import postReducer from './slices/postSlice';
import pipelineReducer from './slices/pipelineSlice';
import connectionsReducer from './slices/connectionsSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer, // Add auth slice here
    user: userReducer,
    posts: postReducer,
    pipelines: pipelineReducer,
    connections: connectionsReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

