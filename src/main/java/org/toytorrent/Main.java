package org.toytorrent;

import be.adaxisoft.bencode.BDecoder;
import be.adaxisoft.bencode.BEncodedValue;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class Main {
    public static void main(String[] args) {



        try {
            FileInputStream torrentStream = new FileInputStream(new File(args[0]));

            BDecoder reader = new BDecoder(torrentStream);

            Map<String, BEncodedValue> document = reader.decodeMap().getMap();

            System.out.println(document.get("announce").getString());

            System.out.println(document.get("info").getMap().keySet());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}