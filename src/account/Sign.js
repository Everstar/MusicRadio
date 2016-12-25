/**
 * Created by tsengkasing on 12/3/2016.
 */
import React from 'react';
import { hashHistory } from 'react-router'
import {Tabs, Tab} from 'material-ui/Tabs'
import SwipeableViews from 'react-swipeable-views';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {RadioButton, RadioButtonGroup} from 'material-ui/RadioButton';
import $ from 'jquery';
import SimpleDialog from './Dialog';
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
    radioButtonGroup: {
        textAlign : 'center',
        margin : '0 auto',
        marginBottom: '16px'
    }
};

export default class Sign extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            slideIndex: 0,

            username : null,
            password : null,

            error_username : null,
            error_password : null,

            sex : 'M',
        };
    }

    handleChange = (value) => {
        this.setState({
            slideIndex: value,
        });
    };

    //检查用户名是否存在
    checkUsernameExist = (event) => {
        //如果用户名为空
        if (event.target.value === '') {
            this.setState({error_username: 'This field is required'});
        }else {
            const URL = API.Account;
            $.ajax({
                url : URL,
                type : 'GET',
                headers : {
                    'target' : 'api',
                },
                contentType: 'application/json',
                data : {
                    id : this.state.username
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

    //检查用户名是否存在
    checkUsernameNotExist = (event) => {
        //如果用户名为空
        if (event.target.value === '') {
            this.setState({error_username: 'This field is required'});
        }else {
            const URL = API.Account;
            $.ajax({
                url : URL,
                type : 'GET',
                headers : {
                    'target' : 'api',
                },
                contentType: 'application/json',
                data : {
                    id : this.state.username
                },
                success : function(data, textStatus, jqXHR) {
                    if(data.result)
                        this.setState({error_username: 'This username is already used!'});
                    else
                        this.setState({error_username: null});
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
        hashHistory.push('/Home');
    };

    // 登录
    onSignIn = () => {

        if(this.state.error_username !== null) return;

        /*
        * 为了测试而存在
        */
        if(this.state.username === 'test'){
            this.refs.dialog.handleOpen();
            //登录信息保存到本地
            window.localStorage.setItem('musicradio', this.state.username);
            Auth.username = this.state.username;
            return;
        }


        let data ={ username : this.state.username, password : this.state.password };
        const URL = API.SignIn;
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                if(data.result){
                    this.refs.dialog.handleOpen(true);
                    window.localStorage.setItem('musicradio', this.state.username);
                    Auth.username = this.state.username;
                    Auth.Avator = this.getAvator();
                }
            }.bind(this),
            error : function(xhr, textStatus) {
                this.setState({error_password: 'password not matched'});
                console.log(xhr.status + '\n' + textStatus + '\n');
            }.bind(this)
        });
    };

    getAvator = () => {
        let avator_url = Auth.Avator;
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
                avator_url = data.avator_url;
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        return avator_url;
    };

    // 注册
    onSignUp = () => {
        const URL = API.SignUp;
        let data = {
            username : this.state.username,
            password : this.state.password,
            gender : this.state.sex
        };
        console.log(data);
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            data : JSON.stringify(data),
            dataType:'json',
            success : function(data, textStatus, jqXHR) {
                if(data.result) {
                    this.refs.dialog.setContent('Sign Up success!', 'You can sign in now.');
                    this.refs.dialog.handleOpen(false);
                    this.handleChange(0);
                }
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    selectSex = (event) => {
        this.setState({sex : event.target.value});
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
                                   onBlur={this.checkUsernameExist}
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
                                   id="signup"
                                   errorText={this.state.error_username}
                                   onBlur={this.checkUsernameNotExist}
                                   onChange={this.inputUsername}/>
                        <br/>
                        <TextField hintText="password"
                                   floatingLabelText="password"
                                   type="password"
                                   errorText={this.state.error_password}
                                   onChange={this.inputPassword}/>
                        <br/><br/>
                        <RadioButtonGroup name="sex" valueSelected={this.state.sex} onChange={this.selectSex} className="inputForm" style={styles.radioButtonGroup}>
                            <RadioButton
                                value="M"
                                label="Male"
                            />
                            <RadioButton
                                value="F"
                                label="Female"
                            />
                        </RadioButtonGroup>

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