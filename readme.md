# Web Socket Word Game

Basic Java web socket app, based on the example in [this article](https://blog.openshift.com/how-to-build-java-websocket-applications-using-the-jsr-356-api/).

To run the server,

```bash
cd server
mvn exec:java -Dexec.mainClass="com.haydenbraxton.wordgame.server.WebSocketServer"
docker container run -it -v $(pwd):/server -p 4418:4418 maven bash
```

I'm too cheap to include a docker-compose file or docker image, but to run the server in docker

```bash
cd server
docker container run -it -v $(pwd):/server -p 4418:4418 maven bash
cd server # in the container
mvn exec:java -Dexec.mainClass="com.haydenbraxton.wordgame.server.WebSocketServer" # in the container
```

To run the client,

```bash
cd client
npm install
npm start
```

In the example, the client and server only send simple strings back and forth. For a more complex use-case, you would probably want to communicate with json objects. On the front end, you would build up your object and then pass it to the server via `socket.send(JSON.stringify(data))`. On the backend, you could deserialize these messages using something like [gson](https://github.com/google/gson).

The server uses [tyrus](https://tyrus-project.github.io/) and javax packages for websockets ([here](https://docs.oracle.com/javaee/7/api/javax/websocket/server/package-summary.html) and [here](https://docs.oracle.com/javaee/7/api/javax/websocket/package-summary.html)). The client uses the native `WebSocket` object. Make sure to check out [MDN](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications) for more guidance on writing the client app.
