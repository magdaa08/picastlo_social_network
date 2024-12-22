import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { ThunkAction } from "@reduxjs/toolkit";
import { AnyAction } from "redux";
import axios from "axios";
import { GlobalState } from "../index";

export interface ProfileState {
  profiles: any[];
  profilesLoading: boolean;
  error: string | null;
}

const initialState: ProfileState = {
  profiles: [],
  profilesLoading: false,
  error: null,
};

const profileSlice = createSlice({
  name: "profiles",
  initialState,
  reducers: {
    setProfile: (state, action: PayloadAction<any[]>) => {
      state.profiles = action.payload;
      state.profilesLoading = false;
    },
    setLoading: (state, action: PayloadAction<boolean>) => {
      state.profilesLoading = action.payload;
    },
    setError: (state, action: PayloadAction<string | null>) => {
      state.error = action.payload;
    },
  },
});

const { setProfile, setLoading, setError } =
  profileSlice.actions;


export const fetchProfiles =
  (): ThunkAction<void, GlobalState, unknown, AnyAction> =>
  async (dispatch) => {
    dispatch(setLoading(true));
    try {
      const response = await axios.get("/users/profiles");
      dispatch(setProfile(response.data));
    } catch (error: any) {
      dispatch(setError(error.message || "Failed to fetch profiles."));
    } finally {
      dispatch(setLoading(false));
    }
  };

export default profileSlice.reducer;
