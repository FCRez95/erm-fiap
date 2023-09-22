package com.example.ermfiap.domain.Click.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ermfiap.domain.Click.entity.Click;
import com.example.ermfiap.domain.Click.repository.ClickRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class ClickController {
    private ClickRepository clickRepository;

    public ClickController (ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
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
}
