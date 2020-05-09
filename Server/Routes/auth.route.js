const express = require('express');
let router = express.Router();

const controller = require('../Controllers/auth.controller');

router.post('/login', controller.login);


module.exports = router;

