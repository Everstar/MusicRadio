/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import {Card, CardHeader} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import $ from 'jquery';
import API from './API'

const styles = {
    chip: {
        textAlign: 'center',
        margin : '2% auto'
    },
    wrapper: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    autoWidth: {
        width : 'auto',
        maxWidth : '64px',
    }
};

const moment_data = [
    {
        avatar : 'dynamic/img/avatar.png',
        username : 'happyfarmergo',
        operation : 'create',
        songlist : 'drinkme',
    },
    {
        avatar : 'dynamic/img/avatar.png',
        username : 'happyfarmergo',
        operation : 'create',
        songlist : 'forgetYou',
    },
    {
        avatar : 'dynamic/img/avatar.png',
        username : 'happyfarmergo',
        operation : 'create',
        songlist : 'drinkme',
    },
    {
        avatar : 'dynamic/img/avatar.png',
        username : 'happyfarmergo',
        operation : 'liked',
        songlist : 'nothing',
    },
    {
        avatar : 'dynamic/img/avatar.png',
        username : 'happyfarmergo',
        operation : 'comment',
        songlist : 'goodbye',
    },
];


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
            moments : [],
        };
        console.log(this.props.params.id);
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
                this.setState({
                    username : data.username,
                    exp : data.exp,
                    exp_max : data.exp_max,
                    level : data.level,
                    gender : data.gender === 'M' ? 'Male' : 'Female',
                    ctr_songlist: data.ctr_songlist,
                    liked_songlist: data.liked_songlist,
                    following_num: data.friends_num,
                });
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    componentWillMount() {
        this.getUserInfo();
        // this.setState({moments : moment_data, username : this.props.params.id});
    };

    render() {
        return (
            <div style={{maxWidth: '835px', margin:'0 auto'}}>
                <Card containerStyle={{margin: 16}}
                      style={{background : 'transparent'}}>
                    <CardHeader
                        title={this.state.username}
                        subtitle={this.state.gender}
                        avatar="dynamic/img/avatar.png"
                        actAsExpander={true}
                    />
                    <div style={{width: '96%', margin:'10px'}}>
                        Lv{this.state.level}<LinearProgress mode="determinate" value={this.state.exp} max={this.state.exp_max} />
                    </div>
                    <List>
                        <ListItem primaryText="Created Song Lists" rightIcon={<TextField value={this.state.ctr_songlist} id="crt" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Liked Song Lists" rightIcon={<TextField value={this.state.liked_songlist} id="lik" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Friends" rightIcon={<TextField value={this.state.following_num} id="friend" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                    </List>
                </Card>
                {this.state.moments.map((moment, index) => (
                    <Chip
                        key={index}
                        style={styles.chip}
                    >
                        <Avatar src={moment.avatar} />
                        {moment.username} {moment.operation} songlist {moment.songlist}
                    </Chip>
                ))}
                <Divider style={{marginTop : '2%'}}/>
            </div>
        );
    }
}