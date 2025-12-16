package com.demo.blockchain.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.demo.blockchain.models.Block;
import com.demo.blockchain.models.Blockchain;
import com.demo.blockchain.services.BlockchainService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {
    private final BlockchainService service;
    
    public BlockchainController(BlockchainService service) {
        this.service = service;
    }
    
    @GetMapping("/chain")
    public List<Block> getChain() {
        return service.getChain();
    }
    
    @PostMapping("/mine")
    public ResponseEntity<?> mineBlock(@RequestBody String data)  {
       try {
            return ResponseEntity.ok(service.mineBlock(data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/valid")
    public ResponseEntity<String> isValid() {
        return ResponseEntity.ok(service.isValid() ? "VALID" : "INVALID");
    }
}
