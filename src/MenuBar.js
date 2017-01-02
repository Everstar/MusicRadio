/**
 * Created by tsengkasing on 12/2/2016.
 */
import React, {Component} from 'react';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import Drawer from 'material-ui/Drawer';
import AvMusicVideo from 'material-ui/svg-icons/av/music-video'
import NavigationMenu from 'material-ui/svg-icons/navigation/menu'
import MenuItem from 'material-ui/MenuItem';
import Divider from 'material-ui/Divider';
import Avatar from 'material-ui/Avatar';
import {IndexLink, Link, browserHistory, hashHistory}from 'react-router';
import Auth from './account/Auth'
import API from './API'
import $ from 'jquery';


class Signin extends React.Component {
    static muiName = 'FlatButton';

    constructor(props) {
        super(props);
        this.state = {display : 'block'};
    }

    onSwitch = () => {
        this.props.onSwitch('Sign');
        this.setState({display : 'none'});
    };

    render() {
        const Props = Object.assign({}, this.props);
        delete Props.onSwitch;

        return (
            <Link to="/Sign" style={{display:this.state.display}}>
                <FlatButton {...Props} label="SignIn" rippleColor="pink" onClick={this.onSwitch}/>
            </Link>
        );
    }
}

const MenubarHeader_Background = {
    backgroundImage: 'url("http://img.neverstar.top/account-bg.png")',
};

class DrawerUndocked extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            open: false,

            menuItem_status : [false, false, false, false, false, false],

        };
    }

    handleToggle = () => this.setState({open: !this.state.open});

    handleClose = () => this.setState({open: false});

    onTouchMenuItem = (index) => {
        let status = [false, false, false, false, false, false];
        status[index] = true;

        this.setState({
            menuItem_status : status
        });
    };

    onclick = (event) => {
        if(window.location.hash === '#/' + event.target.innerHTML )
            return;

        switch (event.target.innerHTML) {
            case 'Discover' :
                console.log('Discover');
                API.title = 'Discover';
                hashHistory.push('Discover');
                break;
            case 'Hot List' :
                console.log('HotList');
                API.title = 'HotList';
                hashHistory.push('HotList');
                break;
            case 'Home' :
                console.log('Home');
                API.title = 'Home';
                hashHistory.push('Home');
                break;
            case 'Song List' :
                console.log('SongList');
                API.title = 'SongList';
                hashHistory.push('SongList');
                break;
            case 'Following' :
                console.log('Following');
                API.title = 'Following';
                hashHistory.push('Following');
                break;
            default:
                break;
        }
    };

    onSignOut = (event) => {
        const URL = API.SignOut;
        $.ajax({
            url : URL,
            type : 'GET',
            async : false,
            contentType: 'application/json',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        browserHistory.push('/');
        window.location.reload();
    };

    render() {
        let avator_url = Auth.Avator;
        if(avator_url !== null) {
            avator_url = avator_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
            avator_url = avator_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
        }
        return (
            <div>
                <IconButton
                    iconStyle={{color : 'white'}}
                    onTouchTap={this.handleToggle}
                ><NavigationMenu/></IconButton>
                <Drawer
                    docked={false}
                    width={200}
                    open={this.state.open}
                    onRequestChange={(open) => this.setState({open})}
                >
                    <div className="MenuBar-header" style={MenubarHeader_Background}>
                        <Avatar src={avator_url} size={56} />
                        <br/>
                        <span className="Nickname">{Auth.username}</span>
                    </div>
                    <Divider />
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Discover" disabled={this.state.menuItem_status[0]}/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Hot List" disabled={this.state.menuItem_status[1]}/>
                    <Divider />
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Home" disabled={this.state.menuItem_status[2]}/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Song List" disabled={this.state.menuItem_status[3]}/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Following" disabled={this.state.menuItem_status[4]}/>
                    <Divider />
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onSignOut} primaryText="Sign Out"/>
                </Drawer>
            </div>
        );
    }
}

/**
 * This example is taking advantage of the composability of the `AppBar`
 * to render different components depending on the application state.
 */
class MenuBar extends Component {

    state = {
        logged: false,
    };

    componentWillMount() {
        if(Auth.username != null)
            this.setState({logged : true});
        // if(window.location.hash !== '#/'){
        //     browserHistory.push('/');
        //     window.location.reload();
        // }
    };

    shouldComponentUpdate(){
        if(Auth.username != null) {
            this.setState({logged: true});
            return true;
        }
        return false;
    }

    //回首页
    toMainPage = () => {
        this.refs.sign.setState({display : 'block'});
    };

    handleChange = (event, logged) => {
        this.setState({logged: logged});
    };

    changeTitle = (title) => {
        this.setState({title : title});
    };
    // title={window.location.hash.substr(2) || 'Music Radio'}
    render() {
        return (
            <div>
                <AppBar
                    title={API.title}
                    iconElementLeft={this.state.logged ? <DrawerUndocked onSwitch={this.changeTitle} /> : <IndexLink to="/" activeClassName="active" onlyActiveOnIndex={true}>
                        <IconButton iconStyle={{color:'white'}} tooltip="Discover" touch={true} onClick={this.toMainPage} ><AvMusicVideo /></IconButton>
                    </IndexLink>}
                    iconElementRight={this.state.logged ? null : <Signin ref="sign" onSwitch={this.changeTitle} />}
                />
            </div>
        );
    }
}

export default MenuBar;
