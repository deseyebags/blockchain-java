package com.demo.blockchain.services;

import com.demo.blockchain.models.Block;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainServiceTest {

    @Test
    void mineBlockReturnsNewBlockAndServiceReportsValid() {
        BlockchainService svc = new BlockchainService();
        Block b = svc.mineBlock("service-tx");
        assertNotNull(b);
        assertTrue(svc.isValid());
    }
}
