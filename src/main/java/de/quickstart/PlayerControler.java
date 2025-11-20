package de.quickstart;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.quickstart.repos.PlayerRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PlayerControler {

    private final PlayerRepository playerRepo;
    private final TransfermarktImport importer;

    @GetMapping("/")
    public List<PlayerVO> getPlayers(){

        try{
            return playerRepo.findAll()
                    //TODO find only relevant players

                    .stream()
                    .map(player ->  {
                        long marketValue = importer.getPlayerMarketValue(player.getFullName());

                        return new PlayerVO(player.getFullName(), marketValue, 12 );
                    })
                    .toList();
        }catch(Exception e){
            System.err.println(e);
            return List.of();
        }
        

    }
    public record PlayerVO(String name, long marketvalue,  int age) {
       
    }

}
