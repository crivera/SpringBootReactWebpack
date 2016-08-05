import React from 'react';
import {render} from 'react-dom';
import Test from './test.js';

class App extends React.Component {
  render () {
    return <p> Hello Affe!<Test/></p>;
  }
}

render(<App/>, document.getElementById('myDiv'));