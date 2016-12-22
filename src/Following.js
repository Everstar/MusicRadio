/**
 * Created by tsengkasing on 12/16/2016.
 */
import React from 'react';
import Avatar from 'material-ui/Avatar';
import {Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn}
    from 'material-ui/Table';
import { hashHistory } from 'react-router'
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import API from './API';
import $ from 'jquery';

const friends_data_1 = [
    {
        user_id : 15,
        avatar : 'dynamic/img/0.jpg',
        gender : 'male',
        name : 'rabbit',
        level : 4,
    },
    {
        user_id : 15,
        avatar : 'dynamic/img/2.jpg',
        gender : 'male',
        name : 'pig',
        level : 2,
    },
    {
        user_id : 15,
        avatar : 'dynamic/img/3.jpg',
        gender : 'female',
        name : 'monkey',
        level : 3,
    },
    {
        user_id : 15,
        avatar : 'dynamic/img/4.jpg',
        gender : 'female',
        name : 'hive',
        level : 1,
    },


];

export default class Following extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current_page : 1,
            all_pages : 1,

            meta_list : [],
            friend_list : [],

            disablePreviousButton : true,
            disableNextButton : true,
        };
    }

    getFollowing = () => {
        const URL = API.Following;
        $.ajax({
            url : URL,
            type : 'GET',
            contentType: 'application/json',
            dataType: 'json',
            headers : {
                'target' : 'api',
            },
            success : function(data, textStatus, jqXHR) {
                console.log(data);
                let pages = 1;
                let temp = data;
                if(data.length > 10) {
                    pages = data.length / 10 + (data.length % 10) > 0 ? 1 : 0;
                    temp = data.slice(0, 10);
                }
                console.log('all_pages : ' + pages);
                this.setState({
                    meta_list : data,
                    friend_list : temp,
                    all_pages : pages,
                });
            }.bind(this),
            error : function(xhr, textStatus) {
                console.log(xhr.status + '\n' + textStatus + '\n');
            }
        });
    };

    componentWillMount() {
        this.getFollowing();
    };

    previousPage= () => {
        let current = parseInt(this.state.current_page, 10);
        this.setState({
            current_page : current - 1,
            friend_list : this.state.meta_list.slice((current - 2) * 10, (current - 2) * 10 + 10),
            disableNextButton: false,
            disablePreviousButton: ((current - 1) === 1),
        });
    };

    nextPage = () => {
        let current = parseInt(this.state.current_page, 10);
        this.setState({
            current_page : (current + 1),
            friend_list : this.state.meta_list.slice((current - 2) * 10, (current - 2) * 10 + 10),
            disableNextButton: ((current + 1) === parseInt(this.state.all_pages, 10)),
            disablePreviousButton: false,
        });
    };


    handleRedirectInfoPage = (event) => {
        console.log('user_id:'+ event.target.parentNode.parentNode.parentNode.getAttribute('alt'));
        hashHistory.push('/user/' + event.target.parentNode.parentNode.parentNode.getAttribute('alt'));
    };

    handleRedirectRadioPage = (event) => {
        console.log('user_id:'+ event.target.parentNode.parentNode.parentNode.getAttribute('alt'));
        hashHistory.push('/user/' + event.target.parentNode.parentNode.parentNode.getAttribute('alt') + '/songlist');
    };

    render() {
        return (
            <Table
                style={{background : 'transparent'}}
                selectable={false}>
            <TableHeader
                displaySelectAll={false}
                adjustForCheckbox={false}>
                <TableRow>
                    <TableHeaderColumn>Avatar</TableHeaderColumn>
                    <TableHeaderColumn>Name</TableHeaderColumn>
                    <TableHeaderColumn>Info</TableHeaderColumn>
                    <TableHeaderColumn>Radio</TableHeaderColumn>
                </TableRow>
            </TableHeader>
            <TableBody
                showRowHover={true}
                displayRowCheckbox={false}>
                {this.state.friend_list.map((row, index) => (
                    <TableRow key={index}>
                        <TableRowColumn><Avatar src={row.avator_url} /></TableRowColumn>
                        <TableRowColumn>{row.username}</TableRowColumn>
                        <TableRowColumn><RaisedButton label="Info" primary={true} onTouchTap={this.handleRedirectInfoPage} alt={row.id} /></TableRowColumn>
                        <TableRowColumn><RaisedButton label="Radio" primary={true} onTouchTap={this.handleRedirectRadioPage} alt={row.id} /></TableRowColumn>
                    </TableRow>
                ))}
            </TableBody>
            <TableFooter
                adjustForCheckbox={false}>
                <TableRow>
                    <TableRowColumn colSpan="5" style={{textAlign: 'center'}}>
                        <FlatButton primary={true} label="previous" onTouchTap={this.previousPage} disabled={this.state.disablePreviousButton} />
                        {this.state.current_page}/{this.state.all_pages}
                        <FlatButton primary={true} label="next" onTouchTap={this.nextPage} disabled={this.state.disableNextButton} />
                    </TableRowColumn>
                </TableRow>
            </TableFooter>
        </Table>
        );
    }
}
