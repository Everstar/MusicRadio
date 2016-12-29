import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, IndexRoute, hashHistory } from 'react-router';
import App from './App';
import Sign from './account/Sign';
import Discover from './Discover';
import Home from './Home';
import HotList from './HotList';
import ManageSongList from './ManageSongList';
import Following from './Following';
import User from './User';
import ViewSongList from './ViewSongList';
import SongList from './SongList';
import API from './API';
import Auth from './account/Auth';
import './index.css';


ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={App} onEnter={() => {if(Auth.username === null) App.getUserInfo();}}>
            <IndexRoute component={Discover}/>
            <Route path="/sign" component={Sign}/>
            <Route path="/discover" component={Discover}/>
            <Route path="/hotlist" component={HotList}/>
            <Route onEnter={() => {if(Auth.username === null) window.location.href='/#/sign';}}>
                <Route path="/home" component={Home}/>
                <Route path="/songlist" component={ManageSongList}/>
                <Route path="/following" component={Following}/>
            </Route>
            <Route onEnter={() => {API.title = 'Music Radio'}}>
                <Route path="/user/:id" component={User}/>
                <Route path="/user/:id/songlist" component={ViewSongList}/>
                <Route path="/songlist/:id(/:songlist_name/:author/:img_id)" component={SongList}/>
            </Route>

        </Route>
    </Router>
), document.getElementById("root"));