package dao;

public class ConnectionException extends Exception {
    public ConnectionException(Exception e) {
        super(e);
    }
}
