package com.example.ermfiap.domain.Users;

import com.example.ermfiap.domain.Users.entity.Users;
import com.example.ermfiap.domain.Users.repository.UsersSprigDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdLineRunner implements CommandLineRunner {
    //@Autowired
    //private UsersRepository repository;
    @Autowired
    private UsersSprigDataRepository repository;

    @Override
    public void run (String... args) throws Exception {
        repository.save(new Users(1l, "Fabio", "t@t.com", "123", "adm"));
        repository.save(new Users(2l, "Fabioo", "tt@tt.com", "1234", "cli"));
        repository.save(new Users(3l, "Fabiooo", "ttt@ttt.com", "12345", "cli"));

        repository.findById(3l);
        repository.deleteById(2l);
        repository.findByEmailEquals("t@t.com");
    }
}
