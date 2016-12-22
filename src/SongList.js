/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import IconButton from 'material-ui/IconButton';
import StarBorder from 'material-ui/svg-icons/toggle/star-border';
import AvPlayCircleOutline from 'material-ui/svg-icons/av/play-circle-outline';
import ContentAddCircleOutline from 'material-ui/svg-icons/content/add-circle-outline';
import $ from 'jquery';
import Auth from './account/Auth'
import API from './API';

const styles = {
    cardSize : {
        maxWidth:'835px',
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
        fontSize:'1.5vh',
    },
    tableDur : {
        fontSize:'1.5vh',
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
};

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

    handleExpandChange = () => {
        this.setState({expanded : !this.state.expanded});
    };

    componentWillMount() {
        console.log(this.state.song_lists);
        this.loadData();
    }

    playAll = () => {
        //

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
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    render() {
        return (
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
                                    <IconButton><AvPlayCircleOutline/></IconButton>
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
                    <RaisedButton label="Share" primary={true} />
                </CardActions>
            </Card>
        );
    }
}