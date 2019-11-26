package com.codeup.blog.blog.controllers;
import com.codeup.blog.blog.models.*;
import com.codeup.blog.blog.repositories.*;
import com.codeup.blog.blog.models.Relationship;
import com.codeup.blog.blog.models.User;
import com.codeup.blog.blog.repositories.LocationRepository;
import com.codeup.blog.blog.repositories.ProfileRepository;
import com.codeup.blog.blog.repositories.RelationshipRepository;
import com.codeup.blog.blog.repositories.UserRepository;
import com.codeup.blog.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
public class ProfileController {

    private ProfileRepository profileDao;
    private UserRepository userDao;
    private RelationshipRepository relationshipDao;
    private HobbyRepository hobbyDao;
    private TraitRepository traitDao;
    private LocationRepository locationDao;
    private BranchRepository branchDao;
    private RankRepository rankDao;
    private ChildRepository childDao;




    @Autowired
    private UserService usersService;
    @Autowired
    UserService usersSvc;


    public ProfileController(ProfileRepository profileDao, UserRepository userDao, RelationshipRepository relationshipDao, HobbyRepository hobbyDao, TraitRepository traitDao, LocationRepository locationDao, BranchRepository branchDao, RankRepository rankDao, ChildRepository childDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.relationshipDao = relationshipDao;
        this.hobbyDao = hobbyDao;
        this.traitDao = traitDao;
        this.locationDao = locationDao;
        this.branchDao = branchDao;
        this.rankDao = rankDao;
        this.childDao = childDao;
    }

    @GetMapping("/users/profile/{id}")
    public String showUserProfile(Model vModel, @PathVariable long id){
        User loggedInUser = usersService.loggedInUser();
        Profile loggedInProfile = loggedInUser.getProfile();
        if (loggedInProfile == null ||loggedInProfile.getFirstName() == null || loggedInProfile.getLastName() == null){
            return "redirect:/users/userdetails";
        }

        vModel.addAttribute("user", userDao.getOne(id));

//        FRIEND REQUESTS
        List<User> allUsers = userDao.findAll();
        List<User> pendingFriends = new ArrayList<>();
        for (User user: allUsers) {

            for (Relationship relationship : user.getRelationships()) {

                if(relationship.getFriend().getId() == id){
                    pendingFriends.add(user);
                }
            }
        }

        vModel.addAttribute("pendingFriends", pendingFriends);


        return "users/profile";
    }




    @GetMapping("/users/userdetails")
    public String showUserDetails(Model model){
        model.addAttribute("profile", new Profile());
        model.addAttribute("hobbies", hobbyDao.findAll());
        model.addAttribute("traits", traitDao.findAll());
        model.addAttribute("branches", branchDao.findAll());
        model.addAttribute("ranks", rankDao.findAll());
        model.addAttribute("locations", locationDao.findAll());

        return "users/userdetails";
    }

    @PostMapping("/users/userdetails")
    public String submitUserDetails(
             @ModelAttribute Profile profile,
             @RequestParam(name="traits", required = false)ArrayList<Long> traitIds,
             @RequestParam(name="hobbies", required = false)ArrayList<Long> hobbyIds,
             @RequestParam(name="branch", required = false)Long branchId,
             @RequestParam(name="rank", required = false) Long rankId

    ){
        User user = usersService.loggedInUser();
       profile.setUser(user);
        profile.setBranch(branchDao.getOne(branchId));
        profile.setRank(rankDao.getOne(rankId));




        // FINDS THE Hobbies THAT WERE SELECTED BY USER
        List<Hobby> hobbiesToAdd = new ArrayList<>();
        for (long hobbyId : hobbyIds) {
            for (Hobby all: hobbyDao.findAll()){
                if(hobbyId == all.getId()){
                    hobbiesToAdd.add(hobbyDao.getOne(hobbyId));
                }
            }
        }

        // ADDS EACH INDIVIDUAL Hobby TO PROFILE
        if (hobbiesToAdd != null){
            profile.setHobbies(new ArrayList<>());
            for (Hobby hobby: hobbiesToAdd){
                profile.getHobbies().add(hobby);
            }
        }




        // FINDS THE TRAITS THAT WERE SELECTED BY USER
        List<Trait> traitsToAdd = new ArrayList<>();
        for (long traitId : traitIds) {
            for (Trait all: traitDao.findAll()){
                if(traitId == all.getId()){
                   traitsToAdd.add(traitDao.getOne(traitId));
                }
            }
        }

        // ADDS EACH INDIVIDUAL TRAIT TO PROFILE
        if (traitsToAdd != null){
            profile.setTraits(new ArrayList<>());
            for (Trait trait: traitsToAdd){
                profile.getTraits().add(trait);
            }
        }
//        hard coded children for now
        List<Child> children = new ArrayList<>();
        children.add(new Child("female", 6));
        profile.setChildren(children);


        profileDao.save(profile);
        user.setProfile(profile);
        userDao.save(user);


        return "redirect:/users/profile/" + user.getId();
    }



