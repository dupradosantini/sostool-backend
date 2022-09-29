package dupradosantini.sostoolbackend.services.interfaces;

import dupradosantini.sostoolbackend.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser findById(Integer userId);

    List<AppUser> findAllUsers();

    AppUser createUser(AppUser appUserObj);

}
