package com.demo.blockchain.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.demo.blockchain.models.Block;
import com.demo.blockchain.models.Blockchain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/blockchain")
public class BlockchainController {

    private final Blockchain blockchain;

    public BlockchainController() throws NoSuchAlgorithmException {
        this.blockchain = new Blockchain();   // constructor that throws
    }
    
    
    @GetMapping("/chain")
    public List<Block> getChain() {
        return blockchain.getBlocks();
    }
    
    @PostMapping("/mine")
    public ResponseEntity<Block> mineBlock(@RequestBody String data) throws Exception {
        blockchain.addBlock(data);
        Block lastBlock = blockchain.getBlocks().get(blockchain.getBlocks().size() - 1);
        return ResponseEntity.ok(lastBlock);
    }
    
    @GetMapping("/valid")
    public ResponseEntity<String> isValid() {
        return ResponseEntity.ok(blockchain.isValid() ? "VALID" : "INVALID");
    }
}
