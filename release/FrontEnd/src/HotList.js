/**
 * Created by tsengkasing on 12/9/2016.
 */
import React from 'react';
import {GridList, GridTile} from 'material-ui/GridList';
import { hashHistory } from 'react-router'
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

class HotList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            songlist : [],
        };
    }


    loadData() {
        const URL = API.HotList;
        $.ajax({
            url : URL,
            type : 'GET',
            headers : {
                'target' : 'api',
            },
            contentType: 'application/json;charset=UTF-8',
            dataType:'json',
            data : {
                num : 11
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                data[0].featured = true;
                this.setState({songlist : data})
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };


    componentWillMount() {
        this.loadData();
    };

    redirect = (event) => {
        const index = event.target.id;
        const list = this.state.songlist[index];

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
                        key={index}
                        title={<span>{tile.songlist_name}&nbsp;<b style={{color : 'yellow'}}>{tile.liked}</b></span>}
                        subtitle={<span>by <b>{tile.author}</b></span>}
                        actionPosition="left"
                        titlePosition="top"
                        titleBackground="linear-gradient(to bottom, rgba(0,0,0,0.7) 0%,rgba(0,0,0,0.3) 70%,rgba(0,0,0,0) 100%)"
                        cols={tile.featured ? 2 : 1}
                        rows={tile.featured ? 2 : 1}
                    >
                        <img alt="居然找不到图片" src={tile.img_url || 'dynamic/img/6.png'} id={index} onClick={this.redirect} />
                    </GridTile>
                ))}
            </GridList>
        </div>);
    }

}

export default HotList;