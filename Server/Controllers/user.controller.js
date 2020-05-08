let User = require("../Models/user");

module.exports.getAllInfo = function(req, res) {
    let userID = req.params.id;
    console.log(userID);
                                        // projection in mongoDB 
    User.findById(userID, '-password -_id').exec( function(err, adventure){
        console.log(adventure);
    })
    return res.send("hello world");
}