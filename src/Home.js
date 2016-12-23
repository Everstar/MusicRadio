/**
 * Created by tsengkasing on 12/8/2016.
 */
import React from 'react';
import {Card, CardHeader,} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Paper from 'material-ui/Paper';
import Avatar from 'material-ui/Avatar';
import $ from 'jquery';
import Auth from './account/Auth';
import API from './API'

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

export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            avator_url : 'http://img.neverstar.top/default.jpg',
            exp: 50,
            exp_max : 100,
            level : 1,
            gender : "male",
            ctr_songlist : 16,
            liked_songlist : 64,
            following_num : 32,
            moments : [],
        };
    };

    //获取用户信息
    getUserInfo = () => {
        const URL = API.Info;
        $.ajax({
            url : URL,
            type : 'POST',
            contentType: 'application/json',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                this.setState({
                    avator_url : data.avator_url,
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

    changeAvatar = () => {
        console.log('changeAvatar');
    };

    getMoments = () => {
        const URL = API.Moment;
        $.ajax({
            url : URL,
            type : 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                this.setState({moments : data});
            }.bind(this),
            error : function(xhr, textStatus) {
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
                        title={Auth.username}
                        titleStyle={{fontSize:'3vh'}}
                        subtitle={this.state.gender}
                        subtitleStyle={{fontSize:'2vh'}}
                        avatar={<Avatar src={this.state.avator_url} size={100} onTouchTap={this.changeAvatar} />}
                    />
                    <div style={{width: '96%', margin:'0 auto'}}>
                        Lv{this.state.level}<LinearProgress mode="determinate" value={this.state.exp} max={this.state.exp_max} />
                    </div>
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