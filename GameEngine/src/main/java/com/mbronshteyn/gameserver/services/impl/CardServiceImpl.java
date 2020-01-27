package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.Play;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.CardRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
import com.mbronshteyn.gameserver.services.CardService;
import com.mbronshteyn.gameserver.services.helper.PinHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    PinHelper pinHelper;

    @Autowired
    CardBatchRepository cardBatchRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    SecurityUser securityUser;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    @Transactional
    public CardBatch generateCardsForBatch(BatchDto batchDto) throws GameServerException {

        Game storedGame = gameRepository.findByName(batchDto.getGameName());

        //create new batch
        CardBatch batch = new CardBatch();
        batch.setGame_id(storedGame.getId());
        batch.setBatchdate(new Date());
        batch.setBarcode(RandomStringUtils.randomAlphanumeric(7));
        batch.setCardsInBatch(batchDto.getNumberOfCards());
        batch.setCardPrice(batchDto.getCardPrice());
        batch.setFreeGame(batchDto.getFreeGame());
        batch.setPayout1(batchDto.getPayout1());
        batch.setPayout2(batchDto.getPayout2());
        batch.setPayout3(batchDto.getPayout3());

        //generate total number of unique positions for request1
        int request1BonusPins = batchDto.getNumberOfBonusPins1() * batchDto.getNumberOfCards() / 100;
        int request1SuperPins = batchDto.getNumberOfSuperPins1() * batchDto.getNumberOfCards() / 100;
        if((request1BonusPins != 0 || request1SuperPins !=0) && request1BonusPins <batchDto.getNumberOfCards() && request1SuperPins <batchDto.getNumberOfCards()){
            List<Integer> bonusPositionsRequest1 = pinHelper.generateUniquePins(batchDto.getNumberOfCards(), request1BonusPins + request1SuperPins);
            //split generated pins into groups
            List<Integer> pinsBonus = bonusPositionsRequest1.subList(0, request1BonusPins);
            List<Integer> pinsSuper = bonusPositionsRequest1.subList(request1BonusPins, bonusPositionsRequest1.size());

            LOGGER.info("Genereating Bonus Pins 1");
            batch.setBonusPins(pinHelper.generateBulkBonusPins(batch, pinsBonus, 1));
            LOGGER.info("Genereating Super Pins 1");
            batch.setSuperPins(pinHelper.generateBulkSuperPins(batch, pinsSuper, 1));
        }

        //generate total number of unique positions for request2
        int request2BonusPins = batchDto.getNumberOfBonusPins2() * batchDto.getNumberOfCards() / 100;
        int request2SuperPins = batchDto.getNumberOfSuperPins2() * batchDto.getNumberOfCards() / 100;
        if((request2BonusPins != 0 || request2SuperPins !=0) && request2BonusPins <batchDto.getNumberOfCards() && request2SuperPins <batchDto.getNumberOfCards()){
            List<Integer> bonusPositionsRequest2 = pinHelper.generateUniquePins(batchDto.getNumberOfCards(), request2BonusPins + request2SuperPins);
            //split generated pins into groups
            List<Integer>  pinsBonus = bonusPositionsRequest2.subList(0, request2BonusPins);
            List<Integer>  pinsSuper = bonusPositionsRequest2.subList(request2BonusPins, bonusPositionsRequest2.size());

            LOGGER.info("Genereating Bonus Pins 2");
            batch.getBonusPins().addAll(pinHelper.generateBulkBonusPins(batch, pinsBonus, 2));
            LOGGER.info("Genereating Super Pins 2");
            batch.getSuperPins().addAll(pinHelper.generateBulkSuperPins(batch, pinsSuper, 2));
        }

        //generate total number of unique positions for request3
        int request3BonusPins = batchDto.getNumberOfBonusPins3() * batchDto.getNumberOfCards() / 100;
        int request3SuperPins = batchDto.getNumberOfSuperPins3() * batchDto.getNumberOfCards() / 100;
        if((request3BonusPins != 0 || request3SuperPins !=0) && request3BonusPins <batchDto.getNumberOfCards() && request3SuperPins <batchDto.getNumberOfCards()){
            List<Integer> bonusPositionsRequest3 = pinHelper.generateUniquePins(batchDto.getNumberOfCards(), request3BonusPins + request3SuperPins);
            //split generated pins into groups
            List<Integer> pinsBonus = bonusPositionsRequest3.subList(0, request3BonusPins);
            List<Integer> pinsSuper = bonusPositionsRequest3.subList(request3BonusPins, bonusPositionsRequest3.size());

            LOGGER.info("Genereating Bonus Pins 3");
            batch.getBonusPins().addAll(pinHelper.generateBulkBonusPins(batch, pinsBonus, 3));
            LOGGER.info("Genereating Super Pins 3");
            batch.getSuperPins().addAll(pinHelper.generateBulkSuperPins(batch, pinsSuper, 3));
        }

        //generate total number of unique positions for request4
        int request4BonusPins = batchDto.getNumberOfBonusPins4() * batchDto.getNumberOfCards() / 100;
        int request4SuperPins = batchDto.getNumberOfSuperPins4() * batchDto.getNumberOfCards() / 100;
        if((request4BonusPins != 0 || request4SuperPins !=0) && request4BonusPins <batchDto.getNumberOfCards() && request4SuperPins <batchDto.getNumberOfCards()){
            List<Integer> bonusPositionsRequest4 = pinHelper.generateUniquePins(batchDto.getNumberOfCards(), request4BonusPins + request4SuperPins);
            //split generated pins into groups
            List<Integer> pinsBonus = bonusPositionsRequest4.subList(0, request4BonusPins);
            List<Integer> pinsSuper = bonusPositionsRequest4.subList(request3BonusPins, bonusPositionsRequest4.size());

            LOGGER.info("Genereating Bonus Pins 4");
            batch.getBonusPins().addAll(pinHelper.generateBulkBonusPins(batch, pinsBonus, 4));
            LOGGER.info("Genereating Super Pins 4");
            batch.getSuperPins().addAll(pinHelper.generateBulkSuperPins(batch, pinsSuper, 4));
        }

        LOGGER.info("Genereating Cards");
        batch.setCards(pinHelper.generateBulkCard(batch,batchDto.getNumberOfCards()));

        LOGGER.info("Persisting Batch Data");
        CardBatch storedBatch = cardBatchRepository.saveAndFlush(batch);

        return storedBatch;

    }

    @Override
    @Transactional
    public Card activateCard(String barcode) throws GameServerException {
        
        //get a card by barcode #
        Card card = cardRepository.findByBarcode(barcode);
        if(card == null){
            throw new GameServerException("Card not found");
        }
        if(card.isActive()){
            throw new GameServerException("Card is already active");
        }
        if(card.isPlayed()){
            throw new GameServerException("Card has been played already");
        }

        card.setActive(true);

        Play play = new Play();
        play.setPlayNumber(0);
        play.setCard(card);
        card.getPlays().add(play);
        card.setWinPin(pinHelper.generateUniquePin(card));
        card.setActivateDate(new Date());
        card.setActivateBy(securityUser.getUser());

        card = cardRepository.save(card);

        return card;
    }

    @Override
    @Transactional
    public CardBatch activateBatch(String barcode) throws GameServerException {

        //get a card by barcode #
        Card firstCard = cardRepository.findByBarcode(barcode);

        if(firstCard == null){
            throw new GameServerException("Card not found");
        }

        CardBatch batch = firstCard.getBatch();

        if(batch == null){
            throw new GameServerException("Batch not found");
        }

        for(Card card: batch.getCards()){
            activateCard(card.getBarcode());
        }

        return batch;
    }

}
