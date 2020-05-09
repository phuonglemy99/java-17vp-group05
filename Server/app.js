// Require the express module
const express = require('express');

// Create a new express application
const app = express();

// Create server 
const http = require("http").createServer(app);

// import Socket.IO
const io = require('socket.io')(http);

// import package to generate id for room
var randomstring = require("randomstring");

// define routes 
const userRoute = require('./Routes/user.route');
const authRoute = require('./Routes/auth.route');

// Mongoose
const mongoose = require("mongoose");
mongoose.connect("mongodb://localhost:27017/battleship");

// Body-parser
app.use(express.urlencoded({ extended: true }));

app.get('/play-match', function(req, res){
    res.sendFile(__dirname + '\\Static\\index_playmatch.html');
})

// Using route in Express
app.use('/users', userRoute);
app.use('/auth', authRoute);

// start server 
http.listen(3000, function(){
    console.log('listening on *:3000');
});