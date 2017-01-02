import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MenuBar from './MenuBar';
import './App.css';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Auth from './account/Auth'
import $ from 'jquery';
import API from './API';


injectTapEventPlugin();

class App extends React.Component {

    static getUserInfo = () => {
        const URL = API.Info;
        $.ajax({
            url : URL,
            type : 'POST',
            async : false,
            contentType: 'application/json',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                console.log('登录状态存在:' + data.username);
                Auth.username = data.username;
                Auth.Avator = data.avator_url;
            },
            error : function(xhr, textStatus) {
                console.log('未登录');
                //window.location.href='/#/sign';
                //console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        return true;
    };

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
}

export default App;
