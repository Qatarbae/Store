import { createSlice, PayloadAction } from "@reduxjs/toolkit";


type UserState = {
    isAuth: boolean
}

const initialState:UserState = {
    isAuth: false
}

const userSlice = createSlice({
    name:'user',
    initialState:{
        isAuth: false
    },
    reducers: {
      setAuthStatus(state, action: PayloadAction<boolean>) {
        state.isAuth = action.payload;
      }  
    }
})


export const {setAuthStatus} = userSlice.actions;