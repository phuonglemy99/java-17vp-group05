const mongoose = require('mongoose');

let Schema = mongoose.Schema;

let HistorySchema = new Schema({
    time: String,
    enemy: String,
    status: String,
    userID: String    
});

let History = mongoose.model("History", HistorySchema, "histories");

module.exports = History;
