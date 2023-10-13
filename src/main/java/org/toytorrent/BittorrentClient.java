package org.toytorrent;

import be.adaxisoft.bencode.BDecoder;
import be.adaxisoft.bencode.BEncodedValue;
import be.adaxisoft.bencode.InvalidBEncodingException;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BittorrentClient {

    Torrent torrent;
    Utils util;

    BittorrentClient(String torrentFile) {
        torrent = new Torrent(torrentFile);
        util = new Utils();
    }

    Map<String, String> getParams() throws java.io.IOException,NoSuchAlgorithmException{
        byte[] infoHash;
        try {
            infoHash = torrent.getInfoHash();
        }
        catch (NoSuchAlgorithmException je) {
            throw new NoSuchAlgorithmException();
        }

        Map<String, String> params = new LinkedHashMap<>();
        String strInfoHash = URLEncoder.encode(new String(infoHash, StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1.toString());
        System.out.println(strInfoHash);
        params.put("info_hash", strInfoHash);
        params.put("peer_id", "12345678901234567890");
        params.put("port", "6881");
        params.put("uploaded", "0");
        params.put("downloaded", "0");
        params.put("left", torrent.getLength());
        params.put("compact", "1");
        return params;
    }

    String getUrl() throws InvalidBEncodingException {
        return torrent.getAnnounceURL();
    }

    void getPeers() throws InvalidBEncodingException, Exception{
        String url = getUrl();
        InputStream response = util.sendHTTPRequest(url, getParams());
        Map<String, BEncodedValue> decodedResponse = BDecoder.decode(response).getMap();
        byte[] peers = decodedResponse.get("peers").getBytes();

        List<String> peersList = new ArrayList<>();

        for (int i = 0; i< peers.length; i += 6) {
            StringBuilder address = new StringBuilder();
            for (int j = i; j < i + 4; j++) {
                if (address.length() > 0) {
                    address.append(".");
                }
                if (j == i) {
                    address.append("" + (256 +peers[j]));
                } else {
                    address.append("" + peers[j]);
                }
            }
            String port = String.valueOf(65536 + (peers[i + 4] * 256 + peers[i + 5]));
            peersList.add(String.format("%s:%s", address, port));
        }

        System.out.println(peersList);

    }
    public void download()   {
       try {

       } catch (Exception e) {
           e.printStackTrace();
       }

    }

}
