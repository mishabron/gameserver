package com.mbronshteyn.gameserver.service;

import com.mbronshteyn.data.cards.*;
import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import com.mbronshteyn.data.cards.repository.BonusPinRepository;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.audit.GameAuditorConfig;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.dto.card.BonusGenDto;
import com.mbronshteyn.gameserver.dto.game.*;
import com.mbronshteyn.gameserver.exception.ErrorCode;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.impl.CardServiceImpl;
import com.mbronshteyn.gameserver.services.impl.EmailServiceImpl;
import com.mbronshteyn.gameserver.services.impl.GameServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@DataJpaTest
public class CardServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Autowired
    @InjectMocks
    EmailServiceImpl emailService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CardServiceImpl cardService;

    @Autowired
    @InjectMocks
    GameServiceImpl gameServiceImpl;

    @Autowired
    CardBatchRepository cardBatchRepository;

    @Autowired
    BonusPinRepository bonusPinRepository;

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
    private CardBatch batch;

    @Before
    @Transactional
    public void setup() throws GameServerException {

        game = new Game();
        game.setName("Pingo");

        gameRepository.saveAndFlush(game);

        BatchDto batchDto = new BatchDto();
        batchDto.setGameName("Pingo");
        batchDto.setNumberOfCards(100);
        batchDto.setPayout1(new BigDecimal(100.0));
        batchDto.setPayout2(new BigDecimal(50.0));
        batchDto.setPayout3(new BigDecimal(30.0));

        batch = cardService.generateCardsForBatch(batchDto);
        barcode = batch.getBarcode();
        cardBarcode = batch.getCards().stream().findFirst().get().getBarcode();
        cardId = batch.getCards().stream().findFirst().get().getId();

        //setup bonus pins
        BonusPin bonusPin = new BonusPin();
        AuxiliaryPinId id = new AuxiliaryPinId(batch,2,1);
        bonusPin.setId(id);
        bonusPin.setActive(true);
        bonusPin.setUsed(false);
        bonusPinRepository.saveAndFlush(bonusPin);

        Mockito.when(securityUser.getUser()).thenReturn("TestUser");
    }

    @Test
    public void testBatchGeneration() throws GameServerException {

        BatchDto batchDto = new BatchDto();
        batchDto.setGameName("Pingo");
        batchDto.setNumberOfCards(1000);
        batchDto.setNumberOfBonusPins1(50);
        batchDto.setNumberOfSuperPins1(20);

        CardBatch batch = cardService.generateCardsForBatch(batchDto);
        CardBatch testBatch = cardBatchRepository.findByBarcode(batch.getBarcode());

        Assert.assertEquals(batch,testBatch);
    }

    @Test
    public void testBonusGeneration() throws GameServerException {

        BonusGenDto bonusGenDto = new BonusGenDto();
        bonusGenDto.setBatchId(batch.getId());
        bonusGenDto.setNumberOfBonusPins1(100);
        bonusGenDto.setNumberOfSuperPins1(0);
        bonusGenDto.setNumberOfBonusPins2(0);
        bonusGenDto.setNumberOfSuperPins2(100);
        bonusGenDto.setNumberOfBonusPins3(60);
        bonusGenDto.setNumberOfSuperPins3(40);
        cardService.generateBonuses(bonusGenDto);

        CardBatch testBatch = cardBatchRepository.findByBarcode(batch.getBarcode());

        int bonus1 = Math.toIntExact(testBatch.getBonusPins().stream().filter(b -> b.getId().getSequence() == 1).count());
        int bonus2 = Math.toIntExact(testBatch.getBonusPins().stream().filter(b -> b.getId().getSequence() == 2).count());
        int bonus3 = Math.toIntExact(testBatch.getBonusPins().stream().filter(b -> b.getId().getSequence() == 3).count());

        Assert.assertEquals(100,bonus1);
        Assert.assertEquals(0,bonus2);
        Assert.assertEquals(60,bonus3);

        int super1 = Math.toIntExact(testBatch.getSuperPins().stream().filter(b -> b.getId().getSequence() == 1).count());
        int super2 = Math.toIntExact(testBatch.getSuperPins().stream().filter(b -> b.getId().getSequence() == 2).count());
        int super3 = Math.toIntExact(testBatch.getSuperPins().stream().filter(b -> b.getId().getSequence() == 3).count());

        Assert.assertEquals(0,super1);
        Assert.assertEquals(100,super2);
        Assert.assertEquals(40,super3);

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

        for(BonusPin  bonusPin: testBatch.getBonusPins()){
            Assert.assertNotEquals(activeCard.getWinPin(),bonusPin.getId().getPin());
        }
        for(SuperPin superPin: testBatch.getSuperPins()){
            Assert.assertNotEquals(activeCard.getWinPin(),superPin.getId().getPin());
        }

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
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(3,4))+1);
        CardDto hitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertTrue(hitCard.getHits().get(0).getNumber_3().isGuessed());
        Assert.assertFalse(hitCard.getHits().get(0).getNumber_4().isGuessed());
        Assert.assertEquals(hitCard.getHits().get(0).getSequence(),1);

    }

    @Test(expected = GameServerException.class)
    public void testGetWinningPinFail() throws GameServerException {

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

        String pin = gameServiceImpl.getWinningPin(cardHitDto);

    }

    @Test
    public void testGetWinningPinSuccess() throws GameServerException {

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
        hitCard = gameServiceImpl.hitCard(cardHitDto);
        hitCard = gameServiceImpl.hitCard(cardHitDto);
        hitCard = gameServiceImpl.hitCard(cardHitDto);

        String pin = gameServiceImpl.getWinningPin(cardHitDto);

        Assert.assertEquals(pin,winPin);
    }

    @Test
    public void testFreeGame() throws GameServerException {

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
        hitCard = gameServiceImpl.hitCard(cardHitDto);
        hitCard = gameServiceImpl.hitCard(cardHitDto);

        //hit last attempt with winning pin
        cardHitDto.setHit1(Integer.parseInt(winPin.substring(0,1)));
        cardHitDto.setHit2(Integer.parseInt(winPin.substring(1,2)));
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(3,4)));
        CardDto lastHitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertTrue(lastHitCard.isPlayed());

        CardDto freeGameCard = gameServiceImpl.logingCard(authDto);
        Assert.assertTrue(!freeGameCard.isPlayed());
        Assert.assertEquals(freeGameCard.getNumberOfHits(),0);
        Assert.assertEquals(freeGameCard.getHits().size(),0);

        cardHitDto.setHit1(8);
        cardHitDto.setHit2(1);
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(1);
        hitCard = gameServiceImpl.hitCard(cardHitDto);
        Assert.assertEquals(hitCard.getNumberOfHits(),1);

    }

    @Test
    public void testWinGame() throws GameServerException {

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
        //hit last attempt with winning pin
        cardHitDto.setHit1(Integer.parseInt(winPin.substring(0,1)));
        cardHitDto.setHit2(Integer.parseInt(winPin.substring(1,2)));
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(3,4)));
        CardDto lastHitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertTrue(gameServiceImpl.isWinnig(activeCard));

        authCard = gameServiceImpl.logingCard(authDto);
        Assert.assertNotNull(authCard);
        Assert.assertTrue(authCard.isPlayed());
    }

    @Test
    public void testWinnerEmail() throws GameServerException {

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
        //hit last attempt with winning pin
        cardHitDto.setHit1(Integer.parseInt(winPin.substring(0,1)));
        cardHitDto.setHit2(Integer.parseInt(winPin.substring(1,2)));
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(3,4)));
        CardDto lastHitCard = gameServiceImpl.hitCard(cardHitDto);

        WinnerEmailDto dto = new WinnerEmailDto();
        dto.setCardNumber(activeCard.getCardNumber());
        dto.setDeviceId("123");
        dto.setGame("Pingo");
        dto.setEmail("test@test.com");
        gameServiceImpl.saveEmail(dto);
    }

    @Test(expected = GameServerException.class)
    public void testWinnerEmail_NotPlayed() throws GameServerException {

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
        //hit last attempt with winning pin
        cardHitDto.setHit1(Integer.parseInt(winPin.substring(0,1)));
        cardHitDto.setHit2(Integer.parseInt(winPin.substring(1,2)));
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(0,1)));
        CardDto lastHitCard = gameServiceImpl.hitCard(cardHitDto);

        WinnerEmailDto dto = new WinnerEmailDto();
        dto.setCardNumber(activeCard.getCardNumber());
        dto.setDeviceId("123");
        dto.setGame("Pingo");
        dto.setEmail("test#test.com");
        dto.setEmail("misha_bronshteyn@hotmail.com");
        gameServiceImpl.saveEmail(dto);
    }

    @Test
    public void testBonusHitCard() throws GameServerException {

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
        Assert.assertNull(authCard.getBonusPin());

        //hit card 1
        CardHitDto cardHitDto = new CardHitDto();
        cardHitDto.setCardNumber(activeCard.getCardNumber());
        cardHitDto.setDeviceId("123");
        cardHitDto.setGame("Pingo");
        cardHitDto.setHit1(null);
        cardHitDto.setHit2(1);
        cardHitDto.setHit3(Integer.parseInt(winPin.substring(2,3)));
        cardHitDto.setHit4(Integer.parseInt(winPin.substring(3,4))+1);
        CardDto hitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertNotNull(hitCard);
        Assert.assertNull(hitCard.getBonusPin());

        //hit card 2
        cardHitDto = new CardHitDto();
        cardHitDto.setCardNumber(activeCard.getCardNumber());
        cardHitDto.setDeviceId("123");
        cardHitDto.setGame("Pingo");
        cardHitDto.setHit1(3);
        cardHitDto.setHit2(4);
        cardHitDto.setHit3(5);
        cardHitDto.setHit4(6);
        CardDto bonosHitCard = gameServiceImpl.hitCard(cardHitDto);

        Assert.assertNotNull(bonosHitCard);
        Assert.assertEquals(Bonus.BONUSPIN,bonosHitCard.getBonusPin());

        BonusPin bonus = bonusPinRepository.findOne(new AuxiliaryPinId(batch, 2, 1));
        Assert.assertNotNull(bonus);
        Assert.assertEquals(activeCard.getCardNumber(),bonus.getCardNumber());
        Assert.assertTrue(bonus.isUsed());
        Assert.assertFalse(bonus.isActive());

        //login card
        CardDto loginCard = gameServiceImpl.logingCard(authDto);
        Assert.assertNull(loginCard.getBonusPin());

        //hit card 3
        cardHitDto = new CardHitDto();
        cardHitDto.setCardNumber(activeCard.getCardNumber());
        cardHitDto.setDeviceId("123");
        cardHitDto.setGame("Pingo");
        cardHitDto.setBonus(bonosHitCard.getBonusPin());
        cardHitDto.setBonusHit(true);
        cardHitDto.setHit1(3);
        cardHitDto.setHit2(4);
        cardHitDto.setHit3(5);
        cardHitDto.setHit4(6);
        CardDto bonusCard = gameServiceImpl.hitCard(cardHitDto);
        Assert.assertEquals(3,bonusCard.getHits().size());
        Assert.assertEquals(Bonus.BONUSPIN,bonusCard.getHits().get(2).getBonusHit());

        CardDto nonBonusCard = gameServiceImpl.logingCard(authDto);
        Assert.assertNull(nonBonusCard.getBonusPin());
        Assert.assertEquals(3,nonBonusCard.getHits().size());
        Assert.assertEquals(Bonus.BONUSPIN,nonBonusCard.getHits().get(2).getBonusHit());
    }

    @Test
    public void testLogin() throws GameServerException {

        ErrorCode errorCode  = null;

        //login card
        AuthinticateDto authDto = new AuthinticateDto();
        authDto.setDeviceId("123");
        authDto.setGame("Pingo");
        authDto.setCardNumber(5555L);
        try {
            CardDto authCard = gameServiceImpl.logingCard(authDto);
        } catch (GameServerException e) {
            e.printStackTrace();
            errorCode = e.getErrorCode();
        }
        Assert.assertNotNull(errorCode);
        Assert.assertEquals(ErrorCode.INVALID,errorCode);
    }
}
