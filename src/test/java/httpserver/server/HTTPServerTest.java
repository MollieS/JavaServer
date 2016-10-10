package httpserver.server;

import httpserver.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPServerTest {

    private final ConnectionsSocketServer connectionsSocketServer = new ConnectionsSocketServer();

    @Test
    public void canAcceptConnectionsOnThePortWhenListening() throws IOException {
        HTTPServer httpServer = new HTTPServer(connectionsSocketServer, new RouterDummy(), new LoggerDummy());

        ExecutorService executorService = new ExecutorServiceSpy();
        httpServer.start(executorService);

        assertEquals(1, connectionsSocketServer.getConnections());
    }

    @Test
    public void usesAnExecutorServiceToProcessRequest() {
        HTTPServer httpServer = new HTTPServer(new SocketServerFake(), new RouterDummy(), new LoggerDummy());

        ExecutorServiceSpy executorService = new ExecutorServiceSpy();
        httpServer.start(executorService);

        assertTrue(executorService.hasBeenCalled);
    }

    private class SocketServerFake implements SocketServer {

        int timesCalled;

        public SocketServerFake() {
            timesCalled = 0;
        }

        @Override
        public ClientSocket serve() {
            timesCalled++;
            if (timesCalled > 1) {
                return null;
            }
            return new SocketFake();
        }
    }

    private class RouterDummy implements HTTPRouter {

        @Override
        public Response route(Request httpRequest) {
            return null;
        }
    }

    private class LoggerDummy extends HTTPLogger {

        public LoggerDummy() {
            super("/path");
        }

        @Override
        public void log(String request) {
        }
    }

    private class ExecutorServiceSpy implements ExecutorService {

        boolean hasBeenCalled;

        public ExecutorServiceSpy() {
            hasBeenCalled = false;
        }

        @Override
        public void shutdown() {
        }

        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return null;
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void execute(Runnable command) {
            hasBeenCalled = true;
        }
    }
}

