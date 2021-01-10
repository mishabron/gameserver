package com.mbronshteyn.gameserver.dto.game;

import java.util.ArrayList;
import java.util.List;

public class HistoryDto {

    Long cardNumber;
    private List<PlayDto> plays = new ArrayList<>();

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<PlayDto> getPlays() {
        return plays;
    }

    public void setPlays(List<PlayDto> plays) {
        this.plays = plays;
    }

    public void addPlay(PlayDto playDto) {
        plays.add(playDto);
    }
}
