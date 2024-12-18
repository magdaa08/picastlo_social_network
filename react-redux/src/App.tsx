import './App.css';
import Counter from './components/Counter';
import Homepage from './pages/Homepage';
import Login from './pages/Login';
import { Provider, useSelector } from 'react-redux'
import { GlobalState, store } from './store';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function App() {

  return (
  <Router>
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </Router>
  );
}

const RdxApp = () => 
  <Provider store={store}>
    <App/>
  </Provider>

export default RdxApp;
