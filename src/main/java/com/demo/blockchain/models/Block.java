package com.demo.blockchain.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    private int index;
    private long timestamp;
    private String data;
    private String previousHash;
    private int nonce;
    private String hash;

    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.hash = calculateHash(this.nonce);
    }

    private String calculateHash(int nonce) {
        int index = this.index;
        long timestamp = this.timestamp;
        String data = this.data;
        String previousHash = this.previousHash;        
        
        String input = index + timestamp + data + previousHash + nonce;
        StringBuilder hexString = new StringBuilder();
        String hex = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(input.getBytes("UTF-8"));
            for (byte b : hashedBytes) {
                hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception e) {
            System.out.println("Error Message: " + e.getMessage());
        }
        
        return hexString.toString();
    }   

    public void mineBlock(int difficulty) throws NoSuchAlgorithmException {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash(nonce);
        }
    }

    public String recalculateHash() {
        return calculateHash(nonce);  // Calls private method with current nonce
    }

    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public int getNonce() {
    return nonce;
}

}
