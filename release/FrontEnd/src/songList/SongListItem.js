/**
 * Created by tsengkasing on 12/16/2016.
 */

import React from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import Checkbox from 'material-ui/Checkbox';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import AutoComplete from 'material-ui/AutoComplete';
import IconButton from 'material-ui/IconButton';
import TextField from 'material-ui/TextField';
import StarBorder from 'material-ui/svg-icons/toggle/star-border';
import ContentRemoveCircleOutline from 'material-ui/svg-icons/content/remove-circle-outline';
import AvPlayCircleOutline from 'material-ui/svg-icons/av/play-circle-outline';
import Toggle from 'material-ui/Toggle';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import $ from 'jquery'
import API from '../API'

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
    checkbox : {
        marginBottom : 16,
        float :'left',
        width : 200,
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

//编辑乐单信息
class EditSongListDialog extends React.Component {
    state = {
        open: false,
        songlist_id : '',
        songlist_name : "",
        description : "",
        image_url : '',
        imageImportWay : false,
        error_text : null,
    };

    handleOpen = (id, name, description, img_id) => {
        this.setState({
            open: true,
            songlist_id : id,
            songlist_name : name,
            description : description,
            img_id : img_id
        });
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        if(this.state.songlist_name === "") {
            this.setState({error_text: "This field is required"});
            return;
        }else
            this.setState({error_text: null});
        $('#uploadSonglistImage').click();
        console.log('createlist \nname :' + this.state.songlist_name + '\ndescription : ' + this.state.description );
        this.handleClose();
        window.setTimeout(this.props.refresh, 3000);
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

    inputImageUrl = (event) => {
        this.setState({image_url : event.target.value});
    };

    handleToggle = () => {
        this.setState({imageImportWay : !this.state.imageImportWay});
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
                    title="Edit song list"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    <Toggle
                        label="Image Url/upload local image"
                        defaultToggled={false}
                        style={styles.toggle}
                        onToggle={this.handleToggle}
                    />
                    <form name="changelist" action={API.ChangeList} method="post" encType="multipart/form-data" target="uploadFrame">
                    <TextField
                        type="text"
                        name="songlist_name"
                        hintText="Song List Name"
                        floatingLabelText="song list name"
                        value={this.state.songlist_name}
                        onChange={this.inputName}
                        errorText={this.state.error_text}
                    /><br />
                    <TextField
                        name="description"
                        type="text"
                        hintText="description"
                        floatingLabelText="Description"
                        multiLine={true}
                        rows={2}
                        rowsMax={6}
                        value={this.state.description}
                        onChange={this.inputDescription}
                        style={{width:'100%'}}
                    />

                        <input type="number" value={this.state.songlist_id} style={{display:'none'}} name="songlist_id" />
                        <input type="number" value={this.state.img_id} name="image_id" style={{display:'none'}} />
                        <TextField
                            style={{display : this.state.imageImportWay ? 'none' : 'block'}}
                            name="image_url"
                            type="text"
                            hintText="image_url"
                            floatingLabelText="image_url"
                            value={this.state.image_url}
                            onChange={this.inputImageUrl}
                        />
                        <FlatButton label="Choose an Image" labelPosition="before" style={{display : this.state.imageImportWay ? 'block' : 'none'}}>
                            <input type="file" name="image_file" style={styles.imageInput} />
                        </FlatButton>
                        <input type="submit" id="uploadSonglistImage" value="upload"  style={{display:'none'}}/>
                    </form>
                </Dialog>
            </div>
        );
    }
}

class ChangeMusicImage extends React.Component {
    state = {
        open: false,
        imageImportWay : false,
        songlist_id : '',
        song_id : '',
        img_id : '',

        image_url : "",

        error_text :null,
    };

    handleOpen = (songlist_id, song_id, img_id) => {
        this.setState({
            open: true,
            songlist_id : songlist_id,
            song_id : song_id,
            img_id : img_id,
        });
        console.log(songlist_id + "/" + song_id + '/' + img_id);
    };

    handleClose = () => {
        this.setState({open: false});
    };

    inputImageUrl =(event) => {
        this.setState({
            image_url : event.target.value,
            error_text : null,
        });
    };

    handleSubmit = () => {

        if(!this.state.imageImportWay) {
            if(this.state.image_url === '') {
                this.setState({error_text : 'This field is required!'});
                return;
            }
        }

        $('#changeSongImg').click();

        this.setState({open: false});
        window.setTimeout(this.props.refresh, 3000);
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
                    title="Select Image"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    <Toggle
                        label="image from internet url/upload local image"
                        defaultToggled={false}
                        style={styles.toggle}
                        onToggle={this.handleToggleImportImage}
                    />
                    <form id="upload_image" method="post" encType="multipart/form-data" action={API.ChangeSongImage} target="uploadFrame">
                        <TextField
                            name="image_url"
                            style={this.state.imageImportWay ? {visibility : 'collapse'}:{visibility : 'visible'}}
                            floatingLabelText="Image Url"
                            value={this.state.image_url}
                            errorText={this.state.error_text}
                            onChange={this.inputImageUrl}
                            type='text'/>
                        <input type="hidden" value={this.state.img_id} name="image_id" />
                        <input type="hidden" value={this.state.song_id} name="song_id"/>
                        <input type="hidden" value={this.state.songlist_id} name="songlist_id"/>
                        <FlatButton label="Choose an Image" labelPosition="before" style={{display : this.state.imageImportWay ? 'block' : 'none'}}>
                            <input
                                style={styles.imageInput}
                                name="image_file"
                                type="file"
                                accept='image/*'
                            />
                        </FlatButton>
                        <input
                            type="submit" id="changeSongImg" value="Upload"
                            style={{visibility : 'collapse'}}
                        />
                        <br />
                    </form>
                </Dialog>
            </div>
        );
    }
}

