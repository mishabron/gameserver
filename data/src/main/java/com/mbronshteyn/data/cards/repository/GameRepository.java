package com.mbronshteyn.data.cards.repository;

import com.mbronshteyn.data.cards.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
