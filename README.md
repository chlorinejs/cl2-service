# Chlorine service

Chlorine compiling service to use with [Plunker](http://plnkr.co/)

## Start server

Start the server

```
lein ring server
```

or

```
lein run
```

## Usage

- Include compiled `core-cl2` library for your strategy.
(You don't need this if 'bare' strategy is used.)
```html
<script src="{host}/{strategy}/prelude.js"></script>
```

- Include your cl2 script. Yes, the path is in *compiling service's server*
instead of a relative one.
The service will look at each request's referer header to determine URL
to the cl2 script then fetch and serve its compiled counterpart to users.
```html
<script src="{host}/{strategy}/script.cl2"></script>
```
where {HOST} can be any host where this software is running.
{strategy} can be one of these: bare, prod, prod-compate, dev

## Examples

### A simple AngularJS example

- [with latest Angular-cl2 snapshot version](http://plnkr.co/edit/gist:7409003?p=preview)

### Compiling errors

- These demos show how compiling errors are reported to Plunker users:
  + [Timeout compiling](http://plnkr.co/edit/gist:5469423?p=preview)
  + [Error expanding macro](http://plnkr.co/edit/gist:5469390?p=preview)
  + Expected error [here](http://plnkr.co/edit/gist:5469298?p=preview)

## License

Copyright (C) 2012-2014 Hoang Minh Thang

Distributed under the Eclipse Public License, the same as Clojure.
