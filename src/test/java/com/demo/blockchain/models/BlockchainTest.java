package com.demo.blockchain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {

    @Test
    void genesisExistsAndChainIsValid() {
        Blockchain chain = new Blockchain();
        assertFalse(chain.getBlocks().isEmpty());
        assertTrue(chain.isValid());
    }

    @Test
    void addingBlockIncreasesSizeAndKeepsValid() {
        Blockchain chain = new Blockchain();
        int before = chain.getBlocks().size();
        chain.addBlock("tx-data");
        assertEquals(before + 1, chain.getBlocks().size());
        assertTrue(chain.isValid());
    }

    @Test
    void addBlockRejectsEmptyData() {
        Blockchain chain = new Blockchain();
        assertThrows(IllegalArgumentException.class, () -> chain.addBlock("  "));
    }
}
