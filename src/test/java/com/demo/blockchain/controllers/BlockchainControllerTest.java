package com.demo.blockchain.controllers;

import com.demo.blockchain.models.Block;
import com.demo.blockchain.services.BlockchainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlockchainController.class)
class BlockchainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BlockchainService service;

    @Test
    void getChainReturnsJsonArray() throws Exception {
        when(service.getChain()).thenReturn(Collections.singletonList(new Block(1, "hello", "0")));

        mvc.perform(get("/api/blockchain/chain").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data").value("hello"));
    }
}
