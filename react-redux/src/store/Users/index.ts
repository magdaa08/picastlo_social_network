import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";

export interface UserState {
  users: any[];
  usersLoading: boolean;
  error: string | null;
}

const initialState: UserState = {
  users: [],
  usersLoading: false,
  error: null,
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
  },
});

const { setUsers, setLoading, setError } = userSlice.actions;

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
