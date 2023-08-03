const http = require('http')
const port = process.env.PORT || 8080

const requestHandler = (request, response) => {
    response.end(`Hello, World!\nNode.js Version: ${process.version}\n`)
}

const server = http.createServer(requestHandler)

server.listen(port, (err) => {
    if (err) {
        return console.log('something bad happened', err)
    }
})
