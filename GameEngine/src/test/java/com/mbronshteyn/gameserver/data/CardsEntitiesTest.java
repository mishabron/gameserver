package com.mbronshteyn.gameserver.data;


import com.mbronshteyn.data.cards.*;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.data.cards.repository.HitPinRepository;
import com.mbronshteyn.gameserver.audit.GameAuditorConfig;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.services.helper.PinHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration()
@WebAppConfiguration
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class CardsEntitiesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CardBatchRepository cardBatchRepository;

    @Autowired
    HitPinRepository hitPinRepository;

    @Autowired
    PinHelper pinHelper;

    @MockBean
    SecurityUser securityUser;

    @InjectMocks
    GameAuditorConfig gameAuditorConfig;

    private Game game;

    @Before
    public void setup(){

        game = new Game();
        game.setName("Pingo");

        entityManager.persist(game);
        entityManager.flush();

        Mockito.when(securityUser.getUser()).thenReturn("TestUser");
    }

    @Test
    public void testGameCRUD(){

        Game storedGame = gameRepository.findByName(game.getName());

        assertEquals(game.getId(),storedGame.getId());
    }
    @Test
    public void testPinGenerator(){

        int bonusPins = 10;
        int boosterPins = 15;
        int superPins = 5;

        List<String> generatedPins = pinHelper.generateUniquePins(bonusPins + boosterPins + superPins);

        List<String> pinsBonus = generatedPins.subList(0, bonusPins);
        List<String> pinsBooster = generatedPins.subList(bonusPins, bonusPins + boosterPins);
        List<String> pinsSuper = generatedPins.subList(bonusPins + boosterPins, generatedPins.size());

        assertEquals(pinsBonus.size(),bonusPins);
        assertEquals(pinsBooster.size(),boosterPins);
        assertEquals(pinsSuper.size(),superPins);
    }

    @Test
    public void testCardBatchCRUD(){

        String testEmail = "test@test.com";

        CardBatch batch = new CardBatch();
        Game storedGame = gameRepository.findByName(game.getName());
        batch.setGame_id(storedGame.getId());
        batch.setBatchdate(new Date());
        batch.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        batch.setCardsInBatch(1);

        //setup card
        Card card = new Card();
        Play play = new Play();
        play.setPlayNumber(0);
        play.setCard(card);
        card.getPlays().add(play);
        card.setCardNumber(Long.parseLong(RandomStringUtils.randomNumeric(12)));
        card.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        card.setGameId(storedGame.getId());
        card.setWinPin(RandomStringUtils.randomNumeric(4));
        batch.addCard(card);

        int bonusPins = 10;
        int boosterPins = 15;
        int superPins = 5;

        List<String> generatedPins = pinHelper.generateUniquePins(bonusPins + boosterPins + superPins);

        List<String> pinsBonus = generatedPins.subList(0, bonusPins);
        List<String> pinsSuper = generatedPins.subList(bonusPins + boosterPins, generatedPins.size());

        batch.setBonusPins(pinHelper.generateBulkBonusPins(batch,pinsBonus));
        batch.setSuperPins(pinHelper.generateBulkSuperPins(batch,pinsSuper));

        CardBatch storedBatch = cardBatchRepository.saveAndFlush(batch);

        assertNotNull(storedBatch);

        CardBatch testBatch = cardBatchRepository.findByBarcode(batch.getBarcode());

        assertNotNull(testBatch);
        assertEquals(testBatch,storedBatch);
        assertEquals(testBatch.getBonusPins().size(),10);
        assertEquals(testBatch.getSuperPins().size(),5);
        assertEquals(testBatch.getUpdateBy(),"TestUser");

        Card card1 = cardRepository.findByBarcode(card.getBarcode());
        Card card2 = cardRepository.findByCardNumber(card.getCardNumber());
        assertEquals(card1,card2);

        //make a hit
        Hit hit1 = new Hit();
        hit1.setNumber_1(3);
        hit1.setNumber_2(0);
        hit1.setNumber_3(5);
        hit1.setNumber_4(8);
        card1.addHit(hit1);
        card1.setEmail(testEmail);

        Card card3 = cardRepository.saveAndFlush(card1);

        Hit hit2 = new Hit();
        hit2.setNumber_1(3);
        hit2.setNumber_2(0);
        hit2.setNumber_3(5);
        card3.addHit(hit2);

        Card card4 = cardRepository.saveAndFlush(card3);

        Hit hit3 = new Hit();
        hit3.setNumber_1(3);
        hit3.setNumber_2(0);
        hit3.setNumber_3(5);
        hit3.setNumber_4(7);
        card4.addHit(hit3);

        cardRepository.saveAndFlush(card4);

        Card testCard = cardRepository.findByCardNumber(card.getCardNumber());
        List<Hit> hits = testCard.getLastPlay().getHits();
        assertTrue(hits.size() == 3);
        assertEquals(testCard.getEmail(),testEmail);

    }

    @Test
    public void testHitsCount(){

        //setu batch
        CardBatch batch = new CardBatch();
        Game storedGame = gameRepository.findByName(game.getName());
        batch.setGame_id(storedGame.getId());
        batch.setBatchdate(new Date());
        batch.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        batch.setCardsInBatch(1);

        //setup card1
        Card card1 = new Card();
        Play play1 = new Play();
        play1.setPlayNumber(0);
        play1.setCard(card1);
        card1.getPlays().add(play1);
        card1.setCardNumber(Long.parseLong(RandomStringUtils.randomNumeric(12)));
        card1.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        card1.setGameId(storedGame.getId());
        card1.setWinPin(RandomStringUtils.randomNumeric(4));
        batch.addCard(card1);
        CardBatch storedBatch = cardBatchRepository.saveAndFlush(batch);

        assertNotNull(storedBatch);

        //make a hit1
        Hit hit1 = new Hit();
        hit1.setBatch(storedBatch);
        hit1.setNumber_1(3);
        hit1.setNumber_2(0);
        hit1.setNumber_3(5);
        hit1.setNumber_4(8);
        hit1.setFirstPlay(true);
        card1.addHit(hit1);
        cardRepository.saveAndFlush(card1);

        //setup card2
        Card card2 = new Card();
        Play play2 = new Play();
        play2.setPlayNumber(0);
        play2.setCard(card2);
        card2.getPlays().add(play2);
        card2.setCardNumber(Long.parseLong(RandomStringUtils.randomNumeric(12)));
        card2.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        card2.setGameId(storedGame.getId());
        card2.setWinPin(RandomStringUtils.randomNumeric(4));
        batch.addCard(card2);

        //make a hit2
        Hit hit2 = new Hit();
        hit2.setBatch(storedBatch);
        hit2.setNumber_1(3);
        hit2.setNumber_2(0);
        hit2.setNumber_3(5);
        hit2.setNumber_4(8);
        hit2.setFirstPlay(true);
        card2.addHit(hit2);
        cardRepository.saveAndFlush(card2);

        int hits1 = hitPinRepository.countByFirstPlayAndSequenceAndBatch(true, 1, storedBatch);
        assertEquals(2,hits1);

        //make a hit3
        Hit hit3 = new Hit();
        hit3.setBatch(storedBatch);
        hit3.setNumber_1(3);
        hit3.setNumber_2(0);
        hit3.setNumber_3(5);
        hit3.setNumber_4(8);
        hit3.setFirstPlay(true);
        card2.addHit(hit3);
        cardRepository.saveAndFlush(card2);

        int hits2 = hitPinRepository.countByFirstPlayAndSequenceAndBatch(true, 2, storedBatch);
        assertEquals(1,hits2);

        int hits3 = hitPinRepository.countByFirstPlayAndSequenceAndBatch(true, 3, storedBatch);
        assertEquals(0,hits3);

        List<Hit> allhits = hitPinRepository.findAll();
    }

}
