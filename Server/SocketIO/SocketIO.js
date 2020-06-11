const imp = require("../import");

const io = imp.IO;

let count_connection = 0;
let last_room = null;

console.log("Hello world");

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