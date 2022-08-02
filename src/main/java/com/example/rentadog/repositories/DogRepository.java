/*package com.example.studentpractice.repositories;

import com.example.studentpractice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findStudentById(long kw);
}
*/

//Step 22
package com.example.rentadog.repositories;
import com.example.rentadog.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DogRepository extends JpaRepository<Dog,Long> {
    List<Dog> findDogByBreed(String key);
}