package hangyin.dev.stocksimulator.service;


import hangyin.dev.stocksimulator.dto.EnrollUserThirdPartyRequest;
import hangyin.dev.stocksimulator.entity.User;
import hangyin.dev.stocksimulator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    // you can't use autowired without using @Service for this class
    @Autowired
    private UserRepository userRepository;

    public String enrollUserThirdParty(EnrollUserThirdPartyRequest enrollUserThirdPartyRequest){
        String thirdPartyId = enrollUserThirdPartyRequest.getThirdPartyId();
        if(userRepository.findUserByThirdPartyId(thirdPartyId) == null){
            User user = transformEnrollUserThirdPartyRequestToUser(enrollUserThirdPartyRequest);
            userRepository.save(user);
            return user.getUserId();
        }
        // throw service exception
        return null;
    }

    public User findUserByThirdPartyId(String thirdPartyId){
        User user = userRepository.findUserByThirdPartyId(thirdPartyId);
        return user;
    }

    public User findUserByUserId(String userId){
        User user = userRepository.findUserByUserId(userId);
        return user;
    }

    public User transformEnrollUserThirdPartyRequestToUser(EnrollUserThirdPartyRequest enrollUserThirdPartyRequest){
        User user = new User();
        user.setThirdPartyId(enrollUserThirdPartyRequest.getThirdPartyId());
        user.setFirstName(enrollUserThirdPartyRequest.getFirstName());
        user.setLastName(enrollUserThirdPartyRequest.getLastName());
        user.setUserId(UUID.randomUUID().toString());
        return user;
    }
}
