package bpdtsdwp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class LocateUsersController {

    @GetMapping("/test")
    public List<UserDetails> test() {
        return Collections.singletonList(new UserDetails("hello", "world"));
    }
}
