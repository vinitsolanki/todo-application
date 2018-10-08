var express = require('express');
var app = express();
app.use(express.static('../todo-angular-app')); // 'todo' will be the same folder name.
app.get('/', (req, res) => res.sendFile(__dirname + '/index.html'))
app.listen(9090, 'localhost');
console.log("MyProject Server is Listening on port 9090");