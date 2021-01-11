package com.mbronshteyn.gameserver.dto.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayDto implements Serializable {

    private int playNumber;
    List<HitDto> hits = new ArrayList<>();

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public List<HitDto> getHits() {
        return hits;
    }

    public void setHits(List<HitDto> hits) {
        this.hits = hits;
    }

    public void addHit(HitDto hitDto) {
        hits.add(hitDto);
    }
}
