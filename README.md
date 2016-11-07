# HTTP Server

[![Coverage Status](https://coveralls.io/repos/github/MollieS/HTTPServer/badge.svg?branch=master)](https://coveralls.io/github/MollieS/HTTPServer?branch=master) [![Build Status](https://travis-ci.org/MollieS/HTTPServer.svg?branch=master)](https://travis-ci.org/MollieS/HTTPServer)

Simple HTTP Server build with Java

## To run

* clone this repository
* build with gradle by moving into the repository and using the command `gradle build`
* use the command `java -jar build/libs/Server-1.0.jar` with two arguments : `-p <port number>` and `-d <directory path>`

## To run with Tic Tac Toe
* Tic Tac Toe uses some external dependencies not included in the regular jar
* to run the server with TTT, use the command `gradle fatJar`
* run the server with `java -jar build/libs/Server-1.0-all.jar`

## To test

* from within the directory run `gradle test`
