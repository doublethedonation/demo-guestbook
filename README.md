# guestbook

## Front end

`npm install`

`npx shadow-cljs compile app`

`npx shadow-cljs watch app`

## Back end

`make repl`

```clojure
(go)
```

```clojure
(reset)
```

## REPLs

### Cursive

Configure a [REPL following the Cursive documentation](https://cursive-ide.com/userguide/repl.html). Using the default "Run with IntelliJ project classpath" option will let you select an alias from the ["Clojure deps" aliases selection](https://cursive-ide.com/userguide/deps.html#refreshing-deps-dependencies).

### CIDER

Use the `cider` alias for CIDER nREPL support (run `clj -M:dev:cider`). See the [CIDER docs](https://docs.cider.mx/cider/basics/up_and_running.html) for more help.

Note that this alias runs nREPL during development. To run nREPL in production (typically when the system starts), use the kit-nrepl library through the +nrepl profile as described in [the documentation](https://kit-clj.github.io/docs/profiles.html#profiles).

### Calva (VS Code)

Easiest is to use the command **Calva: Start a Project REPL and Connect** (a.k.a. Jack-in)** and select the `:dev` alias. See [Calva docs](https://calva.io) for how to use Calva.

If you prefer to start the REPL manually, you can run `clj -M:dev:cider` and then connect Calva.

### Command Line

Run `clj -M:dev:nrepl` or `make repl`.

Note that, just like with [CIDER](#cider), this alias runs nREPL during development. To run nREPL in production (typically when the system starts), use the kit-nrepl library through the +nrepl profile as described in [the documentation](https://kit-clj.github.io/docs/profiles.html#profiles).
