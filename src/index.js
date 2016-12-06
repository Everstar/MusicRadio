import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Sign from './account/Sign'
import Discover from './Discover'
import './index.css';
import { Router, Route, Link, IndexRoute, Redirect , hashHistory } from 'react-router'

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
        </Route>
    </Router>
), document.getElementById("root"));