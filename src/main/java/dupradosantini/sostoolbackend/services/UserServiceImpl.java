package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.repositories.UserRepository;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import dupradosantini.sostoolbackend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser findById(Integer userId) {
        Optional<AppUser> obj = userRepository.findById(userId);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    @Override
    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser createUser(AppUser appUserObj) {
        appUserObj.setId(null);
        appUserObj.setWorkspaceMember(null);
        return userRepository.save(appUserObj);
    }
}