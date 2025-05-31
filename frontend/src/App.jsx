import './App.css';
import ListWorkShiftComponent from "./components/ListWorkShiftComponent";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import WorkShiftComponent from "./components/WorkShiftComponent";

function App() {
  return (
      <>
          <BrowserRouter>
              <HeaderComponent />
                <Routes>
                    {/* http://localhost:3000 */}
                    <Route path='/' element = { <ListWorkShiftComponent /> }></Route>
                    {/* http://localhost:3000/workshifts */}
                    <Route path='/workshifts' element = { <ListWorkShiftComponent /> }></Route>

                    {/* http://localhost:3000/add-workshift */}
                    <Route path='/add-workshift' element = { <WorkShiftComponent /> }></Route>

                    {/* http://localhost:3000/edit-workshift/1 */}
                    <Route path='/edit-workshift/:id' element = { <WorkShiftComponent /> }></Route>
                </Routes>
              {/* <FooterComponent /> */}
          </BrowserRouter>
      </>
  );
}

export default App;
