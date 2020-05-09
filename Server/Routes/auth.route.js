const express = require('express');
let route = express.Router();

const controller = require('../Controllers/auth.controller');

route.post('/login', controller.login);


module.exports = route;

