### RUN
```bash
$ clojure -M:run
[main] INFO org.eclipse.jetty.server.Server - jetty-11.0.18; built: 2023-10-27T02:14:36.036Z; git: 5a9a771a9fbcb9d36993630850f612581b78c13f; jvm 21.0.2+13-LTS
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.s.ServletContextHandler@5c14c284{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@5632bed3{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
[main] INFO org.eclipse.jetty.server.Server - Started Server@295f115d{STARTING}[11.0.18,sto=0] @12985ms
```
### PLAY
```bash
~ $ curl -H "Content-Type: application/json" http://localhost:8080 -d '{"email": "syd@email.com", "password": "_5rtong-p4ssw0rd_"}'
login berhasil!
~ $ curl -H "Content-Type: application/json" http://localhost:8080 -d '{"email": "syd@email.com", "password": "_5rtong-p4sswrd_"}'
login gagal!
```
### REFERENCES
- [https://clojure.org/](https://clojure.org/)
- [https://github.com/ring-clojure/ring/](https://github.com/ring-clojure/ring/)
- [https://docs.datomic.com/cloud/datomic-local.html/](https://docs.datomic.com/cloud/datomic-local.html/)
