import React, { Component } from 'react';
import { connect } from 'react-redux';
import logo from '../../common/media/logo.svg';
import MvpMenu from '../MvpMenu/MvpMenu';
import './Header.scss';

export class Header extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
    return (
      <div className="header-container">
        <div className="logo-container">
          <img className="logo" src={logo} alt="Cockpit Logo" />
        </div>
        <MvpMenu />
      </div>
    );
  }
}

export default connect()(Header);
