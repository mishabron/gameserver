package com.mbronshteyn.gameserver.service;

import com.auth0.jwt.JWT;
import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.audit.GameAuditorConfig;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.dto.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.CardService;
import com.mbronshteyn.gameserver.services.impl.CardServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CardServiceTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    CardBatchRepository cardBatchRepository;

    @MockBean
    SecurityUser securityUser;

    @InjectMocks
    GameAuditorConfig gameAuditorConfig;

    private Game game;
    private String barcode;

    @Before
    @Transactional
    public void setup() throws GameServerException {

        game = new Game();
        game.setName("Pingo");

        gameRepository.saveAndFlush(game);

        BatchDto batchDto = new BatchDto();
        batchDto.setGameName("Pingo");
        batchDto.setNumberOfCards(10);
        batchDto.setNumberOfBonusPins(2);
        batchDto.setNumberOfBooserPins(15);
        batchDto.setNumberOfSuperPins(7);

        CardBatch batch = cardService.generateCardsForBatch(batchDto);
        barcode = batch.getBarcode();

        Mockito.when(securityUser.getUser()).thenReturn("TestUser");
    }

    @Test
    public void testBatchGeneration() throws GameServerException {

        BatchDto batchDto = new BatchDto();
        batchDto.setGameName("Pingo");
        batchDto.setNumberOfCards(1000);
        batchDto.setNumberOfBonusPins(500);
        batchDto.setNumberOfBooserPins(150);
        batchDto.setNumberOfSuperPins(200);

        CardBatch batch = cardService.generateCardsForBatch(batchDto);
        CardBatch testBatch = cardBatchRepository.findByBarcode(batch.getBarcode());

        Assert.assertEquals(batch,testBatch);
    }

    @Test
    @Transactional
    public void testBatc() throws GameServerException {
        CardBatch testBatch = cardBatchRepository.findByBarcode(barcode);
        Set<Card> cards = testBatch.getCards();
    }
}
