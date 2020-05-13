let User = require("../Models/user");

module.exports.login = function(req, res) {
    let {username, password} = req.body;
    User.findOne({username, password}, function(err, user){
        if (err)
            res.json({status: 0});
        
        if (!user)
            res.json({status: 0});
        else res.json(
            {
                status: 1,
                userid: user._id,
                activate: user.activate
            }
        );
    })
}