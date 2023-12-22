package com.example.demo.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepositoty authorRepositoty;

    @Autowired
    public AuthorService(AuthorRepositoty authorRepositoty) {
        this.authorRepositoty = authorRepositoty;
    }
}
