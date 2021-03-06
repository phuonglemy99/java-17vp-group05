const mongoose = require('mongoose');

let Schema = mongoose.Schema;

let HistorySchema = new Schema({
    time: Date,
    enemy: String,
    status: Boolean,
    userID: String    
});

let History = mongoose.model("History", HistorySchema, "histories");

module.exports = History;
