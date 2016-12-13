import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MenuBar from './MenuBar';
import './App.css';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Auth from './account/Auth'


injectTapEventPlugin();

const App = React.createClass({

    //初始化
    getInitialState() {
        let username = window.localStorage.getItem('musicradio');
        if(username != null) {
            console.log('登录状态存在:' + username);
            Auth.username = username;
        }
        return {
            logged : false,
            permit : false
        }
    },

    // checkPrivilege() {
    //     let component_id = (this.props.children.type.displayName || this.props.children.type.name);
    //     let permit = false;
    //     if(!this.state.logged)
    //         permit = (component_id === 'Discover' || component_id === 'Sign');
    //     else
    //         permit = true;
    //     this.setState({permit : permit});
    //     return permit;
    // },

    // shouldComponentUpdate() {
    //     return this.checkPrivilege();
    // },
    //
    // componentWillMount() {
    //     this.checkPrivilege();
    // },

    // componentWillUpdate() {
    //     //this.checkPrivilege();
    //     console.log("willupdate");
    //     console.log(this.props.children);
    //     console.log(this.props.children.type.displayName || this.props.children.type.name);
    // },


    render() {
        return (
            <div>
              <MuiThemeProvider>
                  <MenuBar/>
              </MuiThemeProvider>
                <MuiThemeProvider>
                    {/*{this.state.permit ? this.props.children : null}*/}
                    {this.props.children }
                </MuiThemeProvider>
            </div>
        );
    }
});

export default App;
