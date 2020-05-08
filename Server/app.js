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

// Mongoose
const mongoose = require("mongoose");
mongoose.connect("mongodb://localhost:27017/battleship");


app.get('/', function(req, res){
    res.sendFile(__dirname + '\\Static\\index.html');
})

app.get('/play-match', function(req, res){
    res.sendFile(__dirname + '\\Static\\index_playmatch.html');
})

let count_connection = 0;
let last_room = null;

const play_match = io.of('/play-match')
                    .on('connect', function(socket){
                        // distribute user finding match in room
                        if ( count_connection % 2 === 0 )                          
                            last_room = randomstring.generate();
            
                        // increase number of user finding match
                        count_connection ++;

                        // join user to room
                        socket.join(last_room)

                        console.log('user connected. \ncount_connection = ' + count_connection);
                        console.log(`user join the room ${last_room}`);

                        play_match.to(last_room).emit('id_room', last_room);
                        
                        // cancel finding match 
                        socket.on('disconnect', function(){
                            count_connection -- ;
                            console.log('user disconnected. \ncount_connection = ' + count_connection);
                        })
                        socket.on('chat message', function(msg){
                            for (let key in socket.rooms) { 
                                if (! key.includes('play-match') )
                                    play_match.to(socket.rooms[key]).emit('chat message', msg);
                            }
                        });                        
                    });

// Using route in Express
app.use('/users', userRoute);

// start server 
http.listen(3000, function(){
    console.log('listening on *:3000');
});