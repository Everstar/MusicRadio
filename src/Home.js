/**
 * Created by tsengkasing on 12/8/2016.
 */
import React from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import {Card, CardHeader,} from 'material-ui/Card';
import { hashHistory } from 'react-router'
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import Subheader from 'material-ui/Subheader';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import {GridList, GridTile} from 'material-ui/GridList';
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
        maxWidth: '1024px',
        marginBottom: 20,
        textAlign: 'center',
    },
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
    },
    gridList: {
        display: 'flex',
        flexWrap: 'nowrap',
        overflowX: 'auto',
        textAlign : 'left',
    },
    titleStyle: {
        color: 'rgb(0, 188, 212)',
    },
    imageInput: {
        cursor: 'pointer',
        position: 'absolute',
        top: 0,
        bottom: 0,
        right: 0,
        left: 0,
        width: '100%',
        opacity: 0,
    },
};


class UploadAvator extends React.Component {
    state = {
        open: false,
    };

    handleOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        $('#uploadAvator').click();
        this.setState({open: false});
    };

    render() {
        const actions = [
            <FlatButton
                label="取消"
                primary={true}
                onTouchTap={this.handleClose}
            />,
            <FlatButton
                label="确定"
                primary={true}
                onTouchTap={this.handleSubmit}
            />,
        ];

        return (
            <div>
                <Dialog
                    title="Upload a Avator"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    <form id="changeAvator" method="post" encType="multipart/form-data" action={API.Avatar} target="uploadFrame">
                        <FlatButton label="Choose an Image" labelPosition="before">
                            <input type="file" style={styles.imageInput} name="image_file" />
                        </FlatButton>
                        <input type="submit" value="upload" id="uploadAvator" style={{visibility : 'collapse'}}/>
                    </form>
                </Dialog>
            </div>
        );
    }
}

