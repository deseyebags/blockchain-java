package com.demo.blockchain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void recalculateHashMatchesStoredHash() {
        Block b = new Block(1, "payload", "0");
        assertEquals(b.getHash(), b.recalculateHash());
    }

    @Test
    void mineBlockProducesPrefix() throws Exception {
        Block b = new Block(2, "payload", "0");
        b.mineBlock(2);
        assertTrue(b.getHash().startsWith("00"));
    }
}
