package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    public Game findByName(String name);
}
