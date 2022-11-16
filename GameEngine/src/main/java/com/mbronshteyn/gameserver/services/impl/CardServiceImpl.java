package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.Play;
import com.mbronshteyn.data.cards.repository.*;
import com.mbronshteyn.gameserver.audit.SecurityUser;
import com.mbronshteyn.gameserver.dto.card.BatchDto;
import com.mbronshteyn.gameserver.dto.card.BonusGenDto;
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
import java.util.ArrayList;
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

    @Autowired
    BonusPinRepository bonusPinRepository;

    @Autowired
    SuperPinRepository superPinRepository;
    
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

        LOGGER.info("Genereating Cards");
        batch.setCards(pinHelper.generateBulkCard(batch,batchDto.getNumberOfCards()));

        BonustData bonustData = new BonustData(
                batchDto.getNumberOfBonusPins1(),
                batchDto.getNumberOfBonusPins2(),
                batchDto.getNumberOfBonusPins3(),
                batchDto.getNumberOfSuperPins1(),
                batchDto.getNumberOfSuperPins2(),
                batchDto.getNumberOfSuperPins3());
        batch = generateBonuses(batch,bonustData);

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

    @Override
    @Transactional
    public void generateBonuses(BonusGenDto bonusGenDto) throws GameServerException{

        //retrieve the batch
        CardBatch cardBarch = cardBatchRepository.findOne(bonusGenDto.getBatchId());
        if(cardBarch == null){
            throw new GameServerException("Batch ID: "+bonusGenDto.getBatchId()+" Not Found");
        }

        //clear existing bonuses
        bonusPinRepository.deleteByBatchId(cardBarch.getId());
        superPinRepository.deleteByBatchId(cardBarch.getId());

        BonustData bonustData = new BonustData(
                bonusGenDto.getNumberOfBonusPins1(),
                bonusGenDto.getNumberOfBonusPins2(),
                bonusGenDto.getNumberOfBonusPins3(),
                bonusGenDto.getNumberOfSuperPins1(),
                bonusGenDto.getNumberOfSuperPins2(),
                bonusGenDto.getNumberOfSuperPins3());

        cardBarch = generateBonuses(cardBarch,bonustData);

        cardBatchRepository.saveAndFlush(cardBarch);
    }

    @Transactional
    private CardBatch generateBonuses(CardBatch batch, BonustData bonustData) throws GameServerException{

        //validate input
        if(bonustData.getNumberOfBonusPins1() + bonustData.getNumberOfBonusPins2() + bonustData.getNumberOfBonusPins3() > 100){
            throw new GameServerException("Number of requested BunusPins exceeds 100%");
        }
        if(bonustData.getNumberOfSuperPins1() + bonustData.getNumberOfSuperPins2() + bonustData.getNumberOfSuperPins3() > 100){
            throw new GameServerException("Number of requested SuperPins exceeds 100%");
        }
        if(bonustData.getNumberOfBonusPins1() >0 && bonustData.getNumberOfSuperPins1() >0){
            throw new GameServerException("Conflict between Bonus and Super pins for attemps #1");
        }
        if(bonustData.getNumberOfBonusPins2() >0 && bonustData.getNumberOfSuperPins2() >0){
            throw new GameServerException("Conflict between Bonus and Super pins for attemps #2");
        }
        if(bonustData.getNumberOfBonusPins3() >0 && bonustData.getNumberOfSuperPins3() >0){
            throw new GameServerException("Conflict between Bonus and Super pins for attemps #3");
        }

        int batchSize = batch.getCardsInBatch();
        int cardsPlayed = Math.toIntExact(batch.getCards().stream().filter(c -> c.isPlayed()).count());

        //generate total number of unique positions for bonusPins
        int request1BonusPins = bonustData.getNumberOfBonusPins1() * batchSize / 100;
        int request2BonusPins = bonustData.getNumberOfBonusPins2() * batchSize / 100;
        int request3BonusPins = bonustData.getNumberOfBonusPins3() * batchSize / 100;

        //generate total number of unique positions for superPins
        int request1SuperPins = bonustData.getNumberOfSuperPins1() * batchSize / 100;
        int request2SuperPins = bonustData.getNumberOfSuperPins2() * batchSize / 100;
        int request3SuperPins = bonustData.getNumberOfSuperPins3() * batchSize / 100;

        List<Integer> pinsBonus1 = new ArrayList<>();
        List<Integer> pinsBonus2 = new ArrayList<>();
        List<Integer> pinsBonus3 = new ArrayList<>();

        List<Integer> pinsSuperPins1 = new ArrayList<>();
        List<Integer> pinsSuperPins2 = new ArrayList<>();
        List<Integer> pinsSuperPins3 = new ArrayList<>();

        //generate bonusPins
        if((request1BonusPins + request2BonusPins + request3BonusPins) > 0 && (request1BonusPins + request2BonusPins + request3BonusPins) <= (batchSize - cardsPlayed)){

            List<Integer> bonusPositions = pinHelper.generateUniquePins(batchSize, cardsPlayed,request1BonusPins + request2BonusPins + request3BonusPins);
            //split generated pins into attempts groups
            pinsBonus1 = bonusPositions.subList(0, request1BonusPins);
            pinsBonus2 = bonusPositions.subList(request1BonusPins, request1BonusPins + request2BonusPins);
            pinsBonus3 = bonusPositions.subList(request1BonusPins + request2BonusPins, bonusPositions.size());
        }

        //generate superPins
        if((request1SuperPins + request2SuperPins + request3SuperPins) > 0 && (request1SuperPins + request2SuperPins + request3SuperPins) <= (batchSize - cardsPlayed)) {

            List<Integer> superPinsPositions = pinHelper.generateUniquePins(batchSize, cardsPlayed, request1SuperPins + request2SuperPins + request3SuperPins);
            //split generated pins into attempts groups
            pinsSuperPins1 = superPinsPositions.subList(0, request1SuperPins);
            pinsSuperPins2 = superPinsPositions.subList(request1SuperPins, request1SuperPins + request2SuperPins);
            pinsSuperPins3 = superPinsPositions.subList(request1SuperPins + request2SuperPins, superPinsPositions.size());

        }

        LOGGER.info("Genereating Bonus Pins");
        batch.setBonusPins(pinHelper.generateBulkBonusPins(batch, pinsBonus1, 1));
        batch.getBonusPins().addAll(pinHelper.generateBulkBonusPins(batch, pinsBonus2, 2));
        batch.getBonusPins().addAll(pinHelper.generateBulkBonusPins(batch, pinsBonus3, 3));

        LOGGER.info("Genereating Super Pins");
        batch.setSuperPins(pinHelper.generateBulkSuperPins(batch, pinsSuperPins1, 1));
        batch.getSuperPins().addAll(pinHelper.generateBulkSuperPins(batch, pinsSuperPins2, 2));
        batch.getSuperPins().addAll(pinHelper.generateBulkSuperPins(batch, pinsSuperPins3, 3));

        return batch;
    }

    private class BonustData{

        int numberOfBonusPins1;
        int numberOfBonusPins2;
        int numberOfBonusPins3;
        int numberOfSuperPins1;
        int numberOfSuperPins2;
        int numberOfSuperPins3;

        public BonustData(int numberOfBonusPins1, int numberOfBonusPins2, int numberOfBonusPins3, int numberOfSuperPins1, int numberOfSuperPins2, int numberOfSuperPins3) {
            this.numberOfBonusPins1 = numberOfBonusPins1;
            this.numberOfBonusPins2 = numberOfBonusPins2;
            this.numberOfBonusPins3 = numberOfBonusPins3;
            this.numberOfSuperPins1 = numberOfSuperPins1;
            this.numberOfSuperPins2 = numberOfSuperPins2;
            this.numberOfSuperPins3 = numberOfSuperPins3;
        }

        public int getNumberOfBonusPins1() {
            return numberOfBonusPins1;
        }

        public int getNumberOfBonusPins2() {
            return numberOfBonusPins2;
        }

        public int getNumberOfBonusPins3() {
            return numberOfBonusPins3;
        }

        public int getNumberOfSuperPins1() {
            return numberOfSuperPins1;
        }

        public int getNumberOfSuperPins2() {
            return numberOfSuperPins2;
        }

        public int getNumberOfSuperPins3() {
            return numberOfSuperPins3;
        }
    }
}
