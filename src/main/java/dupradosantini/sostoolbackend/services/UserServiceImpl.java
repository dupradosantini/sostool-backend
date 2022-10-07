package dupradosantini.sostoolbackend.services;

import dupradosantini.sostoolbackend.domain.AppUser;
import dupradosantini.sostoolbackend.domain.BusinessRole;
import dupradosantini.sostoolbackend.domain.Workspace;
import dupradosantini.sostoolbackend.domain.WorkspaceMember;
import dupradosantini.sostoolbackend.repositories.UserRepository;
import dupradosantini.sostoolbackend.repositories.WorkspaceMemberRepository;
import dupradosantini.sostoolbackend.services.exceptions.ObjectNotFoundException;
import dupradosantini.sostoolbackend.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WorkspaceMemberRepository workspaceMemberRepository) {
        this.userRepository = userRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
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

    public Optional<WorkspaceMember> findUserMemberSet(AppUser user, BusinessRole businessRole){
       return workspaceMemberRepository.findWorkspaceMemberByAppUserAndBusinessRole(user, businessRole);
    }

    public WorkspaceMember createWorkspaceMember(WorkspaceMember workspaceMember){
        return this.workspaceMemberRepository.save(workspaceMember);
    }

    public Set<AppUser> findUsersWithRole(BusinessRole role){
        return this.workspaceMemberRepository.findUsersWithRole(role.getId());
    }

    public boolean UserHasRoleInWorkspace(AppUser user, Workspace workspace){

        var returnedObj = workspaceMemberRepository.findUserWithRoleInWorkspace(workspace.getId(), user.getId());
        for(var member : returnedObj){
            if(member.getEndDate()==null){
                System.out.println("User pertence a um cargo cuja data de termino ainda é null");
                return true;
            }
        }
        return false;
    }
}