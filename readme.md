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
