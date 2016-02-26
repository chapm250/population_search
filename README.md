# simple-search
##Done by Josh Chapman, Nate Perfetti, Snuffy Linder. 

A Clojure implementation of a some simple heuristic search tools for the Spring, 2016, Evolutionary Computation and Artificial Intelligence class. Students will clone this and add functionality as the semester progresses.

##Project Tasks:

- [ ]  purely mutational system
- [ ]  system has uniform crossover
- [ ]  system has 2-point crossover
- [ ]  R markdown write-up


## How to run the tests

The project uses [Midje](https://github.com/marick/Midje/).

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
