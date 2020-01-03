const express = require('express');
const fs = require('fs');
const path = require('path');
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(express.static(`d:\\data`));
app.use(express.static(path.join(__dirname, 'public')));

app.get('/', (req, res) => res.sendFile('index.html'));

app.post('/:folder', (req, res) => {
    fs.readdir(path.join('d:', req.body.cur, req.params.folder), (err, files) => res.json(files));
});

app.listen(3000, '0.0.0.0', () => console.log('Example app listening on port 3000!'));