//搜索回来的音乐设置
const dataSourceConfig = {
    text: 'textKey',
    value: 'valueKey',
};

const language_items = [
    <MenuItem value={'zh-cn'} key={0} primaryText={'华语'} />,
    <MenuItem value={'en-us'} key={1} primaryText={'英语'} />,
    <MenuItem value={'ja-jp'} key={2} primaryText={'日语'} />,
    <MenuItem value={'fr-fr'} key={3} primaryText={'法语'} />,
    <MenuItem value={'ko-kr'} key={4} primaryText={'韩语'} />,
];

//增加音乐
class AddMusicItemDialog extends React.Component {

    state = {
        open: false,
        dataSource: [],
        songlist_id : null,
        musicImportWay : false,

        searchText : "",

        language : '',
        styles_binary : '000000000000000000000',
        styles_array : ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '0', '0', '0', '0', '0'],
        styles_source : [false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false],

        song_name : '',
        song_artists : '',
        mp3Url : '',
        duration : '',

        song_netease_id : "",
        error_text : null,
    };

    handleUpdateInput = (value) => {
        const URL = API.SearchhSong;
        $.ajax({
            url : URL,
            async : false,
            type : 'GET',
            headers : {
                target : 'api'
            },
            data : {
                key : value,
                num : 10,
            },
            success : function(data, textStatus, jqXHR) {
                this.setState({
                    error_text : null,
                    dataSource: data.map((song)=> (
                        {
                            textKey :
                                song.song_name + "|Artists:" + song.author + "|Duration: " + (song.duration / 60000).toFixed(0) + "m" +
                                ((song.duration / 1000) % 60).toFixed(0) + "s",
                            valueKey: song.song_id,
                            duration : song.duration,
                            name : song.song_name,
                        }
                    )),
                });
                console.log(data);
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });

    };

    handleOpen = (songlist_id) => {
        this.setState({
            open: true,
            songlist_id: songlist_id
        });
    };

    handleClose = () => {
        this.setState({open: false});
    };

    handleSubmit = () => {
        if(!this.state.musicImportWay) {
            if(this.state.song_netease_id === '') {
                this.setState({error_text : 'This field is required'});
                return;
            }
            $('#AddNeteaseSong').click();
        }else {
            $('#uploadLocalSong').click();
        }


        //console.log('song_id : ' + $("[name='uploadFrame']").html());
        this.setState({open: false});
        console.log('add update');
        window.setTimeout(this.props.refresh, 3000);
    };

    handleToggleImportSong = () => {
        this.setState({
            musicImportWay:!this.state.musicImportWay
        });
    };

    GetSongDetail = () => {
        const URL = API.SongInfo;
        $.ajax({
            url : URL,
            async : false,
            type : 'GET',
            headers : {
                target : 'api'
            },
            data : {
                id : this.state.song_netease_id,
            },
            success : function(data, textStatus, jqXHR) {
                this.setState({
                    mp3Url : data.mp3Url,
                    song_artists : data.song_artists,
                    song_name : data.song_name,
                });
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    handleMenuClose = (chosenRequest, index) => {
        console.log(chosenRequest);
        console.log('searchText' + this.state.searchText + 'index:' + index);
        this.setState({
            song_netease_id: chosenRequest.valueKey,
            searchText : chosenRequest.name,
            duration : chosenRequest.duration,
        });
        console.log(this.state.song_netease_id);
        this.GetSongDetail();
        console.log(this.state);
    };

    handleChangeLanguage = (event, index, value) => {
        this.setState({language : value});
    };

    handleChangeStyle = (event, isInputChecked) => {
        let checked = this.state.styles_source;
        let style = this.state.styles_array;
        let position = parseInt(event.target.id, 10);
        checked[position] = isInputChecked;
        style[position] = isInputChecked ? '1' : '0';
        this.setState({
            styles_source : checked,
            styles_array : style,
            styles_binary: style.join(''),
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
                    title="Add Music"
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                    autoScrollBodyContent={true}
                >
                    <Toggle
                        label="Music from Netease/upload local song"
                        defaultToggled={false}
                        style={styles.toggle}
                        onToggle={this.handleToggleImportSong}
                    />
                    <AutoComplete
                        style={this.state.musicImportWay ? {visibility : 'collapse'}:{visibility : 'visible'}}
                        fullWidth={true}
                        hintText="Search a song"
                        dataSource={this.state.dataSource}
                        onUpdateInput={this.handleUpdateInput}
                        searchText={this.state.searchText}
                        errorText={this.state.error_text}
                        dataSourceConfig={dataSourceConfig}
                        onNewRequest={this.handleMenuClose}
                        maxSearchResults={10}
                    />
                    <form id="Uploadsong" method="post" encType="multipart/form-data"
                          style={this.state.musicImportWay ? {visibility : 'visible', textAlign:'center'}:{visibility : 'collapse'}}
                          action={API.AddSongFromUpload} target="uploadSongFrame"
                    >
                        <FlatButton label="Choose a Song" labelPosition="before" style={{display : this.state.musicImportWay ? 'block' : 'none'}}>
                            <input
                                disabled={this.state.musicImportWay ? '' : 'disabled'}
                                style={styles.imageInput}
                                name="song_file"
                                placeholder="upload local song"
                                type="file"
                            />
                        </FlatButton>
                        <input type="hidden" name="songlist_id" value={this.state.songlist_id}/>
                        <input type="hidden" name="language" value={this.state.language}/>
                        <input type="hidden" name="styles" value={this.state.styles_binary}/>
                        <input type="submit" value="upload" id="uploadLocalSong" style={{visibility : 'collapse'}}/>
                    </form>
                    <SelectField
                        name="language"
                        value={this.state.language}
                        onChange={this.handleChangeLanguage}
                        floatingLabelText="语言"
                        maxHeight={200}
                    >
                        {language_items}
                    </SelectField>
                    <div>
                        <Checkbox label="流行" checked={this.state.styles_source[0]} id="0" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="摇滚" checked={this.state.styles_source[1]} id="1" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="民谣" checked={this.state.styles_source[2]} id="2" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="电子" checked={this.state.styles_source[3]} id="3" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="舞曲" checked={this.state.styles_source[4]} id="4" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="说唱" checked={this.state.styles_source[5]} id="5" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="轻音乐" checked={this.state.styles_source[6]} id="6" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="爵士" checked={this.state.styles_source[7]} id="7" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="乡村" checked={this.state.styles_source[8]} id="8" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="古典" checked={this.state.styles_source[9]} id="9" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="民族" checked={this.state.styles_source[10]} id="10" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="英伦" checked={this.state.styles_source[11]} id="11" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="金属" checked={this.state.styles_source[12]} id="12" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="朋克" checked={this.state.styles_source[13]} id="13" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="蓝调" checked={this.state.styles_source[14]} id="14" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="雷鬼" checked={this.state.styles_source[15]} id="15" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="古风" checked={this.state.styles_source[16]} id="16" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="另类/独立" checked={this.state.styles_source[17]} id="17" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="世界音乐" checked={this.state.styles_source[18]} id="18" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="拉丁" checked={this.state.styles_source[19]} id="19" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                        <Checkbox label="R&B/Soul" checked={this.state.styles_source[20]} id="20" style={styles.checkbox} onCheck={this.handleChangeStyle}/>
                    </div>
                    <form id="addsong" method="post" encType="multipart/form-data" action={API.AddSongFromNetEase} target="uploadSongFrame"
                          style={this.state.musicImportWay ? {visibility : 'collapse', textAlign:'center'}:{visibility : 'visible'}}
                    >
                        <input type="hidden" name="songlist_id" value={this.state.songlist_id}/>
                        <input type="hidden" name="netease_id" value={this.state.song_netease_id} />
                        <input type="hidden" name="song_name" value={this.state.song_name}/>
                        <input type="hidden" name="song_artists" value={this.state.song_artists}/>
                        <input type="hidden" name="mp3Url" value={this.state.mp3Url}/>
                        <input type="hidden" name="duration" value={this.state.duration}/>
                        <input type="hidden" name="language" value={this.state.language}/>
                        <input type="hidden" name="styles" value={this.state.styles_binary}/>
                        <input type="submit" value="upload" id="AddNeteaseSong" style={{visibility : 'collapse'}}/>
                    </form>
                </Dialog>
            </div>
        );
    }
}

//展示乐单
export default class SongListItem extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            expanded: false,
            song_list : [],
            liked : this.props.liked,
        };
    }

    componentWillMount() {
        //this.setState({song_list : tableData});
    }

    loadData = () => {
        const URL = API.GetSongsOfSongList;
        $.ajax({
            url : URL,
            type : 'GET',
            async : true,
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            dataType:'json',
            data : {
                id : this.props.songlist_id,
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                for(let i = 0; i < data.length; i++){
                    let img_url = data[i].image_url;
                    if(img_url === null) continue;
                    data[i].image_url = img_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
                    data[i].image_url = img_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
                }
                this.setState({song_list : data})
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    handleExpandChange = (expanded) => {
        if(expanded) {
            this.loadData();
        }
        this.setState({expanded: expanded});
    };

    addItem = () => {
        console.log('add a music');
        this.refs.addItemDialog.handleOpen(this.props.songlist_id);
    };

    removeItem = (event) => {
        //console.log('remove' + event.target.parentNode.parentNode.parentNode.getAttribute('key'));
        //this.state.song_list.splice(parseInt(event.target.parentNode.parentNode.parentNode.getAttribute('order'), 10), 1);
        //this.setState({song_list:this.state.song_list});

        const song_id = event.target.parentNode.parentNode.parentNode.getAttribute('alt');

        let data ={ songlist_id : this.props.songlist_id, song_id : song_id };
        const URL = API.RemoveSong;
        $.ajax({
            url : URL,
            type : 'POST',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            dataType : 'json',
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                if(data.result){
                    this.loadData();
                }
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    changeImg = (event) => {
        const index = event.target.getAttribute('alt');
        const list = this.state.song_list[index];
        console.log('change Img');
        console.log(event.target.getAttribute('alt'));
        this.refs.changeItemImageDialog.handleOpen(this.props.songlist_id, list.song_id, list.image_id);
    };

    deleteList = () => {
        console.log('delete List' + this.props.songlist_id);
        this.props.onDelete(this.props.songlist_id);
    };

    playAll = () => {
        window.open('/radio.html?type=songlist&songlist_id=' + this.props.songlist_id);
    };

    playSingle = (event) => {
        const index = event.target.parentNode.parentNode.parentNode.getAttribute('alt');
        const song = this.state.song_list[index];
        console.log(song);
        window.open('/radio.html?type=song&song_id=' + song.song_id + '&img_id=' + song.image_id);
    };

    like = (event) => {
        console.log('like!');
        const URL = API.LikeSongList;
        let data = {songlist_id : event.target.parentNode.parentNode.parentNode.getAttribute('alt')};
        $.ajax({
            url : URL,
            type : 'POST',
            contentType: 'application/json',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            data : JSON.stringify(data),
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                if(data.result) this.setState({liked : true});
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    changeTitle = () => {
        this.refs.editSongList.handleOpen(this.props.songlist_id, this.props.title, this.props.description, this.props.img_id);
    };

    replaceURL = (url) => {
        let img_url = url;
        img_url = img_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
        img_url = img_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");
        return img_url;
    };

    render() {

        let img_url = this.props.img_url;
        img_url = img_url.replace(/.*\\resources\\images\\/, "http://radioimg.neverstar.top/");
        img_url = img_url.replace(/.*\/resources\/images\//, "http://radioimg.neverstar.top/");

        return (
            <Card expanded={this.state.expanded} onExpandChange={this.handleExpandChange} style={styles.cardSize}>
                <CardHeader
                    title={this.props.title}
                    subtitle={'by ' + this.props.author}
                    avatar={<IconButton onTouchTap={this.like} alt={this.props.songlist_id}><StarBorder color={this.state.liked ? "yellow" : "black"} /></IconButton>}
                    actAsExpander={true}
                    showExpandableButton={true}
                />
                <CardMedia
                    expandable={true}
                    overlay={<CardTitle
                        title={this.props.title}
                        subtitle={this.props.author}
                    />}
                >
                    <img src={img_url} alt="居然找不到图片" />
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
                    >
                        <TableRow>
                            <TableHeaderColumn colSpan="5" tooltip="Song Information" style={{fontSize : '2.5vh'}}>
                                Song Information
                            </TableHeaderColumn>
                            <TableHeaderColumn colSpan="1" tooltip="Edit Title" style={{textAlign: 'center', fontSize : '2.5vh'}}>
                                <FlatButton onTouchTap={this.changeTitle} label="Edit Title"></FlatButton>
                            </TableHeaderColumn>
                        </TableRow>
                        <TableRow>
                            <TableHeaderColumn tooltip="play" style={styles.tableFont}>play</TableHeaderColumn>
                            <TableHeaderColumn tooltip="song_name" style={styles.tableFont}>Song</TableHeaderColumn>
                            <TableHeaderColumn tooltip="author" style={styles.tableFont}>Author</TableHeaderColumn>
                            <TableHeaderColumn tooltip="duration" style={styles.tableFont}>Duration</TableHeaderColumn>
                            <TableHeaderColumn tooltip="img_url" style={styles.tableFont}>Image</TableHeaderColumn>
                            <TableHeaderColumn tooltip="modify" style={styles.tableFont}>Modify</TableHeaderColumn>
                        </TableRow>
                    </TableHeader>
                    <TableBody
                        showRowHover={true}
                        displayRowCheckbox={false}>
                        {this.state.song_list.map((row, index) => (
                            <TableRow key={index}>
                                <TableRowColumn style={styles.tableButton}>
                                    <IconButton onTouchTap={this.playSingle} alt={index}><AvPlayCircleOutline/></IconButton>
                                </TableRowColumn>
                                <TableRowColumn style={styles.tableFont}>{row.song_name}</TableRowColumn>
                                <TableRowColumn style={styles.tableFont}>{row.artists}</TableRowColumn>
                                <TableRowColumn style={styles.tableDur}>{(row.duration / 60000).toFixed(0) + '分' + ((row.duration / 1000) % 60).toFixed(0) + '秒' }</TableRowColumn>
                                <TableRowColumn><img style={styles.imgSize} src={this.replaceURL(row.image_url)} onClick={this.changeImg} alt={index}/></TableRowColumn>
                                <TableRowColumn style={styles.tableButton}>
                                    <IconButton onTouchTap={this.removeItem} alt={row.song_id} key={index}><ContentRemoveCircleOutline /></IconButton>
                                </TableRowColumn>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
                <CardText expandable={true}>
                    {this.props.description}
                </CardText>
                <CardActions>
                    <FlatButton label="Add" onTouchTap={this.addItem} />
                    <FlatButton label="Delete" onTouchTap={this.deleteList} />
                    <FlatButton label="PlayAll" onTouchTap={this.playAll} />
                </CardActions>
                <ChangeMusicImage ref="changeItemImageDialog" refresh={this.loadData}/>
                <AddMusicItemDialog ref="addItemDialog" refresh={this.loadData}/>
                <EditSongListDialog ref="editSongList" refresh={this.props.refresh}/>
            </Card>
        );
    }
}