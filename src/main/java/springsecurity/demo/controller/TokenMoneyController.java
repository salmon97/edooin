package springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.demo.entity.TokenMoney;
import springsecurity.demo.payload.ApiResponse;
import springsecurity.demo.payload.ReqEduCenter;
import springsecurity.demo.payload.ResTokenMoney;
import springsecurity.demo.repository.TokenMoneyRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/token")
public class TokenMoneyController {

    @Autowired
    TokenMoneyRepository tokenMoneyRepository;

    @GetMapping("/get")
    public HttpEntity<?> get() {
        List<TokenMoney> all = tokenMoneyRepository.findAll();
        List<ResTokenMoney> resTokenMonies = new ArrayList<>();
        all.forEach(tokenMoney -> resTokenMonies.add(new ResTokenMoney(
                tokenMoney.getId(),
                tokenMoney.getDate(),
                tokenMoney.getTokenMoney(),
                tokenMoney.getStJoinGroup().getStudent().getUser().getFirstName()
                )));
        return ResponseEntity.ok(resTokenMonies);
    }
}
