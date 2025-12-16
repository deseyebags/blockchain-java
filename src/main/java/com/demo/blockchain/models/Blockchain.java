package com.demo.blockchain.models;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> blocks;

    public Blockchain() {
        blocks = new ArrayList<>();
        Block genesis = new Block(0, "Genesis Block", "0");
        try{
            genesis.mineBlock(2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
         
        blocks.add(genesis);  
    }

    public void addBlock(String data)  {
        if (data == null || data.trim().isEmpty()) {
        throw new IllegalArgumentException("Transaction data cannot be empty");
    }
        Block lastBlock = blocks.get(blocks.size() - 1);
        Block new_block = new Block(blocks.size(), data, lastBlock.getHash());
        try {
            new_block.mineBlock(2);
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException("Mining failed - SHA-256 unavailable", e);
        }

        blocks.add(new_block);
    }


    public boolean isValid() {
        if (blocks.size() == 0) {
            return false;
        }
        
        for (int i = 1;i < blocks.size(); i++) {
            Block current_block = blocks.get(i);
            Block previous_block = blocks.get(i - 1);

            if (!current_block.getHash().equals(current_block.recalculateHash())) {
                return false;
            }

            if (!current_block.getPreviousHash().equals(previous_block.getHash())) {
                return false;
            }
        }
        return true;

    }

    public List<Block> getBlocks() {
            return blocks;
        }

    public void printChain() {
    for (Block block : blocks) {
        System.out.println("Index: " + block.getIndex());
        System.out.println("Data: " + block.getData());
        System.out.println("Hash: " + block.getHash());
        System.out.println("Prev: " + block.getPreviousHash());
        System.out.println("---");
    }
}

}
