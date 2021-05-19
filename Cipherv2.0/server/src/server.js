const fs = require('fs')
const SocketServer = require('websocket').server
const http = require('http')

const port = process.env.PORT || 3000
const address = "0.0.0.0"

const server = http.createServer((req, res) => {})

server.listen(port,address, ()=>{
    console.log("Listening on port 3000...")
})

wsServer = new SocketServer({httpServer:server})

const connections = []

wsServer.on('request', (req) => {
    const connection = req.accept()
    console.log('new connection')
    connections.push(connection)

    connection.on('message', function(mes) {
        var text = mes.utf8Data;
        text += "\n";
        fs.writeFile('.\\messages.txt', text, { flag: 'a+' }, err => {})
        console.log(mes.utf8Data)
        connections.forEach(element => {
            if (element != connection)
                element.sendUTF(mes.utf8Data)
        })
    })
// console.log(connections)
    connection.on('close', (resCode, des) => {
        console.log('connection closed')
        connections.splice(connections.indexOf(connection),1)
    })

})