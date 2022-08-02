package com.example.rentadog;

import com.example.rentadog.entities.Dog;
import com.example.rentadog.entities.Rental;
import com.example.rentadog.entities.Review;
import com.example.rentadog.entities.User;
import com.example.rentadog.repositories.DogRepository;
import com.example.rentadog.repositories.RentalRepository;
import com.example.rentadog.repositories.ReviewRepository;
import com.example.rentadog.repositories.UserRepository;
import com.example.rentadog.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
class RentADogApplicationTests {
    @Mock
    DogRepository dogRepository=mock(DogRepository.class);
    DogService dogService=new DogService(dogRepository);

    @Mock
    ReviewRepository reviewRepository=mock(ReviewRepository.class);
    ReviewService reviewService=new ReviewService(reviewRepository);

    @Mock
    RentalRepository rentalRepository=mock(RentalRepository.class);
    RentalService rentalService=new RentalService(rentalRepository);

    @Mock
    JavaMailSender mailSender=mock(JavaMailSender.class);
    EmailSenderService emailSenderService=new EmailSenderService(mailSender);

    @Mock
    UserRepository userRepository=mock(UserRepository.class);
    UserService userService=new UserService(userRepository);

    @Test
    void saveDogToDBtest() throws IOException {
        String dogname="Dog A";
        String owner="Tom";
        String email="tom@douglas.com";
        String location="Vancouver";
        String breed="Labrador";
        char sex='M';
        String f1="src/main/resources/static/dog1.jfif";
        String f2="src/main/resources/static/dog2.jfif";
        String f3="src/main/resources/static/dog3.jfif";

        MultipartFile fileResource1 = new MockMultipartFile("src/main/resources/static/dog1.jfif",new FileInputStream(new File(f1)));
        MultipartFile fileResource2 = new MockMultipartFile("src/main/resources/static/dog2.jfif",new FileInputStream(new File(f2)));
        MultipartFile fileResource3 = new MockMultipartFile("src/main/resources/static/dog3.jfif",new FileInputStream(new File(f3)));
        dogService.saveDogToDB(dogname,owner,email,location,breed,sex,fileResource1,fileResource2,fileResource3);

        ArgumentCaptor<Dog> varArgs = ArgumentCaptor.forClass(Dog.class);
        verify(dogRepository).save(varArgs.capture());
        assertEquals(dogname,varArgs.getValue().getDogName());
        assertEquals(owner,varArgs.getValue().getOwnerName());
        assertEquals(email,varArgs.getValue().getEmail());
        assertEquals(location,varArgs.getValue().getLocation());
        assertEquals(breed,varArgs.getValue().getBreed());
        assertEquals(sex,varArgs.getValue().getSex());
    }

    @Test
    void saveReviewToDBtest() {
        String fullname="Peter";
        String review="This dog is obeying";
        Long id=1L;

        reviewService.saveReviewToDB(fullname,review,id);
        ArgumentCaptor<Review> varArgs = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository).save(varArgs.capture());
        assertEquals(fullname,varArgs.getValue().getFullName());
        assertEquals(review,varArgs.getValue().getReview());
    }

    @Test
    void saveRentalToDBtest() {
        String fullname="Peter";
        String email="peter@douglas.com";
        LocalDateTime startrentaldate= LocalDateTime.of(2022, 07, 01, 00, 00, 00);
        LocalDateTime endrentaldate=LocalDateTime.of(2022, 07, 10, 00, 00, 00);
        String message="Hi I want to rent your a dog for a short period";
        String ownemail="tom@dougals.com";

        rentalService.saveRentalToDB(fullname,email,startrentaldate,endrentaldate,message,ownemail);
        ArgumentCaptor<Rental> varArgs = ArgumentCaptor.forClass(Rental.class);
        verify(rentalRepository).save(varArgs.capture());
        assertEquals(fullname,varArgs.getValue().getFullName());
        assertEquals(email,varArgs.getValue().getEmail());
        assertEquals(startrentaldate,varArgs.getValue().getStartRental());
        assertEquals(endrentaldate,varArgs.getValue().getEndRental());
        assertEquals(message,varArgs.getValue().getMessage());
        assertEquals(ownemail,varArgs.getValue().getOwnerEmail());
    }

    @Test
    void sendSimpleEmailtest(){
        String toemail="tom@dougals.com";
        String body="You've received a rental request!...";
        String subject="You've received a new rental request!";

        emailSenderService.sendSimpleEmail(toemail,body,subject);
        ArgumentCaptor<SimpleMailMessage> varArgs = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(varArgs.capture());
        assertEquals(toemail,varArgs.getValue().getTo()[0]);
        assertEquals(body,varArgs.getValue().getText());
        assertEquals(subject,varArgs.getValue().getSubject());

    }

    @Test
    void saveUsertest(){
        String fname="Peter";
        String email="tom@dougals.com";
        String pw="123456";
        User user=new User(fname,email,pw);

        userService.saveUser(user);
        ArgumentCaptor<User> varArgs = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(varArgs.capture());
        assertEquals(fname,varArgs.getValue().getFName());
        assertEquals(email,varArgs.getValue().getUsername());
    }

    @Test
    void showreviewtest(){
        Review r = new Review();
        List<Review> reviewlist = new ArrayList<>();
        reviewlist.add(r);

        when(reviewRepository.findAll()).thenReturn(reviewlist);
        List<Review>test=reviewRepository.findAll();
        assertEquals(1,test.size() );
    }
}



