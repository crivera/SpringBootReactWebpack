import React from 'react';
import {render} from 'react-dom';

class AroundMe extends React.Component {
  render () {
    return <p> Hello React123!</p>;
  }
}

render(<AroundMe/>, document.getElementById('aroundMeDiv'));