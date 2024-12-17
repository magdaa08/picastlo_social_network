import { configureStore } from "@reduxjs/toolkit"
import { logger } from 'redux-logger'
import postReducer, { fetchPublicFeed, PostState } from "./Posts"
import counterReducer, { CounterState } from "./Counter"

export interface GlobalState {
    posts: PostState,
    counter: CounterState
}

export const store = configureStore({
    reducer : {
        posts: postReducer,
        counter: counterReducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
})

store.dispatch(fetchPublicFeed())