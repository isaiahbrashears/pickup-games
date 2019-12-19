package com.codeup.blog.blog.controllers;


import com.codeup.blog.blog.models.Game;
import com.codeup.blog.blog.models.Sport;
import com.codeup.blog.blog.repositories.GameRepository;
import com.codeup.blog.blog.repositories.SportRepository;
import com.codeup.blog.blog.repositories.UserRepository;
import com.codeup.blog.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@Controller
public class GameController {

    private GameRepository gameDao;
    private UserRepository userDao;
    private SportRepository sportDao;

    public GameController(GameRepository gameDao, UserRepository userDao, SportRepository sportDao){
        this.gameDao = gameDao;
        this.userDao = userDao;
        this.sportDao = sportDao;
    }

    @Autowired
    private UserService userService;


    @GetMapping("/games/create")
    public String ShowCreateGame(Model vModel){
        vModel.addAttribute("game", new Game());
        vModel.addAttribute("sports", sportDao.findAll());
        return "games/create";
    }

    @PostMapping("/games/create")
    public String createGame(@ModelAttribute Game game, @RequestParam(name = "sport", required = false) Long sportID){
        Sport sport = sportDao.getOne(sportID);
        game.setSport(sport);
        gameDao.save(game);

        return "redirect:/";
    }

    @GetMapping("/games")
    public String viewAllGames(Model vModel){
        vModel.addAttribute("games", gameDao.findAll());

        return "games/index";
    }

    @GetMapping("games/ind/{id}")
    public String viewIndGame(@PathVariable long id, Model vModel){
        Game game = gameDao.getOne(id);
        vModel.addAttribute("game", game);

        return "games/ind";
    }

}
