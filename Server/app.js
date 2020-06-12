// Require the express module
const imp = require('./import.js');

const express = imp.expr;

// Create a new express application
const app = imp.application;

// Create server 
const http = imp.http;

const io = imp.IO;

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

// Using route in Express
app.use('/users', userRoute);
app.use('/auth', authRoute);

app.get('/', function(req, res){
    res.sendFile(__dirname + '\\Static\\index.html');
})

app.get('/play-match', function(req, res){
    res.sendFile(__dirname + '\\Static\\index_playmatch.html');
})

// Socket.IO program
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
                        console.log(`Socket ID = ${socket.id}`);
                        console.log(`user join the room ${last_room}`);

                        play_match.to(last_room).emit('id_room', last_room);
                        
                        // cancel socket client disconnect 
                        socket.on('disconnect', function(){
                            count_connection -- ;
                            console.log('user disconnected. \ncount_connection = ' + count_connection);
                        })

                        socket.on("fire", function() {
                            console.log("Player fire");
                        })

                        socket.on('chat message', function(msg){
                            for (let key in socket.rooms) { 
                                if (! key.includes('play-match') )
                                    play_match.to(socket.rooms[key]).emit('chat message', msg);
                            }
                        });                        
                    });



// start server 
http.listen(3000, function(){
    console.log('listening on *:3000');
});