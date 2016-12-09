/**
 * Created by kevin on 12/8/2016.
 */
import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import ActionInfo from 'material-ui/svg-icons/action/info';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import $ from 'jquery';
import Auth from './account/Auth';
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


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            exp: 50,
            exp_max : 100,
            level : 1,
            gender : "male",
            ctr_songlist : 16,
            liked_songlist : 64,
            friends_num : 32,
        };
    };

    //获取用户信息
    getUserInfo = () => {
        const URL = API.Info;
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
                this.setState({
                    exp : data.exp,
                    exp_max : data.exp_max,
                    level : data.level,
                    gender : data.gender == 'M' ? 'Male' : 'Female',
                    ctr_songlist: data.ctr_songlist,
                    liked_songlist: data.liked_songlist,
                    friends_num: data.friends_num,
                });
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    componentWillMount() {
        //this.getUserInfo();
    };

    render() {
        return (
            <div style={{maxWidth: '535px', margin:'0 auto'}}>
                <Card containerStyle={{margin: 16}}>
                    <CardHeader
                        title={Auth.username}
                        subtitle={this.state.gender}
                        avatar="img/profile_1.png"
                        actAsExpander={true}
                    />
                    <div style={{width: '96%', margin:'0 5% 0 5%'}}>Lv{this.state.level}<LinearProgress mode="determinate" value={this.state.exp} max={this.state.exp_max}  /></div>l
                    <List>
                        <ListItem primaryText="Created Song Lists" rightIcon={<TextField value={this.state.ctr_songlist} id="crt" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Liked Song Lists" rightIcon={<TextField value={this.state.liked_songlist} id="lik" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Friends" rightIcon={<TextField value={this.state.friends_num} id="friend" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                    </List>
                </Card>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo create songlist drinkMe
                </Chip>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo create songlist forgetYou
                </Chip>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo liked songlist nothing
                </Chip>                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo commented songlist goodbye
                </Chip>
                <Divider style={{marginTop : '2%'}}/>
            </div>
        );
    }
}