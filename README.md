# scala-fullstack

Full stack Scala skeleton project using Akka-http, Scala.js, Laminar, Sloth, Boopickle.

It uses the [Mill build tool](http://www.lihaoyi.com/mill/).

To start the server:

```bash
./mill -watch jvm.runBackground
```

in another shell you need to generate the javascript output:

```bash
./mill -watch js.fastOpt
```

then you can open the served page at `http://localhost:8080`.
