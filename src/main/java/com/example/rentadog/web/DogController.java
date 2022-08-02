/*package com.example.studentpractice.web;

import com.example.studentpractice.entities.Student;
import com.example.studentpractice.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping(path = "/index")
    public String students(Model model) {

        List<Student> students = studentRepository.findAll();
        model.addAttribute("listStudents",students);

        return "students";
    }
}
*/


package com.example.rentadog.web;

import com.example.rentadog.entities.User;
import com.example.rentadog.entities.Dog;
import com.example.rentadog.entities.Rental;
import com.example.rentadog.entities.Review;
import com.example.rentadog.repositories.UserRepository;
import com.example.rentadog.repositories.DogRepository;
import com.example.rentadog.repositories.ReviewRepository;
import com.example.rentadog.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class DogController {
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogService dogService;
    @Autowired
    private UserRepository customerRepository;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private EmailSenderService service;

    private UserService userService;

    @GetMapping(path="/index")
    public String dogs(Model model, @RequestParam(name="keyword",defaultValue = "") String keyword){

        List<Dog> dogs;
        if (keyword.isEmpty()) {
            dogs = dogRepository.findAll();
        } else {
            String key = keyword;
            dogs = dogRepository.findDogByBreed(key);

        }
        model.addAttribute("listDogs", dogs);
        return "index";
    }

    @GetMapping("listDogs.html")
    public String showDogs(Model model){
        List<Dog> dogs = dogRepository.findAll();
        model.addAttribute("listDogs", dogs);
        return "/listDogs.html";

    }

    //dog owner registration
    @GetMapping("/formDogRegister")
    public String formDogs(Model model){
        model.addAttribute("dog", new Dog());
        return "registerDog";
    }

    @PostMapping("addDog")
    public String saveDog(@RequestParam("dog") String dogName,
                          @RequestParam("owner") String ownerName,
                          @RequestParam("email") String email,
                          @RequestParam("location") String location,
                          @RequestParam("breed") String breed,
                          @RequestParam("sex") char sex,
                          @RequestParam("image1") MultipartFile image1,
                          @RequestParam("image2") MultipartFile image2,
                          @RequestParam("image3") MultipartFile image3){
        try {
            dogService.saveDogToDB(dogName, ownerName, email, location, breed, sex, image1, image2, image3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/index";
    }

    //login
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    public String loginAccount(@RequestParam("email") String email, @RequestParam("password") String password){
        return "redirect:/index";
    }

    //customer registration
    @GetMapping("/formCustomerRegister")
    public String formCustomer(Model model){
        model.addAttribute("user", new User());
        return "registerCustomer";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(Model model,
                               @RequestParam("fName") String fName,
                               @RequestParam("username") String email,
                               @RequestParam("password") String password){

        User user = new User(fName,email,password);

        userService.saveUser(user);

        return "redirect:index";
    }

    //dog profile pages
    //@GetMapping("/profile/{id}")
    @RequestMapping(value="/profile/{id}")
    public String showProfile(@PathVariable("id") Long id, Model model){
        Dog dog = dogRepository.findById(id).orElse(null);
        if(dog == null){
            throw new RuntimeException("Dog does not exist.");
        }

        List<Review> reviews;
        reviews = reviewRepository.findReviewByOwnerId(id);

        model.addAttribute("reviews", reviews);
        model.addAttribute("listDogs", dog);
        return "profile";
    }

    @GetMapping("/profile/{id}/rentalForm")
    public String rentalForm(@PathVariable("id") Long id, Model model){
        Dog dog = dogRepository.findById(id).orElse(null);
        model.addAttribute("listDogs", dog);
        model.addAttribute("rental", new Rental());
        return "rentalForm";
    }

    @PostMapping("/index")
    public String saveRental(@RequestParam("fullName") String fullName, @RequestParam("email") String email,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startRental,   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endRental,
                             @RequestParam("message")String message, @RequestParam("ownerEmail") String ownerEmail){

        rentalService.saveRentalToDB(fullName,email,startRental,endRental,message, ownerEmail);

        service.sendSimpleEmail(ownerEmail,
                "You've received a rental request!" + "\n" +
                    "The rental details are as follows:"+ "\n" +
                        "Customer Name: " + fullName + "\n" +
                        "Email: " + email + "\n" +
                        "Rental Start Date" + startRental +
                        "\nRental End Date: " +endRental + "\n" +
                        "Message: " + message + "\n" +
                        "Please contact the customer through the email provided above to arrange for " +
                        "a pick-up or notify customer of the rental refusal.",
                "You've received a new rental request!");

        return "redirect:/index";
    }

    @GetMapping("/reviewForm/{id}")
    public String reviewForm(@PathVariable("id") Long id, Model model){
        Dog dog = dogRepository.findById(id).orElse(null);
        model.addAttribute("listDogs", dog);

        Review review = new Review();
        model.addAttribute("reviews", review);
        return "reviewForm";
    }

    @PostMapping("/saveReview")
    public String saveReview(@RequestParam("fullName") String fullName, @RequestParam("review") String review, @RequestParam("ownerId") Long ownerId){
        reviewService.saveReviewToDB(fullName, review, ownerId);
        return "redirect:/index";
    }

}

