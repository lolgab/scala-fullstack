# scala-fullstack
Full stack skeleton project using Akka-http, Scala.js, Laminar, Sloth, Boopickle

To try you need [Mill build tool](http://www.lihaoyi.com/mill/).

To start the server:
```
mill -watch jvm.runBackground
```

in another shell you need to generate the javascript output:
```
mill -watch js.fastOpt
```

then you can open the `index.html` file in the `www` folder.
