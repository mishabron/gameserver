package com.mbronshteyn.gameserver.services.helper;

import com.mbronshteyn.data.cards.*;
import com.mbronshteyn.data.cards.keys.AuxiliaryPinId;
import com.mbronshteyn.data.cards.repository.BoosterPinRepository;
import com.mbronshteyn.data.cards.repository.SuperPinRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PinHelper {

    @Autowired
    SuperPinRepository superPinRepository;
    @Autowired
    BoosterPinRepository soosterPinRepository;
    @Autowired
    BoosterPinRepository boosterPinRepository;

    public PinHelper() {
    }

    public Set<BonusPin> generateBulkBonusPins(CardBatch batch, List<String> pinsBonus){

        HashSet<BonusPin> bonusPins = new HashSet<>();

        for(String pin: pinsBonus){
            BonusPin bonusPin = new BonusPin();
            AuxiliaryPinId id = new AuxiliaryPinId(batch,pin);
            bonusPin.setId(id);
            bonusPin.setActive(true);
            bonusPin.setUsed(false);
            bonusPins.add(bonusPin);
        }

        return bonusPins;
    }

    public Set<BoosterPin> generateBulkBoosterPins(CardBatch batch,List<String> pinsBooster ){

        HashSet<BoosterPin> boosterPins = new HashSet<>();

        for(String pin: pinsBooster){
            BoosterPin boosterPin = new BoosterPin();
            AuxiliaryPinId id = new AuxiliaryPinId(batch,pin);
            boosterPin.setId(id);
            boosterPin.setActive(true);
            boosterPin.setUsed(false);
            boosterPins.add(boosterPin);
        }

        return boosterPins;
    }

    public Set<SuperPin> generateBulkSuperPins(CardBatch batch, List<String> pinsSuper ){

        HashSet<SuperPin> superPins = new HashSet<>();

        for(String pin: pinsSuper){
            SuperPin superPin = new SuperPin();
            AuxiliaryPinId id = new AuxiliaryPinId(batch,pin);
            superPin.setId(id);
            superPin.setActive(true);
            superPin.setUsed(false);
            superPins.add(superPin);
        }

        return superPins;
    }

    public Set<Card> generateBulkCard(CardBatch batch, int numberOfCards) {

        Set<Card> cards = new HashSet<>();

        long[] cardNumbers = ThreadLocalRandom.current().longs(100000000000L, 999999999999L).distinct().limit(numberOfCards).toArray();

        for(long cardNUmber : cardNumbers){
            //setup card
            Card card = new Card();
            card.setCardNumber(cardNUmber);
            card.setBarcode(RandomStringUtils.randomAlphanumeric(7));
            card.setGameId(batch.getGame_id());
            card.setBatch(batch);
            card.setActive(false);
            card.setPlayed(false);
            card.setPlayed(false);
            card.setPrice(batch.getPayout1());
            card.setNumberOfHits(0);

            cards.add(card);
        }

        return cards;
    }

    public List<String>  generateUniquePins(int numberOfPins){

        return ThreadLocalRandom.current().ints(0, 9999).distinct().limit(numberOfPins).mapToObj(p -> String.format("%04d", p)).collect(Collectors.toList());
    }

    public String generareUniquePin(Card card) {

        CardBatch batch = card.getBatch();
        
        String uniquePin = RandomStringUtils.randomNumeric(4);
        while(superPinRepository.findOne(new AuxiliaryPinId(batch,uniquePin)) != null &&
                soosterPinRepository.findOne(new AuxiliaryPinId(batch,uniquePin)) != null &&
                boosterPinRepository.findOne(new AuxiliaryPinId(batch,uniquePin)) != null){
            uniquePin = RandomStringUtils.randomNumeric(4);
        }

        return uniquePin;
    }
}
