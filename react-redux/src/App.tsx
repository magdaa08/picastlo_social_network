import './App.css';
import Counter from './components/Counter';
import Homepage from './components/Homepage';
import { Provider, useSelector } from 'react-redux'
import { GlobalState, store } from './store';

function App() {

  return (
   <div>
    <Homepage/>
    </div>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>

export default RdxApp;
