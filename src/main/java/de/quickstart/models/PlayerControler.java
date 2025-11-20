package de.quickstart.models;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import de.quickstart.TransfermarktImport;
import de.quickstart.repos.PlayerRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PlayerControler {

    private final PlayerRepository playerRepo;
    private final TransfermarktImport importer;

    public List<PlayerVO> getPlayers(){

        try{
            return playerRepo.findAll().stream().map(player -> new PlayerVO(player.getFullName(), importer.getPlayerMarketValue(player.getFullName()))).toList();
        }catch(Exception e){
            System.err.println(e);
            return List.of();
        }
        

    }
    public record PlayerVO(String name, long markvalue) {
       
    }

}
