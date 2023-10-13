package org.toytorrent;

import be.adaxisoft.bencode.BDecoder;
import be.adaxisoft.bencode.BEncodedValue;
import be.adaxisoft.bencode.BEncoder;
import be.adaxisoft.bencode.InvalidBEncodingException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Torrent {

    private Map<String, BEncodedValue> torrent;
    private Map<String, BEncodedValue> info;

    Torrent(String filePath) {
        File torrentFile = new File(filePath);
        try {
            FileInputStream torrentStream = new FileInputStream(torrentFile);
            BDecoder reader = new BDecoder(torrentStream);

            torrent = reader.decodeMap().getMap();

            info = torrent.get("info").getMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public String getAnnounceURL() throws InvalidBEncodingException{
        try {
            return this.torrent.get("announce").getString();
        } catch (InvalidBEncodingException e) {
           throw new InvalidBEncodingException(e.toString());
        }
    }

    Map<String, BEncodedValue> getInfoMap(){
            return info;
    }

     String getLength() throws InvalidBEncodingException{
        return String.valueOf(info.get("length").getNumber());
    }

    Set<String> getTorrentKeys() {
        return this.torrent.keySet();
    }

    byte[] getInfoHash() throws NoSuchAlgorithmException, java.io.IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        BEncoder.encode(this.torrent.get("info").getMap(), bytestream);
        byte[] encodedInfo = bytestream.toByteArray();

        return md.digest(encodedInfo);
    }



}
