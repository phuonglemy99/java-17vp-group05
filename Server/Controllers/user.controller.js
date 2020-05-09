let User = require("../Models/user");
let History = require("../Models/history");

module.exports.getAllInfo = function(req, res) {
    let userID = req.params.id;
                    // projection in mongoDB 
    User.findById(userID, '-password -__v').exec( function(err, user){
        if(err) 
            return res.json({status: 0});
        History.find({userID}, "-userID -_id -__v", function(error, histories) {
            if (error)
                return res.json({status: 0});
            if (!histories)
                histories = [];
            return res.json({ ...user["_doc"], history: histories});
        })
    })
}

module.exports.updateInfo = function(req, res) {
    let userID = req.params.id;
    User.updateOne({_id: userID}, req.body, function(err, result){
        if (err)
            return res.json({status: 0});
        return res.json({status: result.ok});
    });
}

module.exports.addHistory = function(req, res) {
    let userID = req.params.id;
    
    let newHistory = new History({ userID, ...req.body});
    
    newHistory.save(function(err, history){
        if (err)
            return res.json({status: 0});
        return res.json({status: 1});
    });
}


module.exports.create = function(req, res) {
    let {name, username, password} = req.body;
    let NewUser = new User({
        name,
        username,
        password,
        win: 0,
        lose: 0,
        money: 0
    });
    NewUser.save(function (err, newUser) {
        if (err)
            return res.json({status: 0});
        return res.json({status: 1});
    });
}


