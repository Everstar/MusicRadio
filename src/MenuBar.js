/**
 * Created by kevin on 12/2/2016.
 */
import React, {Component} from 'react';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import Toggle from 'material-ui/Toggle';
import Drawer from 'material-ui/Drawer';
import AvMusicVideo from 'material-ui/svg-icons/av/music-video'
import NavigationMenu from 'material-ui/svg-icons/navigation/menu'
import MenuItem from 'material-ui/MenuItem';
import Divider from 'material-ui/Divider';
import Avatar from 'material-ui/Avatar';
import {Link, browserHistory}from 'react-router';
import Auth from './account/Auth'


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
                <FlatButton {...Props} label="SignIn" onClick={this.onSwitch}/>
            </Link>
        );
    }
}

const MenubarHeader_Background = {
    backgroundImage: 'url("img/account-bg.png")',
};

class DrawerUndocked extends React.Component {

    constructor(props) {
        super(props);
        this.state = {open: false};
    }

    handleToggle = () => this.setState({open: !this.state.open});

    handleClose = () => this.setState({open: false});

    onclick = (event) => {
        this.props.onSwitch(event.target.innerHTML);
    };

    onSignOut = (event) => {
        window.localStorage.removeItem('username');
        browserHistory.push('/');
        window.location.reload();
    };

    render() {
        return (
            <div>
                <IconButton
                    onTouchTap={this.handleToggle}
                ><NavigationMenu/></IconButton>
                <Drawer
                    docked={false}
                    width={200}
                    open={this.state.open}
                    onRequestChange={(open) => this.setState({open})}
                >
                    <div className="MenuBar-header" style={MenubarHeader_Background}>
                        <Avatar src="img/profile_1.png" size={56} />
                        <br/>
                        <span id="nickname" className="Nickname" >Everstar</span>
                    </div>
                    <Divider />
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} href="/#/Discover" primaryText="Discover"/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Hot List"/>
                    <Divider />
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} href="/#/Home" primaryText="Home"/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Music List"/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Friends"/>
                    <MenuItem onTouchTap={this.handleClose} onClick={this.onclick} primaryText="Message"/>
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

    constructor(props) {
        super(props);
    };

    state = {
        logged: false,
        title : 'Discover'
    };

    componentWillMount() {
        if(Auth.username != null)
            this.setState({logged : true});
    };

    toMainPage = () => {
        this.setState({title : 'Discover'});
        this.refs.sign.setState({display : 'block'});
    };

    componentWillUpdate() {
        if(this.state.title !== 'Discover')
            this.toMainPage();
    };

    handleChange = (event, logged) => {
        this.setState({logged: logged});
    };

    changeTitle = (title) => {
        this.setState({title : title});
    };

    render() {
        return (
            <div>
                {/*<Toggle*/}
                    {/*label="Logged"*/}
                    {/*defaultToggled={false}*/}
                    {/*onToggle={this.handleChange}*/}
                    {/*labelPosition="right"*/}
                    {/*style={{margin: 20}}*/}
                {/*/>*/}
                <AppBar
                    title={this.state.title}
                    iconElementLeft={this.state.logged ? <DrawerUndocked onSwitch={this.changeTitle} /> : <IconButton href="/#/" onClick={this.toMainPage} ><AvMusicVideo /></IconButton>}
                    iconElementRight={this.state.logged ? null : <Signin ref="sign" onSwitch={this.changeTitle} />}
                />
            </div>
        );
    }
}

export default MenuBar;
