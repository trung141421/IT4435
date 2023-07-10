import './App.css';
import { Login } from './Pages/Login';
import { Register } from './Pages/Register';
import { Home } from './Pages/Home';
import {Routes, Route } from 'react-router-dom';
import EditRoom from './Pages/EditRoom';
import AddRoom from './Pages/AddRoom';
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path = '/' element = {<Login/>}/>
        <Route path = '/register' element = {<Register/>}/>
        <Route path = '/home' element = {<Home/>}/>
        <Route path = '/editroom' element = {<EditRoom/>}/>
        <Route path='/addroom' element = {<AddRoom/>}/> 
      </Routes>
    </div>
  );
}
export default App;
