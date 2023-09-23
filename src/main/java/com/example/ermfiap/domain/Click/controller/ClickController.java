package com.example.ermfiap.domain.Click.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ermfiap.domain.Campaing.entity.CampaingEntity;
import com.example.ermfiap.domain.Campaing.entity.CampaingSummary;
import com.example.ermfiap.domain.Campaing.repository.CampaingSpringDataRepository;
import com.example.ermfiap.domain.Click.entity.Click;
import com.example.ermfiap.domain.Click.repository.ClickRepository;
import com.example.ermfiap.domain.Users.entity.Users;
import com.example.ermfiap.domain.Users.repository.UsersSprigDataRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ClickController {
    private ClickRepository clickRepository;
    private CampaingSpringDataRepository campaingRepository;
    private UsersSprigDataRepository userRepository;

    public ClickController (ClickRepository clickRepository, CampaingSpringDataRepository campaingRepository, UsersSprigDataRepository userRepository) {
        this.clickRepository = clickRepository;
        this.campaingRepository = campaingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/click")
    public ResponseEntity<Click> createClick(@RequestHeader(value="x-access-token") String clickAuther, @RequestBody Click click) {
        String decoded = JWT.decode(clickAuther).getIssuer();
        String[] splitDecoded = decoded.split("@", 2);
        click.setIdCampaing(Long.valueOf(splitDecoded[1]));
        click.setClickToken(JWT.create().withIssuer(click.getSiteOrigin()).withClaim("timeStamp", new Date()).sign(Algorithm.HMAC256("MySecret")));

        Click createdClick = clickRepository.save(click);
        return ResponseEntity.ok(createdClick);
    }

    @PutMapping("/click")
    public ResponseEntity<Click> updateClick(@RequestHeader(value = "x-access-token") String clickToken, @RequestBody Click click) {
        Optional<Click> searchClick = clickRepository.findByClickToken(clickToken);

        if (searchClick.isPresent()) {
            Click response = searchClick.get();
            if(Objects.nonNull(click.getIncome())) response.setIncome(click.getIncome());
            if(Objects.nonNull(click.getPaidClick())) response.setPaidClick(click.getPaidClick());
            clickRepository.save(response);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/click/campaing-summary/{idCampaing}")
    public ResponseEntity<CampaingSummary> getClicksSummary(@PathVariable Long idCampaing , @RequestHeader(value = "x-access-token") String tokenAccess) {
        String email = JWT.decode(tokenAccess).getIssuer();
        Users user = userRepository.findByEmailEquals(email).get();
        Optional<CampaingEntity> campaing = campaingRepository.findById(idCampaing);
        if (campaing.isEmpty() || campaing.get().getIdUser() != user.getId()) {
            return ResponseEntity.status(401).build();
        }

        Optional<Integer> totalClicks = clickRepository.countByIdCampaing(idCampaing);
        Optional<Integer> paidClicks = clickRepository.countByIdCampaingAndPaidClick(idCampaing, true);
        Double totalSum = clickRepository.sumIncome(idCampaing);

        CampaingSummary summary = new CampaingSummary();
        summary.setTotalClicks(totalClicks.get());
        summary.setTotalPaidClicks(paidClicks.get());
        summary.setIncome(totalSum);
        System.out.println("AQUAAAA: " + summary.getTotalClicks() + " paidClicks: " + summary.getTotalPaidClicks() + " totalIncome: " + totalSum);
        return ResponseEntity.ok(summary);
    }
}
