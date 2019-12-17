package com.codeup.blog.blog.repositories;

import com.codeup.blog.blog.models.Game;
import com.codeup.blog.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {


}