let express = require("express");
let router  = express.Router();

const controller = require('../Controllers/user.controller');

router.get('/:id/getAllInfo', controller.getAllInfo)


module.exports = router;