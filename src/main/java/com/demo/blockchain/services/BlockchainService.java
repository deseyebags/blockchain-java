package com.demo.blockchain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.blockchain.models.Block;
import com.demo.blockchain.models.Blockchain;

@Service
public class BlockchainService {
    private final Blockchain blockchain = new Blockchain();

    public List<Block> getChain() {
        return blockchain.getBlocks();
    }
    
    public Block mineBlock(String data) {
        blockchain.addBlock(data);
        return blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
    }

    public boolean isValid() {
        return blockchain.isValid();
    }
}