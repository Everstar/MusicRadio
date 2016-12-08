/**
 * Created by kevin on 12/3/2016.
 */
import React from 'react';
import { browserHistory } from 'react-router'
import {Tabs, Tab} from 'material-ui/Tabs'
import SwipeableViews from 'react-swipeable-views';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import SimpleDialog from '../Dialog/Dialog';
import $ from 'jquery';
import Auth from './Auth'
import API from '../API'

const styles = {
    headline: {
        fontSize: 24,
        paddingTop: 16,
        marginBottom: 12,
        fontWeight: 400,
    },
    slide: {
        marginTop : '2%',
        padding: 10,
    },
};

export default class Sign extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            slideIndex: 0,

            username : null,
            password : null,

            error_username : null,
            error_password : null
        };
    }

    handleChange = (value) => {
        this.setState({
            slideIndex: value,
        });
    };

    //检查用户名是否存在
    checkUsername = (event) => {
        //如果用户名为空
        if (event.target.value == '') {
            this.setState({error_username: 'This field is required'});
        }else {
            const URL = API.Account;
            let callback = 'c'+Math.floor((Math.random()*100000000)+1);
            $.ajax({
                url : URL,
                type : 'POST',
                jsonpCallback: callback, //specify callback name
                contentType: 'application/json',
                dataType: 'jsonp', //specify jsonp
                data : {
                    username : this.state.username
                },
                success : function(data, textStatus, jqXHR) {
                    if(data.result)
                        this.setState({error_username: null});
                    else
                        this.setState({error_username: 'Username not existed'});
                }.bind(this),
                error : function(xhr, textStatus) {
                    console.log(xhr.status + '\n' + textStatus + '\n');
                }
            });
        }
    };

    inputUsername = (event) => {
        this.setState({
            username : event.target.value,
            error_username : null
        });
    };

    inputPassword = (event) => {
        this.setState({
            password : event.target.value,
            error_password : null
        });
    };

    //重定向
    redirectPage = () => {
        browserHistory.push('/');
        window.location.reload();
    };

    // 登录
    onSignIn = () => {
        const URL = API.SignIn;
        let callback = 'c'+Math.floor((Math.random()*100000000)+1);
        $.ajax({
            url : URL,
            type : 'POST',
            jsonpCallback: callback, //specify callback name
            contentType: 'application/json',
            dataType: 'jsonp', //specify jsonp
            data : {
                username : this.state.username,
                passwd : this.state.password
            },
            success : function(data, textStatus, jqXHR) {
                if(data.result){
                    this.refs.dialog.handleOpen();
                    //登录信息保存到本地
                    window.localStorage.setItem('username', this.state.username);
                    Auth.username = this.state.username;
                }
                else this.setState({error_password: 'password not matched'});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    // 注册
    onSignUp = () => {
        console.log("SignUp");
        const URL = API.SignUp;
        let callback = 'c'+Math.floor((Math.random()*100000000)+1);
        $.ajax({
            url : URL,
            type : 'POST',
            jsonpCallback: callback, //specify callback name
            contentType: 'application/json',
            dataType: 'jsonp', //specify jsonp
            data : {
                username : this.state.username,
                passwd : this.state.password
            },
            success : function(data, textStatus, jqXHR) {
                if(data.result) {
                    this.refs.dialog.setContent('Sign Up success!', 'You can sign in now.');
                    this.refs.dialog.handleOpen();
                    this.handleChange(0);
                }
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    render() {
        return (
            <div>
                    <Tabs
                        onChange={this.handleChange}
                        value={this.state.slideIndex}>
                        <Tab label="SignIn" value={0} />
                        <Tab label="SignUp" value={1} />
                    </Tabs>
                <SwipeableViews
                    index={this.state.slideIndex}
                    onChangeIndex={this.handleChange}>

                    {/*登录*/}
                    <div style={styles.slide} className="alignCenter">

                        <h1 className="againstMyself">Music Radio</h1>
                        <p className="JennaSue subTitle">Share your own music radio</p>

                        <TextField hintText="username"
                                   floatingLabelText="username"
                                   type="text"
                                   errorText={this.state.error_username}
                                   onBlur={this.checkUsername}
                                   onChange={this.inputUsername}/>
                        <br/>
                        <TextField hintText="password"
                                   floatingLabelText="password"
                                   type="password"
                                   errorText={this.state.error_password}
                                   onChange={this.inputPassword}/>
                        <br/><br/>

                        <RaisedButton label="Sign In"
                                      primary={true}
                                      className="inputForm"
                                      onClick={this.onSignIn}/>

                    </div>
                    {/*注册*/}
                    <div style={styles.slide} className="alignCenter">

                        <h1 className="againstMyself">Music Radio</h1>
                        <p className="JennaSue subTitle">Set up your own music radio</p>

                        <TextField hintText="username"
                                   floatingLabelText="username"
                                   type="text"
                                   errorText={this.state.error_username}
                                   onBlur={this.checkUsername}
                                   onChange={this.inputUsername}/>
                        <br/>
                        <TextField hintText="password"
                                   floatingLabelText="password"
                                   type="password"
                                   errorText={this.state.error_password}
                                   onChange={this.inputPassword}/>
                        <br/><br/>

                        <RaisedButton label="Register"
                                      primary={true}
                                      className="inputForm"
                                      onClick={this.onSignUp}/>

                    </div>
                </SwipeableViews>
                <SimpleDialog ref="dialog" onPress={this.redirectPage}/>
            </div>
        );
    }
}