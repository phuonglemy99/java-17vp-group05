// Require the express module
const express = require('express');

// Create a new express application
const app = express();

// Create server 
const http = require("http").createServer(app);

// import Socket.IO
const io = require('socket.io')(http);

module.exports.application = app;
module.exports.http = http;
module.exports.IO = io;