    //    user details edit GET
    @GetMapping("/users/user-details-edit")
    public String showUserDetailsEdit(Model m){
        User user = usersService.loggedInUser();
         Profile profile = user.getProfile();
         m.addAttribute("profile", profile);
        m.addAttribute("hobbies", hobbyDao.findAll());
        m.addAttribute("traits", traitDao.findAll());
        m.addAttribute("branches", branchDao.findAll());
        m.addAttribute("ranks", rankDao.findAll());
        m.addAttribute("locations", locationDao.findAll());
        return "users/user-details-edit";
    }
//
//// user detail edit POST

    @PostMapping("/users/user-details-edit")
    public String submitUserDetailsEdit(
            @RequestParam(name="traits", required = false)ArrayList<Long> traitIds,
            @RequestParam(name="hobbies", required = false)ArrayList<Long> hobbyIds,
            @RequestParam(name="branch", required = false)Long branchId,
            @RequestParam(name="rank", required = false) Long rankId,
            @RequestParam(name="firstName", required = false) String firstName,
            @RequestParam(name="lastName", required = false) String lastName,
            @RequestParam(name="bio", required = false) String bio,
            @RequestParam(name="age", required = false) int age,
            @RequestParam(name="milSpouse", required = false) boolean millSpouse,
            @RequestParam(name="married", required = false) boolean married


    ){

        User user = usersService.loggedInUser();
        Profile profile = user.getProfile();

        profile.setBranch(branchDao.getOne(branchId));
        profile.setRank(rankDao.getOne(rankId));

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setBio(bio);
        profile.setage(age);
        profile.setMilSpouse(millSpouse);
        profile.setMilSpouse(married);







        // FINDS THE Hobbies THAT WERE SELECTED BY USER
        List<Hobby> hobbiesToAdd = new ArrayList<>();
        for (long hobbyId : hobbyIds) {
            for (Hobby all: hobbyDao.findAll()){
                if(hobbyId == all.getId()){
                    hobbiesToAdd.add(hobbyDao.getOne(hobbyId));
                }
            }
        }

        // ADDS EACH INDIVIDUAL Hobby TO PROFILE
        if (hobbiesToAdd != null){
            profile.setHobbies(new ArrayList<>());
            for (Hobby hobby: hobbiesToAdd){
                profile.getHobbies().add(hobby);
            }
        }




        // FINDS THE TRAITS THAT WERE SELECTED BY USER
        List<Trait> traitsToAdd = new ArrayList<>();
        for (long traitId : traitIds) {
            for (Trait all: traitDao.findAll()){
                if(traitId == all.getId()){
                    traitsToAdd.add(traitDao.getOne(traitId));
                }
            }
        }

        // ADDS EACH INDIVIDUAL TRAIT TO PROFILE
        if (traitsToAdd != null){
            profile.setTraits(new ArrayList<>());
            for (Trait trait: traitsToAdd){
                profile.getTraits().add(trait);
            }
        }
//        hard coded children for now
        List<Child> children = new ArrayList<>();
        children.add(new Child("female", 6));
        profile.setChildren(children);


        profileDao.save(profile);

        return "redirect:/users/profile/" + user.getId();
    }










    @GetMapping("/users/{id}/friend-request")
    public String sendFriendRequest(@PathVariable long id, Model vModel) {
        User userOne = usersService.loggedInUser();
        User requestedFriend = userDao.getOne(id);
        FriendStatus status = FriendStatus.PENDING;
        Relationship relationship = new Relationship(userOne, requestedFriend, status);
        relationshipDao.save(relationship);


        return "redirect:/users/profile/"+id;
    }

    @GetMapping("/users/{id}/accept-request")
    public String acceptFriendRequest(@PathVariable long id ) {
        User userOne = usersService.loggedInUser();
        Relationship acceptFriend = relationshipDao.getOne(id);
        FriendStatus status = FriendStatus.ACCEPTED;
        User requestedFriend = acceptFriend.getUser();
        acceptFriend.setStatus(status);


        Relationship relationshipOne = new Relationship(userOne, requestedFriend, status);
        relationshipDao.save(relationshipOne);
        relationshipDao.save(acceptFriend);



        return "redirect:/users/profile/"+ userOne.getId();
    }



    @GetMapping("/users/location")

    public String showCreateForm(Model model) {
        model.addAttribute("location", new Location());

        return "users/location";
    }

    @PostMapping("/users/location")
    public String create(@ModelAttribute ("form2") Location locationToBeCreated){
//        locationToBeCreated.setProfiles(profileDao.getOne(1));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!This!!!!!!!!!!!!!!!!!!!!!!" + locationToBeCreated.getLocation());
        locationDao.save(locationToBeCreated);
        return "redirect:/users/location";
    }

    @GetMapping("/users/profile-design")

    public String showProfile(Model model) {


        return "users/profile-design";
    }


}
