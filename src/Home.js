/**
 * Created by kevin on 12/8/2016.
 */
import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress'
import Divider from 'material-ui/Divider';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import ActionInfo from 'material-ui/svg-icons/action/info';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import Auth from './account/Auth';

const styles = {
    chip: {
        margin: 4,
        'text-align': 'center',
        'margin' : '4% auto'
    },
    wrapper: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    autoWidth: {
        'width' : 'auto'
    }
};


export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            exp: 50,
            maxExp : 100,
            level : 1,
        };
    };

    render() {
        return (
            <div>
                <Card containerStyle={{margin: 16}}>
                    <CardHeader
                        title={Auth.username}
                        subtitle={'level ' + this.state.level}
                        avatar="img/profile_1.png"
                        actAsExpander={true}
                    />
                    <LinearProgress mode="determinate" value={this.state.exp} max={this.state.maxExp} style={{width: '96%', margin:'0 5% 0 5%'}} />
                    <List>
                        <ListItem primaryText="Created Song Lists" rightIcon={<TextField value="233" id="crt" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Liked Song Lists" rightIcon={<TextField value="233" id="lik" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                        <ListItem primaryText="Friends" rightIcon={<TextField value="233" id="friend" inputStyle={styles.autoWidth} readOnly="true" underlineShow={false} />} />
                    </List>
                </Card>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo create songlist drinkMe
                </Chip>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo create songlist forgetYou
                </Chip>
                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo liked songlist nothing
                </Chip>                <Chip
                    style={styles.chip}
                >
                    <Avatar src="img/profile_1.png" />
                    happyfarmergo commented songlist goodbye
                </Chip>
                <Divider style={{'margin-top' : '2%'}}/>
            </div>
        );
    }
}