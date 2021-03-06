/**
 * Created by kevin on 12/5/2016.
 */
import React from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';

/**
 * Dialog with action buttons. The actions are passed in as an array of React objects,
 * in this example [FlatButtons](/#/components/flat-button).
 *
 * You can also close this dialog by clicking outside the dialog, or with the 'Esc' key.
 */
export default class SimpleDialog extends React.Component {

    state = {
        open: false,
        title : "Sign Success!",
        description : "Press OK to redirect to Home.",
        redirect : false,
    };

    handleOpen = (redirect) => {
        this.setState({
            open: true,
            redirect : redirect,
        });
    };

    handleClose = () => {
        this.setState({open: false});
        if(this.props.onPress && this.state.redirect) {
            this.props.onPress();
        }
    };

    setContent = (title, description) => {
        this.setState({
            title : title,
            description : description
        });
    };

    render() {
        const actions = [
            <FlatButton
                label="OK"
                primary={true}
                keyboardFocused={true}
                onTouchTap={this.handleClose}
            />,
        ];

        return (
            <div>
                <Dialog
                    title={this.state.title}
                    actions={actions}
                    modal={false}
                    open={this.state.open}
                    onRequestClose={this.handleClose}
                >
                    {this.state.description}
                </Dialog>
            </div>
        );
    }
}