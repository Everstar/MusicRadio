/**
 * Created by kevin on 12/3/2016.
 */
import React from 'react';

var Discover = React.createClass({
    getInitialState() {
        return {discover_js: null};
    },

    componentWillMount: function() {
        let discover_js = document.createElement('script');
        discover_js.src = './js/discover.js';
        document.body.appendChild(discover_js);
        this.setState({discover_js: discover_js});
    },

    componentWillUnmount: function () {
        document.body.removeChild(this.state.discover_js);
    },

    render() {
        return (<div className="hiddenOverflow">
            <div id="area">
            </div>
        </div>);
    }

});



export default Discover;