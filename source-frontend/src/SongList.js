/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import Divider from 'material-ui/Divider';
import Paper from 'material-ui/Paper';
import {ListItem} from 'material-ui/List';
import ContentAdd from 'material-ui/svg-icons/content/add';
import TextField from 'material-ui/TextField';
import Dialog from 'material-ui/Dialog';
import AvPlayCircleOutline from 'material-ui/svg-icons/av/play-circle-outline';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAddCircleOutline from 'material-ui/svg-icons/content/add-circle-outline';
import $ from 'jquery';
import Avatar from 'material-ui/Avatar';
import API from './API';

const styles = {
    cardSize : {
        maxWidth:'1280px',
        textAlign : 'center',
        margin:'0 auto',
        marginTop: '20px',
        background : 'transparent',
    },
    toggle : {
        marginTop: 8,
    },
    tableBody : {
        overflowX : 'auto',
    },
    tableFont : {
        fontSize:'2vh',
    },
    tableDur : {
        fontSize:'2vh',
    },
    imgSize: {
        maxWidth : '128px',
        maxHeight : '128px',
    },
    table : {
        // minWidth: '835px',
        overflowX : 'scroll',
        background : 'transparent',
    },
    tableButton : {
    },
    paper : {
        paddingTop : 20,
        paddingBottom : 20,
        maxWidth: '1280px',
        margin: '10px auto',
        textAlign: 'center',
    },
    floatingButton : {
        marginRight: 20,
        marginBottom : 20,
        position : 'fixed',
        right : 0,
        bottom : 0,
    },
};

//添加新评论
class NewCommentDialog extends React.Component {
    state = {
        open: false,
        songlist_id : 0,
        content : "",
        callback : null,
        error_text : null,
    };

