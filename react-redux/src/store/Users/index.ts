import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";
import { Console } from "console";

export interface UserState {
  users: any[];
  usersLoading: boolean;
  error: string | null;
  isAuthenticated: boolean;
  currentUser: any | null;
}

const initialState: UserState = {
  users: [],
  usersLoading: false,
  error: null,
  isAuthenticated: false,
  currentUser: null,
};

const userSlice = createSlice({
  name: "users",
  initialState,
  reducers: {
    setUsers: (state, action: PayloadAction<any[]>) => {
      state.users = action.payload;
      state.usersLoading = false;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.usersLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
    setAuthenticated: (state, action: PayloadAction<boolean>) => {
      state.isAuthenticated = action.payload;
    },
    setCurrentUser: (state, action: PayloadAction<any | null>) => {
      state.currentUser = action.payload;
    },
  },
});

const { setUsers, setLoading, setError, setAuthenticated, setCurrentUser } =
  userSlice.actions;

export const login =
  (
    username: string,
    password: string
  ): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.post("/users/login", { username, password });
      if (response.status === 200) {
        const tempUser = { username: username };
        dispatch(setAuthenticated(true));
        dispatch(setCurrentUser(tempUser));
      } else {
        throw new Error("Login failed.");
      }
    
    } catch (error: any) {
      dispatch(setError(error.response?.data?.message || "Login failed."));
      dispatch(setAuthenticated(false));
    } finally {
      dispatch(setLoading(false));
    }
  };

export const fetchUsers =
  (): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get("/users");
      dispatch(setUsers(response.data));
    } catch (error: any) {
      dispatch(setError(error.message || "Failed to fetch users."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export default userSlice.reducer;
