/**
 * Created by tsengkasing on 12/12/2016.
 */
import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import {Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import TextField from 'material-ui/TextField';
import IconButton from 'material-ui/IconButton';
import StarBorder from 'material-ui/svg-icons/toggle/star-border';
import ContentRemoveCircleOutline from 'material-ui/svg-icons/content/remove-circle-outline';
import Dialog from 'material-ui/Dialog';
import AutoComplete from 'material-ui/AutoComplete';
import Toggle from 'material-ui/Toggle';
import API from './API';
import $ from 'jquery';

const styles = {
    cardSize : {
        maxWidth:'835px',
        textAlign : 'center',
        margin:'0 auto',
        marginTop: '20px',
        background : 'transparent',
    },
    table : {
        // minWidth: '835px',
        overflowX : 'scroll',
        background : 'transparent',
    },
    tableBody : {
        overflowX : 'auto',
    },
    tableFont : {
        fontSize:'1.5vh',
    },
    tableDur : {
        fontSize:'1.5vh',
    },
    imgSize: {
        maxWidth : '128px',
        maxHeight : '128px',
    },
    tableButton : {
    },
    toggle : {
        marginTop: 8,
    },
    floatingButton : {
        marginRight: 20,
        marginBottom : 20,
        position : 'fixed',
        right : 0,
        bottom : 0,
    },
};

const tableData = [
    {
        song_id : '1',
        song_name : 'All I need 1',
        author : 'I don\'t know',
        duration : 3.40,
        img_url : 'dynamic/img/5.jpg',
    },
    {
        song_id : '2',
        song_name : 'All I need 2',
        author : 'I don\'t know',
        duration : 3.40,
        img_url : 'dynamic/img/6.jpg',
    },
    {
        song_id : '3',
        song_name : 'All I need 3',
        author : 'I don\'t know',
        duration : 3.40,
        img_url : 'dynamic/img/7.jpg',
    },
];

//创建新乐单
class NewMusicListDialog extends React.Component {
    state = {
        open: false,
        songlist_name : "",
        description : "",

        error_text : null,
    };

    handleOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        const URL = API.NewList;
        if(this.state.songlist_name == "") {
            this.setState({error_text: "This field is required"});
            return;
        }else
            this.setState({error_text: null});

        $.ajax({
            url : URL,
            type : 'POST',
            data : {
                songlist_name: this.state.songlist_name,
                description: this.state.description,
            },
            success : function(data, textStatus, jqXHR) {
                data = $.parseJSON(data);
                if(data.songlist_id != null)
                    window.location.reload();
                else
                    alert('Fail');
            }.bind(this),
            error : function(xhr, textStatus) {
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
                keyboardFocused={true}
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

//增加音乐
class AddMusicItemDialog extends React.Component {
    state = {
        open: false,
        dataSource: [],

        imageImportWay : true,
        musicImportWay : true,
    };

    handleUpdateInput = (value) => {
        this.setState({
            dataSource: [
                value,
                value + value,
                value + value + value,
            ],
        });
    };

    handleOpen = () => {
        this.setState({open: true});
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        this.setState({open: false});
    };

    handleToggleImportSong = () => {
        this.setState({musicImportWay:!this.state.musicImportWay});
    };

    handleToggleImportImage = () => {
        this.setState({imageImportWay:!this.state.imageImportWay});
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
                    title="Add Music"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    <Toggle
                        label="Music from Netease/upload local song"
                        defaultToggled={true}
                        style={styles.toggle}
                        onToggle={this.handleToggleImportSong}
                    />
                    <AutoComplete
                        style={this.state.musicImportWay ? {visibility : 'visible'}:{visibility : 'collapse'}}
                        hintText="Search a song"
                        dataSource={this.state.dataSource}
                        onUpdateInput={this.handleUpdateInput}
                    /><input
                        style={this.state.musicImportWay ? {visibility : 'collapse'}:{visibility : 'visible'}}
                        placeholder="Image Url"
                        type="file"
                    />
                    {/*hintText="upload local song"*/}
                    {/*floatingLabelText="Image Url"*/}
                    <br />
                    <br />
                    <Toggle
                        label="image from internet url/upload local image"
                        defaultToggled={true}
                        style={styles.toggle}
                        onToggle={this.handleToggleImportImage}
                    />
                    <TextField
                        style={this.state.imageImportWay ? {visibility : 'visible'}:{visibility : 'collapse'}}
                        hintText='paste a full size url of the image'
                        type='text'
                    /><input
                        style={this.state.imageImportWay ? {visibility : 'collapse'}:{visibility : 'visible'}}
                        type="file"
                    />
                    <br />
                </Dialog>
            </div>
        );
    }
}

//展示乐单
class MusicListItem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            expanded: false,
            song_list : [],
        };
    }

    componentWillMount() {
        this.setState({song_list : tableData});
    }

    handleExpandChange = (expanded) => {
        this.setState({expanded: expanded});
    };

    addItem = () => {
        console.log('add a music');
        this.refs.addItemDialog.handleOpen();
    };

    removeItem = (event) => {
        console.log('remove' + event.target.parentNode.parentNode.parentNode.getAttribute('key'));
        this.state.song_list.splice(parseInt(event.target.parentNode.parentNode.parentNode.getAttribute('order')), 1);
        this.setState({song_list:this.state.song_list});
        console.log(event.target.parentNode.parentNode.parentNode.getAttribute('alt'));
    };

    changeImg = (event) => {
        console.log('change Img');
        console.log(event.target.getAttribute('alt'));
    };

    deleteList = () => {
        console.log('delete List' + this.props.order);
        this.props.onDelete(this.props.order);
    };

    playAll = () => {
        window.open('/radio.html');
    };

    like = () => {
        console.log('like!');
    };

    render() {
        return (
            <Card expanded={this.state.expanded} onExpandChange={this.handleExpandChange} style={styles.cardSize}>
                <CardHeader
                    title={this.props.title}
                    subtitle={'by ' + this.props.author}
                    avatar={<IconButton onTouchTap={this.like}><StarBorder color={this.props.stared ? "yellow" : "black"} /></IconButton>}
                    actAsExpander={true}
                    showExpandableButton={true}
                />
                <CardText>
                </CardText>
                <CardMedia
                    expandable={true}
                    overlay={<CardTitle title={this.props.title} subtitle={this.props.author} />}
                >
                    <img src={this.props.img_url} />
                </CardMedia>
                {/*<CardTitle title="Card title" subtitle="Card subtitle" expandable={true} />*/}
                <Table
                    expandable={true}
                    selectable={false}
                    style={styles.table}
                    bodyStyle={styles.tableBody}
                >
                    <TableHeader
                        displaySelectAll={false}
                        adjustForCheckbox={false}
                    >
                        <TableRow>
                            <TableHeaderColumn colSpan="5" tooltip="Song Information" style={{textAlign: 'center'}}>
                                Song Information
                            </TableHeaderColumn>
                        </TableRow>
                        <TableRow>
                            <TableHeaderColumn tooltip="song_name">Song</TableHeaderColumn>
                            <TableHeaderColumn tooltip="author">Author</TableHeaderColumn>
                            <TableHeaderColumn tooltip="duration">Duration</TableHeaderColumn>
                            <TableHeaderColumn tooltip="img_url">Image</TableHeaderColumn>
                            <TableHeaderColumn tooltip="modify" style={styles.tableButton}>Modify</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody
                        showRowHover={true}
                        displayRowCheckbox={false}>
                        {this.state.song_list.map((row, index) => (
                            <TableRow key={index}>
                                <TableRowColumn style={styles.tableFont}>{row.song_name}</TableRowColumn>
                                <TableRowColumn style={styles.tableFont}>{row.author}</TableRowColumn>
                                <TableRowColumn style={styles.tableDur}>{row.duration}</TableRowColumn>
                                <TableRowColumn><img style={styles.imgSize} src={row.img_url} onClick={this.changeImg} alt={row.song_id}/></TableRowColumn>
                                <TableRowColumn style={styles.tableButton}>
                                    <IconButton onTouchTap={this.removeItem} alt={row.song_id} key={index}><ContentRemoveCircleOutline /></IconButton>
                                </TableRowColumn>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
                <CardText expandable={true}>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
                    Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
                    Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
                </CardText>
                <CardActions>
                    <FlatButton label="Add" onTouchTap={this.addItem} />
                    <FlatButton label="Delete" onTouchTap={this.deleteList} />
                    <FlatButton label="PlayAll" onTouchTap={this.playAll} />
                </CardActions>
                <AddMusicItemDialog ref="addItemDialog"/>
            </Card>
        );
    }
}

const listItem = [
    {
        title : "Water Fall",
        author : "Everstar",
        img_url : 'dynamic/img/0.jpg',
        stared : true,
    },
    {
        title : "Summer Day",
        author : "happyfarmer",
        img_url : 'dynamic/img/19.jpg',
        stared : false,
    },
    {
        title : "Tomorrow",
        author : "captain",
        img_url : 'dynamic/img/12.jpg',
        stared : false,
    },
    {
        title : "Good bye",
        author : "dragon",
        img_url : 'dynamic/img/9.jpg',
        stared : false,
    },
];

export default class MusicList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            song_lists : listItem,
        }
    };

    deleteList = (index) => {
        this.state.song_lists.splice(index, 1);
        this.setState({song_lists:this.state.song_lists});
    };


    newListQuery = () => {
        this.refs.newListDialog.handleOpen();
    };


    render() {
        return (
            <div style={{textAlign: 'center'}}>
                {this.state.song_lists.map((list, index) => (
                    <MusicListItem
                        onDelete={this.deleteList}
                        order={index}
                        key={index}
                        title={list.title}
                        author={list.author}
                        img_url={list.img_url}
                        stared={list.stared}
                    />
                ))}
                <FloatingActionButton style={styles.floatingButton} onTouchTap={this.newListQuery}>
                    <ContentAdd />
                </FloatingActionButton>
                <NewMusicListDialog ref="newListDialog" />
            </div>
        );
    };
}