    handleOpen = (callback, songlist_id) => {
        this.setState({
            open: true,
            songlist_id : songlist_id,
            callback : callback,
        });
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        const URL = API.AddComment;
        if(this.state.content === "") {
            this.setState({error_text: "This field is required"});
            return;
        }else
            this.setState({error_text: null});
        let data = {
            songlist_id: parseInt(this.state.songlist_id, 10),
            content: this.state.content,
        };
        console.log(data);
        $.ajax({
            url : URL,
            type : 'POST',
            data : JSON.stringify(data),
            contentType: 'application/json;charset=UTF-8',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log('comment_id : ' + data.comment_id);
                this.state.callback();
            }.bind(this),
            error : function(xhr, textStatus) {
                alert('fail');
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        this.handleClose();
    };

    inputContent = (event) => {
        this.setState({
            content : event.target.value,
            error_text: null
        });
    };

    render() {
        const actions = [
            <FlatButton
                label="Cancel"
                primary={true}
                onTouchTap={this.handleClose}
            />,
            <FlatButton
                label="Submit"
                primary={true}
                onTouchTap={this.handleSubmit}
            />,
        ];

        return (
            <div>
                <Dialog
                    title="Create song list"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    <TextField
                        hintText="Comment Content"
                        floatingLabelText="Comment"
                        value={this.state.content}
                        onChange={this.inputContent}
                        errorText={this.state.error_text}
                    />
                </Dialog>
            </div>
        );
    }
}

export default class SongList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            song_lists : [],
            title : '',
            author : '',
            stared : 'black',
            description : '',
            expanded : true,
            comments : [],
            img_url : '',
        }
    };

    loadData = () => {
        const URL = API.GetSongsOfSongList;
        $.ajax({
            url : URL,
            type : 'GET',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json',
            dataType: 'json',
            data : {
                id : this.props.params.id,
            },
            success : function(data, textStatus, jqXHR) {
                this.setState({song_lists: data});
                console.log(this.state.song_lists);
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    loadComments = () => {
        const URL = API.GetComments;
        $.ajax({
            url : URL,
            type : 'GET',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json',
            dataType: 'json',
            data : {
                id : this.props.params.id,
            },
            success : function(data, textStatus, jqXHR) {
                this.setState({comments: data});
                console.log(this.state.song_lists);
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    handleExpandChange = () => {
        this.setState({expanded : !this.state.expanded});
    };

    componentWillMount() {
        console.log(this.state.song_lists);
        this.loadSongListImage();
        this.loadData();
        this.loadComments();
    }

    loadSongListImage = () => {
        const URL = API.ImageInfo;
        $.ajax({
            url : URL,
            type : 'GET',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json',
            dataType: 'json',
            data : {
                id : this.props.params.img_id,
            },
            success : function(data, textStatus, jqXHR) {
                this.setState({img_url: data.imgUrl});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    playAll = () => {
        window.open('/radio.html?type=songlist&songlist_id=' + this.props.params.id);
    };

    newComment = () => {
        this.refs.newComment.handleOpen(this.loadComments, this.props.params.id);
    };

    addTOFavorite = (event) => {
        const index = event.target.parentNode.parentNode.parentNode.id;
        const URL = API.LikeSong;
        const data = {
            songlist_id : this.props.params.id,
            song_id : this.state.song_lists[index].song_id,
        };
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json',
            dataType: 'json',
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
            },
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    playSingle = (event) => {
        const index = event.target.parentNode.parentNode.parentNode.getAttribute('alt');
        if(index === undefined || index === null) return;
        console.log(this.state.song_lists[index]);
        const song = this.state.song_lists[index];
        window.open('/radio.html?type=song&song_id=' + song.song_id + '&img_id=' + song.image_id);
    };

    render() {
        return (
            <div>
                <Card expanded={this.state.expanded} onExpandChange={this.handleExpandChange} style={styles.cardSize}>
                    <CardHeader
                        title={this.props.params.songlist_name}
                        subtitle={'by ' + this.props.params.author}
                        actAsExpander={true}
                        showExpandableButton={true}
                    />
                    <CardText>
                    </CardText>
                    <CardMedia
                        expandable={true}
                        overlay={<CardTitle title={this.state.title} subtitle={this.state.author} />}
                    >
                        <img src={this.state.img_url} alt="居然找不到图片" />
                    </CardMedia>
                    <Table
                        expandable={true}
                        selectable={false}
                        style={styles.table}
                        bodyStyle={styles.tableBody}
                    >
                        <TableHeader
                            displaySelectAll={false}
                            adjustForCheckbox={false}
                            style={{textAlign:'center'}}
                        >
                            <TableRow>
                                <TableHeaderColumn colSpan="5" tooltip="Song Information" style={{textAlign: 'center'}}>
                                    Song Information
                                </TableHeaderColumn>
                            </TableRow>
                            <TableRow>
                                <TableHeaderColumn tooltip="play">play</TableHeaderColumn>
                                <TableHeaderColumn tooltip="song_name">Song</TableHeaderColumn>
                                <TableHeaderColumn tooltip="author">Author</TableHeaderColumn>
                                <TableHeaderColumn tooltip="duration">Duration</TableHeaderColumn>
                                <TableHeaderColumn tooltip="addToMine">AddToMyFavorite</TableHeaderColumn>
                            </TableRow>
                        </TableHeader>
                        <TableBody
                            showRowHover={true}
                            displayRowCheckbox={false}>
                            {this.state.song_lists.map((row, index) => (
                                <TableRow key={index}>
                                    <TableRowColumn style={styles.tableButton}>
                                        <IconButton onTouchTap={this.playSingle} alt={index}><AvPlayCircleOutline/></IconButton>
                                    </TableRowColumn>
                                    <TableRowColumn style={styles.tableFont}>{row.song_name}</TableRowColumn>
                                    <TableRowColumn style={styles.tableFont}>{row.artists}</TableRowColumn>
                                    <TableRowColumn style={styles.tableDur}>{(row.duration / 60000).toFixed(0) + '分' + ((row.duration / 1000) % 60).toFixed(0) + '秒'}</TableRowColumn>
                                    <TableRowColumn >
                                        <IconButton onTouchTap={this.addTOFavorite} id={index}><ContentAddCircleOutline/></IconButton>
                                    </TableRowColumn>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                    <CardText expandable={true}>
                        {this.state.description}
                    </CardText>
                    <CardActions>
                        <RaisedButton label="PlayAll" primary={true} onTouchTap={this.playAll} />
                        {/*<RaisedButton label="Share" primary={true} />*/}
                    </CardActions>
                </Card>
                <Divider style={{marginTop : '2%'}}/>
                <div style={{marginTop : 32, marginBottom: 32}}>
                    {this.state.comments.map((comment, index) => (
                        <Paper style={styles.paper} zDepth={2} key={index} >
                            <ListItem
                                primaryText={<p>{comment.content}</p>}
                                secondaryText={comment.time}
                                leftAvatar={<Avatar src={comment.avator_url} style={{left:'16%', top:'16px'}} size={50} />}
                            />
                        </Paper>
                    ))}
                </div>
                <FloatingActionButton style={styles.floatingButton} onTouchTap={this.newComment}>
                    <ContentAdd />
                </FloatingActionButton>
                <NewCommentDialog ref="newComment" />
            </div>
        );
    }
}