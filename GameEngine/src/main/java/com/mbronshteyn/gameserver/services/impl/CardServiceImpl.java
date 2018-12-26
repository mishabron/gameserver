package com.mbronshteyn.gameserver.services.impl;

import com.mbronshteyn.data.cards.Card;
import com.mbronshteyn.data.cards.CardBatch;
import com.mbronshteyn.data.cards.Game;
import com.mbronshteyn.data.cards.repository.CardBatchRepository;
import com.mbronshteyn.data.cards.repository.GameRepository;
import com.mbronshteyn.gameserver.dto.BatchDto;
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

        //generate total number of unique pins
        List<String> generatedPins = pinHelper.generateUniquePins(batchDto.getNumberOfBonusPins() + batchDto.getNumberOfBooserPins() + batchDto.getNumberOfSuperPins());

        //split generated pins into groups
        List<String> pinsBonus = generatedPins.subList(0, batchDto.getNumberOfBonusPins());
        List<String> pinsBooster = generatedPins.subList(batchDto.getNumberOfBonusPins(), batchDto.getNumberOfBonusPins() + batchDto.getNumberOfBooserPins());
        List<String> pinsSuper = generatedPins.subList(batchDto.getNumberOfBonusPins() + batchDto.getNumberOfBooserPins(), generatedPins.size());

        LOGGER.info("Genereating Bonus Pins");
        batch.setBonusPins(pinHelper.generateBulkBonusPins(batch,pinsBonus));
        LOGGER.info("Genereating Booser Pins");
        batch.setBoosterPins(pinHelper.generateBulkBoosterPins(batch,pinsBooster));
        LOGGER.info("Genereating Super Pins");
        batch.setSuperPins(pinHelper.generateBulkSuperPins(batch,pinsSuper));

        LOGGER.info("Genereating Cards");
        batch.setCards(pinHelper.generateBulkCard(batch,batchDto.getNumberOfCards()));

        LOGGER.info("Persisting Batch Data");
        CardBatch storedBatch = cardBatchRepository.saveAndFlush(batch);

        return storedBatch;

    }
}
