/**
 * Created by tsengkasing on 12/12/2016.
 */
import React from 'react';
import FlatButton from 'material-ui/FlatButton';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import TextField from 'material-ui/TextField';
import Dialog from 'material-ui/Dialog';
import SongListItem from './songList/SongListItem'
import API from './API';
import $ from 'jquery';

const styles = {
    floatingButton : {
        marginRight: 20,
        marginBottom : 20,
        position : 'fixed',
        right : 0,
        bottom : 0,
    },
};


//创建新乐单
class NewSongListDialog extends React.Component {
    state = {
        open: false,
        songlist_name : "",
        description : "",
        callback : null,
        error_text : null,
    };

    handleOpen = (callback) => {
        this.setState({
            open: true,
            callback : callback,
        });
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        const URL = API.NewList;
        if(this.state.songlist_name === "") {
            this.setState({error_text: "This field is required"});
            return;
        }else
            this.setState({error_text: null});
        let data = {
            songlist_name: this.state.songlist_name,
            description: this.state.description,
        };
        $.ajax({
            url : URL,
            type : 'POST',
            data : JSON.stringify(data),
            contentType: 'application/json;charset=UTF-8',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data.songlist_id);
                this.state.callback();
            }.bind(this),
            error : function(xhr, textStatus) {
                alert('fail');
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
        console.log('createlist \nname :' + this.state.songlist_name + '\ndescription : ' + this.state.description );
        this.handleClose();
    };

    inputName = (event) => {
        this.setState({
            songlist_name : event.target.value,
            error_text: null
        });
    };

    inputDescription = (event) => {
        this.setState({description : event.target.value});
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
                        hintText="Song List Name"
                        floatingLabelText="song list name"
                        value={this.state.songlist_name}
                        onChange={this.inputName}
                        errorText={this.state.error_text}
                    /><br />
                    <TextField
                        hintText="description"
                        floatingLabelText="Description"
                        multiLine={true}
                        rows={2}
                        value={this.state.description}
                        onChange={this.inputDescription}
                    />
                </Dialog>
            </div>
        );
    }
}


export default class ManageSongList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            song_lists : [],
        }
    };

    loadData = () => {
        const URL = API.SongList;
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            dataType:'json',
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                this.setState({song_lists : data});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    componentWillMount() {
        this.loadData();
    }

    refresh = () => {
        this.loadData();
        console.log('update');
    };

    deleteList = (index) => {
        const URL = API.DeleteList;
        let data = {id : index};
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            dataType:'json',
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                alert('删除成功');
                this.refresh();
            }.bind(this),
            error : function(xhr, textStatus) {
                alert('Fail');
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };


    newListQuery = () => {
        this.refs.newListDialog.handleOpen(this.refresh);
    };


    render() {
        return (
            <div style={{textAlign: 'center'}}>
                {this.state.song_lists.map((list, index) => (
                    <SongListItem
                        onDelete={this.deleteList}
                        order={index}
                        key={index}
                        title={list.songlist_name}
                        author={list.author}
                        img_id={list.img_id}
                        img_url={list.img_url}
                        liked={list.liked}
                        description={list.description}
                        songlist_id={list.list_id}
                        refresh={this.refresh}
                    />
                ))}
                <FloatingActionButton style={styles.floatingButton} onTouchTap={this.newListQuery}>
                    <ContentAdd />
                </FloatingActionButton>
                <NewSongListDialog ref="newListDialog" />
            </div>
        );
    };
}