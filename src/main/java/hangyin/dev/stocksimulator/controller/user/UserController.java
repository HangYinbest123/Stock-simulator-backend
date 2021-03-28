package hangyin.dev.stocksimulator.controller.user;

import hangyin.dev.stocksimulator.dto.EnrollUserThirdPartyRequest;
import hangyin.dev.stocksimulator.entity.User;
import hangyin.dev.stocksimulator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/enroll/thirdParty")
    public ResponseEntity<?> enrollUserThirdParty(@RequestBody EnrollUserThirdPartyRequest enrollUserThirdPartyRequest){
        String userId = userService.enrollUserThirdParty(enrollUserThirdPartyRequest);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping("thirdPartyId/{thirdPartyId}")
    public ResponseEntity<?> getUserByThirdPartyId(@PathVariable String thirdPartyId){
        User user = userService.findUserByThirdPartyId(thirdPartyId);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // when user login, pass google id
    // check userDataBase, if google id doesn't exist, call createUser
    // Implement this logic in frontend: if user not registered, call register user
}
