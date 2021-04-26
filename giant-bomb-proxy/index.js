const express = require('express')
const axios = require('axios')
const cors = require('cors')

const app = express();
const port = 9001;

app.get('/search', cors(), (req, res) => {
    const giantBomb = 'https://www.giantbomb.com/api/search?'
    const queryString = req.originalUrl.split('?')[1];

    axios.get(giantBomb + queryString)
        .then(x => {
            res.status(x.status);
            res.json(x.data);
        }).catch(e => {
            res.status(500);
            res.send({"error": "something went wrong!!!"});
        });    
});

app.listen(port);