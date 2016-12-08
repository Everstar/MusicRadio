import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, Link, IndexRoute, Redirect , hashHistory } from 'react-router'
import App from './App';
import Sign from './account/Sign'
import Discover from './Discover'
import Home from './Home'
import './index.css';


// ReactDOM.render(
//   <App />,
//   document.getElementById('root')
// );

ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={App}>
            <IndexRoute component={Discover}/>
            <Route path="/Sign" component={Sign}/>
            <Route path="/Discover" component={Discover}/>
            <Route path="/Home" component={Home}/>
        </Route>
    </Router>
), document.getElementById("root"));