var express = require('express');
var app = express();
var fs = require("fs");

var bodyParser = require('body-parser')
app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({
    extended: true
}));

// A promo message to user 
var message = "Hello from another end point.";

app.get('/messages', function (req, res) {
    res.end(JSON.stringify(message));
})


// Home Page 
app.get('/', (req, res) => res.send('Welcome! From new end point!'))

// Configure server 
var server = app.listen(7000, '127.0.0.1', function (req, res) {

    var host = server.address().address
    var port = server.address().port

    console.log(`Server running at http://${host}:${port}/`);
})

