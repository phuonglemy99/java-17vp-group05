let express = require("express");
let router  = express.Router();

const controller = require('../Controllers/user.controller');

// GET Method
router.get('/:id/getAllInfo', controller.getAllInfo);

// POST Method
router.post('/create', controller.create);
router.post('/:id/updateInfo', controller.updateInfo);
router.post('/:id/addHistory', controller.addHistory);

module.exports = router;