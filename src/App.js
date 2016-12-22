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
        let status = window.localStorage.getItem('musicradio');
        if(status != null) {
            console.log('登录状态存在:' + status);
            Auth.username = status;
        }
        return {
            logged : false,
            permit : false
        }
    },

    render() {
        return (
            <div>
              <MuiThemeProvider>
                  <MenuBar/>
              </MuiThemeProvider>
                <MuiThemeProvider>
                    {this.props.children}
                </MuiThemeProvider>
            </div>
        );
    }
});

export default App;
