let User = require("../Models/user");

module.exports.getAllInfo = function(req, res) {
    let userID = req.params.id;
    console.log(userID);
                                        // projection in mongoDB 
    User.findById(userID, '-password -_id').exec( function(err, adventure){
        console.log(adventure);
    })
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
            res.json({status: 0});
        res.json({status: 1});
    });

}