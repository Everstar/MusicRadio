/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import { hashHistory } from 'react-router'
import {Card, CardHeader, CardText} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Paper from 'material-ui/Paper';
import Avatar from 'material-ui/Avatar';
import $ from 'jquery';
import API from './API'
import Auth from './account/Auth';

const styles = {
    wrapper: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    autoWidth: {
        width : 'auto',
        maxWidth : '64px',
    },
    paper : {
        height: 128,
        maxWidth: '835px',
        marginBottom: 20,
        textAlign: 'center',
    },
};

export default class User extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user_id : this.props.params.id,
            username : '',
            exp: 0,
            exp_max : 100,
            level : 1,
            gender : "male",
            ctr_songlist : 0,
            liked_songlist : 0,
            following_num : 0,
            followed : false,
            moments : [],
            avator_url : 'http://img.neverstar.top/default.jpg',
        };
    };

    //获取用户信息
    getUserInfo = () => {
        const URL = API.Info;
        $.ajax({
            url : URL,
            type : 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                id : this.state.user_id
            },
            success : function(data, textStatus, jqXHR) {
                let avator_url = data.avator_url;
                if(avator_url != null) data.avator_url = avator_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
                if(avator_url != null) data.avator_url = avator_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
                this.setState({
                    username : data.username,
                    exp : data.exp,
                    exp_max : data.exp_max,
                    level : data.level,
                    gender : data.gender === 'M' ? 'Male' : 'Female',
                    ctr_songlist: data.ctr_songlist,
                    liked_songlist: data.liked_songlist,
                    following_num: data.friends_num,
                    followed : data.followed,
                    avator_url : data.avator_url,
                });
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    getMoments = () => {
        const URL = API.Moment;
        $.ajax({
            url : URL,
            type : 'GET',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                id : this.props.params.id
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                for(let i = 0; i < data.length; i++) {
                    let avator_url = data[i].avator_url;
                    if(avator_url === null) continue;
                    data[i].avator_url = avator_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
                    data[i].avator_url = avator_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
                };
                this.setState({moments : data});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    follow = () => {
        //如果未登录，跳转到登录页面
        if(Auth.username === null){ hashHistory.push('/sign'); return;}

        const URL = API.Follow;
        const data = {user_id : this.props.params.id};
        $.ajax({
            url : URL,
            type : 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                alert('success!');
            },
            error : function(xhr, textStatus) {
                if(xhr.status === 400)
                    hashHistory.push('/sign');
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    componentWillMount() {
        this.getUserInfo();
        this.getMoments();
    };

    render() {
        return (
            <div style={{maxWidth: '835px', margin:'0 auto'}}>
                <Card containerStyle={{margin: 16}}
                      style={{background : 'transparent'}}>
                    <CardHeader
                        title={this.state.username}
                        subtitle={this.state.gender}
                        avatar={this.state.avator_url}
                        actAsExpander={true}
                    />
                    <div style={{width: '96%', margin:'10px'}}>
                        Lv{this.state.level}<LinearProgress mode="determinate" value={this.state.exp} max={this.state.exp_max} />
                    </div>
                    <CardText>
                        <RaisedButton label={this.state.followed ? 'Followed' : 'Follow'} primary={true} onTouchTap={this.follow} disabled={this.state.followed}/>
                    </CardText>
                    <List>
                        <ListItem primaryText="Created Song Lists" rightIcon={<TextField value={this.state.ctr_songlist} id="crt" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Liked Song Lists" rightIcon={<TextField value={this.state.liked_songlist} id="lik" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Friends" rightIcon={<TextField value={this.state.following_num} id="friend" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                    </List>
                </Card>
                {this.state.moments.map((moment, index) => (
                    <Paper style={styles.paper} zDepth={1} key={index} >
                        <ListItem
                            primaryText={<p>{moment.username} <b>{moment.type.toUpperCase()}</b> {moment.songlist_name}</p>}
                            secondaryText={moment.time}
                            leftAvatar={<Avatar src={moment.avator_url} style={{left:'16%', top:'16px'}} size={50} />}
                        />
                    </Paper>
                ))}
                <Divider style={{marginTop : '2%'}}/>
            </div>
        );
    }
}