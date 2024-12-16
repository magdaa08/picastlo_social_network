import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { GlobalState } from "../../store";
import { actionDecrement, actionIncrement } from "../../store/Counter";

export const Homepage = () => {
  const counter = useSelector((state: GlobalState) => state.counter);
  const dispatch = useDispatch();

  return (
    <div>
      <h1>Welcome to Picastlo Social Network</h1>
      <p>Counter: {counter}</p>
      <button onClick={() => dispatch(actionIncrement())}>+</button>
      <button onClick={() => dispatch(actionDecrement())}>-</button>
      <br />
      <br />
    </div>
  );
};

export default Homepage;
