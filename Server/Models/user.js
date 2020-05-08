const mongoose = require('mongoose');

let Schema = mongoose.Schema;

let UserSchema = new Schema({
    name: String,
    username: String,
    password: String,
    win: Number,
    lose: Number,
    money: Number    
});

let HistorySchema = new Schema({
    time: Date,
    enemy: String,
    status: String    
});

let User = mongoose.model("User", UserSchema, "users");
let History = mongoose.model("User", HistorySchema, "users");

module.exports = User;