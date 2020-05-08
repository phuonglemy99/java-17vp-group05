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

let User = mongoose.model("User", UserSchema, "users");

module.exports = User;