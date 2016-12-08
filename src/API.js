/**
 * Created by kevin on 12/7/2016.
 */
import React from 'react'

let URL = 'http://localhost:8080';

//后端API
class API extends React.Component{

    static SignIn = URL + '/signin';
    static SignUp = URL + '/signup';
    static Account = URL + '/account';

    static render() {
        return null;
    }
}

export default API;