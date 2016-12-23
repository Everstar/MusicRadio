/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import {GridList, GridTile} from 'material-ui/GridList';
import {hashHistory} from 'react-router';
import IconButton from 'material-ui/IconButton';
import StarBorder from 'material-ui/svg-icons/toggle/star-border';
import $ from 'jquery';
import API from './API'

const styles = {
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
    },
    gridList: {
        overflowY: 'auto',
    },
};

class ViewSongList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            songlist : [],
        };
        console.log(this.props.params.id);
    }


    loadData() {
        const URL = API.SongList;
        $.ajax({
            url : URL,
            type : 'GET',
            data : {
                id : this.props.params.id
            },
            headers : {
                target : 'api'
            },
            success : function(data, textStatus, jqXHR) {
                if((data.length % 2) != 0)
                    data[0].featured = true;
                this.setState({songlist : data});
                console.log(data);
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };


    componentWillMount() {
        this.loadData();
    };

    handleLike = (event) => {
        const index = event.target.parentNode.parentNode.parentNode.id;
        const list = this.state.songlist[index];
        if(list.liked) return;//已经喜爱过
        const URL = API.LikeSongList;
        let data = {songlist_id : list.list_id};
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
                this.loadData();
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    handleRedirectSongList = (event) => {
        const index = event.target.getAttribute('about');
        const list = this.state.songlist[index];
        console.log(this.state.songlist[index]);
        hashHistory.push('/songlist/' + list.list_id + '/' + list.songlist_name + '/' + list.author + '/' + list.img_id);
    };

    render() {
        return (<div style={styles.root}>
            <GridList
                cols={2}
                cellHeight={200}
                padding={1}
                style={styles.gridList}
            >
                {this.state.songlist.map((tile, index) => (
                    <GridTile
                        key={tile.list_id}
                        title={<span>{tile.songlist_name}&nbsp;<b style={{color : 'yellow'}}>{tile.likes}</b></span>}
                        subtitle={<span>by <b>{tile.author}</b></span>}
                        actionIcon={<IconButton tooltip="like!" onTouchTap={this.handleLike} id={index}><StarBorder color={tile.liked ? 'yellow' : 'white'}/></IconButton>}
                        actionPosition="left"
                        titlePosition="top"
                        titleBackground="linear-gradient(to bottom, rgba(0,0,0,0.7) 0%,rgba(0,0,0,0.3) 70%,rgba(0,0,0,0) 100%)"
                        cols={tile.featured ? 2 : 1}
                        rows={tile.featured ? 2 : 1}
                    >
                        <img alt="居然找不到图片" about={index} src={tile.img_url} onClick={this.handleRedirectSongList} />
                    </GridTile>
                ))}
            </GridList>
        </div>);
    }

}

export default ViewSongList;