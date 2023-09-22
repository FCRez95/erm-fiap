package com.example.ermfiap.domain.Campaing.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ermfiap.domain.Campaing.entity.CampaingEntity;
import com.example.ermfiap.domain.Campaing.repository.CampaingSpringDataRepository;
import com.example.ermfiap.domain.Users.entity.Users;
import com.example.ermfiap.domain.Users.repository.UsersSprigDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CampaingController {
    private CampaingSpringDataRepository campaingRepo;
    private UsersSprigDataRepository userRepo;

    public CampaingController (CampaingSpringDataRepository campaingRepo, UsersSprigDataRepository userRepo) {
        this.campaingRepo = campaingRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/campaing/{idUser}")
    public ResponseEntity<List<CampaingEntity>> getUserCampaings(@PathVariable Long idUser, @RequestHeader(value="x-access-token") String token) {
        String email = JWT.decode(token).getIssuer();
        Optional<Users> user = userRepo.findByEmailEquals(email);
        if (user.isEmpty() || !idUser.equals(user.get().getId())) {
            return ResponseEntity.status(401).build();
        }

        Optional<List<CampaingEntity>> userCampaings = campaingRepo.findByIdUserEquals(idUser);

        if(userCampaings.isPresent()) {
            List<CampaingEntity> response = userCampaings.get();
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/campaing")
    public ResponseEntity<CampaingEntity> createCampaing(@RequestHeader(value="x-access-token") String token, @RequestBody CampaingEntity campaingData) {
        String email = JWT.decode(token).getIssuer();
        Optional<Users> user = userRepo.findByEmailEquals(email);

        if (user.isEmpty() || !user.get().getId().equals(campaingData.getIdUser())) {
            return ResponseEntity.status(401).build();
        }
        CampaingEntity createdCampaing = campaingRepo.save(campaingData);
        String jwtClickAuther = JWT.create().withIssuer(createdCampaing.getClickAuther() + "@" + createdCampaing.getId()).sign(Algorithm.HMAC256("MySecret"));
        campaingData.setClickAuther(jwtClickAuther);
        campaingRepo.save(createdCampaing);
        return ResponseEntity.ok(createdCampaing);
    }

    @DeleteMapping("/campaing/{idCampaing}")
    public ResponseEntity<String> deleteCampaing(@PathVariable Long idCampaing, @RequestHeader(value="x-access-token") String token) {
        String email = JWT.decode(token).getIssuer();
        Optional<Users> user = userRepo.findByEmailEquals(email);
        Optional<CampaingEntity> searchCampaing = campaingRepo.findById(idCampaing);

        if (user.isEmpty() || searchCampaing.isEmpty() || !user.get().getId().equals(searchCampaing.get().getIdUser())) {
            return ResponseEntity.status(401).build();
        }

        campaingRepo.deleteById(searchCampaing.get().getId());
        return ResponseEntity.ok("Campaing deleted successfuly");
    }
}
