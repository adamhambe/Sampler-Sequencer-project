import React from 'react';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import Workstation from "../workstation/Workstation";
import Home from "../home/Home";
import About from "../about/About";
import Profile from "../profile/Profile";
import './Toolbar.css'
import {faInfinity} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const toolbar = props => {
    return (
        <Router>
            <header className="toolbar">
                <nav className="toolbar_navigation">
                    <div className="toolbar_logo">
                        <Link
                            to="/">
                            SeqArc
                            <FontAwesomeIcon
                                className="infinity"
                                icon={faInfinity}/>
                        </Link>
                    </div>
                    <div className="spacer"></div>
                    <div className="toolbar_navigation_items">
                        <ul className="navbar">
                            <li>
                                <Link to="/">Home</Link>
                            </li>
                            <li>
                                <Link to="/about">About</Link>
                            </li>
                            <li>
                                <Link to="/workstation">Workstation</Link>
                            </li>
                            <li>
                                <Link to="/profile">Profile</Link>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
            <main className="main">
                <Switch>
                    <Route path="/about">
                        <About/>
                    </Route>
                    <Route path="/workstation">
                        <Workstation/>
                    </Route>
                    <Route path="/profile">
                        <Profile/>
                    </Route>
                    <Route path="/">
                        <Home/>
                    </Route>
                </Switch>
            </main>
        </Router>
    )
}

export default toolbar;