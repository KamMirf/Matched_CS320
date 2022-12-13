import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './Home';
import Login from './Login';
import Profile from './Profile';
import MatchOptions from './MatchOptions';
import QuestionnaireF from './QuestionnaireFriend';
import QuestionnaireS from './QuestionnaireStudy';
import QuestionnaireD from './QuestionnaireDate';


function Main(){
    //console.log("hi")
return (    
    
    <Routes>
      <Route path='/' element={<Home/>} />
      <Route path='/Login' element={<Login/>} />
      <Route path='/Profile' element={<Profile/>} />
      <Route path='/MatchOptions' element={<MatchOptions/>} />
      <Route path='/QuestionnaireFriend' element={<QuestionnaireF/>} />
      <Route path='/QuestionnaireStudy' element={<QuestionnaireS/>} />
      <Route path='/QuestionnaireDate' element={<QuestionnaireD/>} />
    </Routes>
);
}
export default Main;