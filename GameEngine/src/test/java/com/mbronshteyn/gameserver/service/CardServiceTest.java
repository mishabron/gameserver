package com.mbronshteyn.gameserver.service;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.audit.GameAuditorConfig;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.dto.game.AuthinticateDto;
import com.mbronshteyn.gameserver.dto.game.CardDto;
import com.mbronshteyn.gameserver.dto.game.CardHitDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.impl.CardServiceImpl;
import com.mbronshteyn.gameserver.services.impl.GameServiceImpl;
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

import java.math.BigDecimal;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CardServiceTest {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    GameServiceImpl gameServiceImpl;

    @Autowired
    CardBatchRepository cardBatchRepository;

    @Autowired
    CardRepository cardRepository;

    @MockBean
    SecurityUser securityUser;

    @InjectMocks
    GameAuditorConfig gameAuditorConfig;

    private Game game;
    private String barcode;
    private String cardBarcode;
    private int cardId;

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
        batchDto.setPayout1(new BigDecimal(100.0));
        batchDto.setPayout2(new BigDecimal(50.0));
        batchDto.setPayout3(new BigDecimal(30.0));

        CardBatch batch = cardService.generateCardsForBatch(batchDto);
        barcode = batch.getBarcode();
        cardBarcode = batch.getCards().stream().findFirst().get().getBarcode();
        cardId = batch.getCards().stream().findFirst().get().getId();

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
    public void testBatch() throws GameServerException {
        CardBatch testBatch = cardBatchRepository.findByBarcode(barcode);
        Set<Card> cards = testBatch.getCards();
    }

    @Test
    public void testActivateCard() throws GameServerException {

        CardBatch testBatch = cardBatchRepository.findByBarcode(barcode);
        Set<Card> cards = testBatch.getCards();

        Card card = cards.stream().findFirst().get();
        Assert.assertTrue(!card.isActive());

        String bardcode = card.getBarcode();
        Card activeCard = cardService.activateCard(bardcode);
        Assert.assertTrue(activeCard.isActive());

    }

    @Test
    public void testActivateBatch() throws GameServerException {
        CardBatch testBatch = cardService.activateBatch(cardBarcode);

        Set<Card> cards = testBatch.getCards();

        for(Card card: cards){
            Assert.assertTrue(card.isActive());
        }
    }

    @Test
    public void testHitCard() throws GameServerException {

        //activate card
        Card activeCard = cardService.activateCard(cardBarcode);
        Assert.assertNotNull(activeCard);
        Assert.assertTrue(activeCard.isActive());
        String winPin = activeCard.getWinPin();

        //login card
        AuthinticateDto authDto = new AuthinticateDto();
        authDto.setDeviceId("123");
        authDto.setGame("Pingo");
        authDto.setCardNumber(activeCard.getCardNumber());
        CardDto authCard = gameServiceImpl.logingCard(authDto);
        Assert.assertNotNull(authCard);
        
        //hit card
        CardHitDto cardHitDto = new CardHitDto();
        cardHitDto.setCardNumber(activeCard.getCardNumber());
        cardHitDto.setDeviceId("123");
        cardHitDto.setGame("Pingo");
        cardHitDto.setHit1(null);
        cardHitDto.setHit2(1);
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(1);
        CardDto hitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertTrue(hitCard.getHits().get(0).getNumber_3().isGuessed());
        Assert.assertEquals(hitCard.getHits().get(0).getSequence(),1);

    }
}
