import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./components/HomePage";
//import "./App.css";
import LoginPage from "./components/login/LoginPage";
import AdminSignUpPage from "./components/admin_sign_up/AdminSignUpPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/admin" element={<AdminSignUpPage />} />
      </Routes>
    </Router>
  );
}

export default App;
