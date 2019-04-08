# Web Socket Word Game

Basic Java web socket app, based on the example in [this article](https://blog.openshift.com/how-to-build-java-websocket-applications-using-the-jsr-356-api/).

To run the server,

```bash
cd server
mvn exec:java -Dexec.mainClass="com.haydenbraxton.wordgame.server.WebSocketServer"
```

To run the client,

```bash
cd client
npm install
npm start
```

In the example, the client and server only send simple strings back and forth. For a more complex use-case, you would probably want to communicate with json objects. On the front end, you would build up your object and then pass it to the server via `socket.send(JSON.stringify(data))`. On the backend, you could deserialize these messages using something like [gson](https://github.com/google/gson).
