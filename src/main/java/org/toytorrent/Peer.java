package org.toytorrent;

public class Peer {
    String address;
    int port;

    Peer(String addr, int pt) {
        address = addr;
        port = pt;
    }

    @Override
    public String toString() {
        return address + ":" + String.valueOf(port);
    }
}