export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            avator_url : 'http://img.neverstar.top/default.jpg',
            exp: 0,
            exp_max : 100,
            level : 1,
            gender : "male",
            ctr_songlist : 0,
            liked_songlist : 0,
            following_num : 0,
            moments : [],

            recommend_user_list : [],

            recommend_song_list : [],
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
        this.refs.UploadAvatorDialog.handleOpen();
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

    getNeteaseSonginfo = (song_id) => {
        const URL = API.SongInfo;
        let music_info = {};
        $.ajax({
            url : URL,
            async : false,
            type : 'GET',
            headers : {
                target : 'api'
            },
            data : {
                id : song_id,
            },
            success : function(data, textStatus, jqXHR) {
                music_info = data;
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        return music_info;
    };

    getLocalSongInfo = (song_id) => {
        let song_info;
        const URL = API.LocalSongInfo;
        $.ajax({
            url : URL,
            type : 'GET',
            async : false,
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                id : song_id,
            },
            success : function(data, textStatus, jqXHR) {
                let avator_url = data.avator_url;
                if(avator_url != null) data.avator_url = avator_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
                if(avator_url != null) data.avator_url = avator_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
                song_info = data;
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });

        if(song_info.netease_id !== 0) {
            song_info = this.getNeteaseSonginfo(song_info.netease_id);
        }else{
            song_info.song_artists = song_info.artists;
        }

        return song_info;
    };

    getRecommendSongs = () => {
        const URL = API.RecommendSong;
        $.ajax({
            url : URL,
            type : 'GET',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                data : 1,
            },
            success : function(data, textStatus, jqXHR) {
                let detail_list = [];
                for(let i = 0; i < data.length; i++) {
                    let info = this.getLocalSongInfo(data[i]);
                    info.song_id = data[i];
                    detail_list.push(info);
                }
                console.log(detail_list);
                this.setState({recommend_song_list : detail_list});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    getRecommendUserInfo = (user_id) => {
        let info = {};
        const URL = API.Info;
        $.ajax({
            url : URL,
            type : 'GET',
            async : false,
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                id : user_id,
            },
            success : function(data, textStatus, jqXHR) {
                info = data;
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        return info;
    };

    getRecommendUsers = () => {
        const URL = API.RecommendUser;
        $.ajax({
            url : URL,
            type : 'GET',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : {
                data : 1,
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                let detail_list = [];
                for(let i = 0; i < data.length; i++) {
                    detail_list.push(this.getRecommendUserInfo(data[i]));
                }
                console.log(detail_list);
                this.setState({recommend_user_list : detail_list});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    onClickRecommendSong = (event) => {
        window.open('/radio.html?type=song&song_id=' + event.target.getAttribute('about'));
    };

    onClickRecommendUser = (event) => {
        hashHistory.push('/user/' + event.target.getAttribute('about'));
    };

    componentWillMount() {
        this.getUserInfo();
    };

    componentDidMount() {
        this.getMoments();
        this.getRecommendSongs();
        this.getRecommendUsers();
    };

    render() {

        let avator_url = this.state.avator_url;
        if(avator_url !== null) avator_url = avator_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
        if(avator_url !== null) avator_url = avator_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");

        return (
            <div style={{maxWidth: '1024px', margin:'0 auto'}}>
                <Card containerStyle={{margin: 16}}
                      style={{background : 'transparent'}}>
                    <CardHeader
                        title={Auth.username}
                        titleStyle={{fontSize:'3vh'}}
                        subtitle={this.state.gender}
                        subtitleStyle={{fontSize:'2vh'}}
                        avatar={<Avatar src={avator_url} size={100} onTouchTap={this.changeAvatar} />}
                    />
                    <div style={{width: '96%', margin:'0 auto'}}>
                        Lv{this.state.level}<LinearProgress mode="determinate" value={this.state.exp} max={this.state.exp_max} />
                    </div>
                    <List>
                        <ListItem primaryText="Created Song Lists" rightIcon={<TextField value={this.state.ctr_songlist} id="crt" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Liked Song Lists" rightIcon={<TextField value={this.state.liked_songlist} id="lik" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Following" rightIcon={<TextField value={this.state.following_num} id="friend" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                    </List>
                </Card>
                <Divider style={{marginTop : '2%'}}/>
                {/*歌曲推荐*/}
                <Subheader>Today's Songs Recommend</Subheader>
                <div style={styles.root}>
                    <GridList style={styles.gridList} cols={2.2}>
                        {this.state.recommend_song_list.map((song, index) => (
                            <GridTile
                                key={index}
                                cols={index}
                                title={song.song_name}
                                titleStyle={styles.titleStyle}
                                subtitle={song.song_artists}
                                titleBackground="linear-gradient(to top, rgba(0,0,0,0.7) 0%,rgba(0,0,0,0.3) 70%,rgba(0,0,0,0) 100%)"
                            >
                                <img src={'http://img.neverstar.top/' + index + '.jpg'} alt="居然找不到图片" onClick={this.onClickRecommendSong} about={song.song_id}
                                     style={this.state.recommend_song_list.length > 1 ? null : {width:'100%', height:'auto'}} />
                            </GridTile>
                        ))}

                    </GridList>
                </div>
                <Divider style={{marginTop : '2%'}}/>
                {/*用户推荐*/}
                <Subheader>Today's Users Recommend</Subheader>
                <div style={styles.root}>
                    <GridList style={styles.gridList} cols={2.2}>
                        {this.state.recommend_user_list.map((user, index) => (
                            <GridTile
                                style={{display : user.avator_url ? 'block' : 'none'}}
                                cols={index}
                                key={index}
                                title={user.username}
                                titleStyle={styles.titleStyle}
                                subtitle={'level ' + user.level}
                                titleBackground="linear-gradient(to top, rgba(0,0,0,0.7) 0%,rgba(0,0,0,0.3) 70%,rgba(0,0,0,0) 100%)"
                            >
                                <img src={user.avator_url} alt="居然找不到头像" onClick={this.onClickRecommendUser} about={user.id}
                                     style={this.state.recommend_user_list.length > 2 ? null : {width:'100%', height:'auto'}} />
                            </GridTile>
                        ))}
                    </GridList>
                </div>
                <Divider style={{marginTop : '2%'}}/>

                <Subheader>Your following's moment</Subheader>
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
                <UploadAvator ref="UploadAvatorDialog" />
            </div>
        );
    }
}