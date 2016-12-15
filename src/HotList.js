/**
 * Created by tsengkasing on 12/9/2016.
 */
import React from 'react';
import {GridList, GridTile} from 'material-ui/GridList';
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
        // width: 500,
        // height: 450,
        overflowY: 'auto',
    },
};

const tilesData = [
    {
        img_url: 'dynamic/img/0.jpg',
        songlist_name: 'Breakfast',
        author: 'jill111',
        liked : 2048,
        featured: true,
    },
    {
        img_url: 'dynamic/img/1.jpg',
        songlist_name: 'Tasty burger',
        author: 'pashminu',
        liked : 1024,
    },
    {
        img_url: 'dynamic/img/2.jpg',
        songlist_name: 'Camera',
        author: 'Danson67',
        liked : 512,
    },
    {
        img_url: 'dynamic/img/3.jpg',
        songlist_name: 'Morning',
        author: 'fancycrave1',
        liked : 256,
    },
    {
        img_url: 'dynamic/img/4.jpg',
        songlist_name: 'Hats',
        author: 'Hans',
        liked : 128,
    },
    {
        img_url: 'dynamic/img/5.jpg',
        songlist_name: 'Honey',
        author: 'fancycravel',
        liked : 64,
    },
    {
        img_url: 'dynamic/img/6.jpg',
        songlist_name: 'Vegetables',
        author: 'jill111',
        liked : 16,
    },
    {
        img_url: 'dynamic/img/7.jpg',
        songlist_name: 'Water plant',
        author: 'BkrmadtyaKarki',
        liked : 8,
    },
    {
        img_url: 'dynamic/img/8.jpg',
        songlist_name: 'Nothing on you',
        author: 'happyfarmergo',
        liked : 4,
    },
    {
        img_url: 'dynamic/img/9.jpg',
        songlist_name: 'goodbye',
        author: 'captain',
        liked : 2,
    },
    {
        img_url: 'dynamic/img/10.jpg',
        songlist_name: 'dream me',
        author: 'everstar',
        liked : 1,
    }
];

/**
 * This example demonstrates "featured" tiles, using the `rows` and `cols` props to adjust the size of the tile.
 * The tiles have a customised title, positioned at the top and with a custom gradient `titleBackground`.
 */
class HotList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            songlist : null,
        };
    }


    loadData() {
        const URL = API.HotList;
        $.ajax({
            url : URL,
            type : 'GET',
            data : {
                num : 11
            },
            success : function(data, textStatus, jqXHR) {
                data = $.parseJSON(data);
                data.result[0].featured = true;
                this.setState({songlist : data.result})
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };


    componentWillMount() {
        //this.loadData();
    };

    render() {
        return (<div style={styles.root}>
            <GridList
                cols={2}
                cellHeight={200}
                padding={1}
                style={styles.gridList}
            >
                {tilesData.map((tile) => (
                    <GridTile
                        key={tile.img_url}
                        title={<span>{tile.songlist_name}&nbsp;<b style={{color : 'yellow'}}>{tile.liked}</b></span>}
                        subtitle={<span>by <b>{tile.author}</b></span>}
                        actionIcon={<IconButton><StarBorder color="white" /></IconButton>}
                        actionPosition="left"
                        titlePosition="top"
                        titleBackground="linear-gradient(to bottom, rgba(0,0,0,0.7) 0%,rgba(0,0,0,0.3) 70%,rgba(0,0,0,0) 100%)"
                        cols={tile.featured ? 2 : 1}
                        rows={tile.featured ? 2 : 1}
                    >
                        <img alt="居然找不到图片" src={tile.img_url} />
                    </GridTile>
                ))}
            </GridList>
        </div>);
    }

}

export default HotList;