package org.toytorrent;

import be.adaxisoft.bencode.BDecoder;
import be.adaxisoft.bencode.BEncodedValue;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {



        try {
           Torrent t = new Torrent(args[0]);

           System.out.println(new String(t.getInfoHash(), StandardCharsets.ISO_8859_1));
           System.out.println(t.getAnnounceURL());
           System.out.println(t.getTorrentKeys());
           System.out.println(t.getLength());

           BittorrentClient client = new BittorrentClient(args[0]);
           client.getPeers();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}