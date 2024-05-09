package com.AbdoHalim.LMS.Service.Implementation;

import com.AbdoHalim.LMS.Entity.*;
import com.AbdoHalim.LMS.Model.AdministratorModel;
import com.AbdoHalim.LMS.Model.AssignAcademicAdvisorModel;
import com.AbdoHalim.LMS.Model.CourseModel;
import com.AbdoHalim.LMS.Model.UserModel;
import com.AbdoHalim.LMS.Repository.*;
import com.AbdoHalim.LMS.Service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImp implements AdministratorService {
    private final DoctorRepo doctorRepo;
    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;
    private final AcademicAdvisorRepo academicAdvisorRepo;
    private final CourseRepo courseRepo;
    private final UserService userService;
    private final AdministratorRepo administratorRepo;
    @Override
    public ResponseEntity<String> addDoctor(UserModel userModel) {
        if (userModel.getDepartment().isEmpty()|| userModel.getFirstname().isEmpty()
                || userModel.getLastname().isEmpty()||userModel.getPhone().isEmpty()){
            return ResponseEntity.badRequest().body("invalid Data");
        }
        //make custom email for doctors
        String email= userModel.getFirstname()+userModel.getLastname()+"@spring.lms";
        if (doctorRepo.findByEmail(email) == null){
            return ResponseEntity.badRequest().body("this username is exist");
        }
        Doctor doctor=new Doctor();
        doctor.setFirstname(userModel.getFirstname());
        doctor.setLastname(userModel.getLastname());
        doctor.setPhone(userModel.getPhone());
        doctor.setEmail(email);
        doctor.setRole(Role.Doctor);
        doctor.setPassword(passwordEncoder.encode("Abcd_1234"));
        doctorRepo.save(doctor);
        return ResponseEntity.ok("Doctor added successfully with email : "+email);
    }

    @Override
    public ResponseEntity<String> addStudent(UserModel userModel) {
        if (userModel.getDepartment().isEmpty()|| userModel.getFirstname().isEmpty()
                || userModel.getLastname().isEmpty()||userModel.getPhone().isEmpty()){
            return ResponseEntity.badRequest().body("invalid Data");
        }
        //make custom email for doctors
        String email= generateStudentId()+ "@spring.lms";
        Student student=new Student();
        student.setFirstname(userModel.getFirstname());
        student.setLastname(userModel.getLastname());
        student.setPhone(userModel.getPhone());
        student.setEmail(email);
        student.setStudentId(generateStudentId());

        student.setDepartment(Department.valueOf(userModel.getDepartment()));
        student.setRole(Role.Student);
        student.setPassword(passwordEncoder.encode("Abcd_1234"));
        studentRepo.save(student);
        return ResponseEntity.ok("Student added successfully with id "+generateStudentId());
    }

    @Override
    public ResponseEntity<String> addAcademicAdvisor(UserModel userModel) {
        if (userModel.getDepartment().isEmpty()|| userModel.getFirstname().isEmpty()
                || userModel.getLastname().isEmpty()||userModel.getPhone().isEmpty()){
            return ResponseEntity.badRequest().body("invalid Data");
        }
        //make custom email for doctors
        String email= userModel.getFirstname()+userModel.getLastname()+"@spring.lms";
        if (academicAdvisorRepo.findByEmail(email)!=null){
            return ResponseEntity.badRequest().body("this username is exist");
        }
        AcademicAdvisor academicAdvisor=new AcademicAdvisor();
        academicAdvisor.setFirstname(userModel.getFirstname());
        academicAdvisor.setLastname(userModel.getLastname());
        academicAdvisor.setPhone(userModel.getPhone());
        academicAdvisor.setEmail(email);
        academicAdvisor.setRole(Role.AcademicAdvisor);
        academicAdvisor.setPassword(passwordEncoder.encode("Abcd_1234"));
        academicAdvisorRepo.save(academicAdvisor);
        return ResponseEntity.ok("academicAdvisor added successfully with email:"+email);
    }

    @Override
    public ResponseEntity<String> addCourse(CourseModel courseModel) {
        if (courseModel.getFinalexam()+courseModel.getQuiz()+courseModel.getMidterm()
                +courseModel.getAssigment()+ courseModel.getPractical()!=100)
            return ResponseEntity.badRequest().body("the final grads is not equal to 100");

        if (courseModel.getName().isEmpty())
            return ResponseEntity.badRequest().body("Enter the name of the course");
        Doctor doctor=doctorRepo.findByEmail(courseModel.getDoctoremail());
        if (doctor==null && !courseModel.getDoctoremail().isEmpty()){
            return ResponseEntity.badRequest().body("Doctor Not Exist");
        }
        Course c=courseRepo.findByName(courseModel.getName());
        if (c!=null){
            return ResponseEntity.badRequest().body("There course with this name and with id :"+c.getId());
        }
        Course course=new Course();
        course.setName(courseModel.getName());
        course.setLevel(courseModel.getLevel());
        course.setFinalexam(courseModel.getFinalexam());
        course.setPractical(courseModel.getPractical());
        course.setAssigment(courseModel.getAssigment());
        course.setQuiz(courseModel.getQuiz());
        course.setHours(courseModel.getHours());

        course.setDoctor(doctor);
        courseRepo.save(course);
        return ResponseEntity.ok("Course Added Successfully");
    }

    @Override
    public ResponseEntity<String> addDoctorToCourse(Long courseid, String doctoremail) {
        Optional<Course> course=courseRepo.findById(courseid);
        Doctor doctor=doctorRepo.findByEmail(doctoremail);
        if (course.isEmpty()){
            return  ResponseEntity.badRequest().body("Course Not exist");
        }
        if (doctor==null){
            return ResponseEntity.badRequest().body("Doctor not exist");
        }
        course.get().setDoctor(doctor);
        courseRepo.save(course.get());
        return ResponseEntity.ok("Doctor Added to Course");
    }

    @Override
    public ResponseEntity<List<Course>> retrieveDoctorCourses(Long id) {
        List<Course> courseList=courseRepo.findAllByDoctorId(id);
        return ResponseEntity.ok(courseList);
    }

    @Override
    public ResponseEntity<String> deleteAcademicAdvisor(Long id) {
        Optional<AcademicAdvisor> academicAdvisor=academicAdvisorRepo.findById(id);
        if (academicAdvisor.isEmpty())
            return ResponseEntity.badRequest().body(String
                    .format("AcademicAdvisor with is id %d not exist",id));
        academicAdvisorRepo.delete(academicAdvisor.get());
        return ResponseEntity.ok("AcademicAdvisor Delete Successfully");
    }
    @Override
    public ResponseEntity<String> deleteStudent(Long id) {
        Optional<Student> student=studentRepo.findById(id);
        if (student.isEmpty())
            return ResponseEntity.badRequest().body(String
                    .format("Student with is id %d not exist",id));
        studentRepo.delete(student.get());
        return ResponseEntity.ok("Student Delete Successfully");

    }
    @Override
    public ResponseEntity<String> deleteDoctor(Long id) {
        Optional<Doctor> doctor=doctorRepo.findById(id);
        if (doctor.isEmpty())
            return ResponseEntity.badRequest().body(String
                    .format("Doctor with is id %d not exist",id));
        doctorRepo.delete(doctor.get());
        return ResponseEntity.ok("Doctor Delete Successfully");

    }

    @Override
    public ResponseEntity<String> deleteCourse(Long id) {
        Optional<Course>  course=courseRepo.findById(id);
        if (course.isEmpty())
            return ResponseEntity.badRequest().body(String
                    .format("Course with is id %d not exist",id));
        courseRepo.delete(course.get());
        return ResponseEntity.ok("Course Delete Successfully");
    }

    @Override
    public ResponseEntity<String> assignAcademicAdvisor(AssignAcademicAdvisorModel assignAcademicAdvisorModel) {
        if (assignAcademicAdvisorModel.getEmail().isEmpty() ||
                assignAcademicAdvisorModel.getLevel() == null || assignAcademicAdvisorModel.getDepartment().isEmpty()){
            return ResponseEntity.badRequest().body("Enter Valid Data");}

        AcademicAdvisor academicAdvisor=academicAdvisorRepo.findByEmail(assignAcademicAdvisorModel.getEmail());

        if (academicAdvisor==null){
            return ResponseEntity.badRequest().body("AcademicAdvisor with this email dosen't exist");
        }
        if (assignAcademicAdvisorModel.getLevel()>4 ||assignAcademicAdvisorModel.getLevel()<1){
            return ResponseEntity.badRequest().body("Invalid Level");
        }

        if (!userService.isDepartmentExist(assignAcademicAdvisorModel.getDepartment())){
            return ResponseEntity.badRequest().body("invalid DepartmentName");
        }
        studentRepo.addAcademicAdvisor(academicAdvisor.getId(),assignAcademicAdvisorModel.getLevel(),
                assignAcademicAdvisorModel.getDepartment());
        return ResponseEntity.ok("AcademicAdvisor Assigned Successfully");
    }
    @Override
    public ResponseEntity<List<Doctor>> retrieveAllDoctors() {
        return ResponseEntity.ok(doctorRepo.findAll());
    }

    @Override
    public ResponseEntity<List<AcademicAdvisor>> retrieveAllAcademicAdvisor() {
        return ResponseEntity.ok(academicAdvisorRepo.findAll());
    }
    @Override
    public ResponseEntity<String> addStudentToDepartment(Long studentId, String depart) {
        if (!userService.isDepartmentExist(depart))
            return ResponseEntity.ok(String.format("Department %s is not exist",depart));
        Student student=studentRepo.findByStudentId(studentId);
        if (student==null)
            return ResponseEntity.badRequest().body(String.format("Student with id : %d is not exist",studentId));
        student.setDepartment(Department.valueOf(depart));
        studentRepo.save(student);
        return ResponseEntity.ok("Student added to department Successfully");
    }

    @Override
    public ResponseEntity<String> addAdministrator(AdministratorModel administratorModel) {
        if ( administratorModel.getOrganizationName().isEmpty() || administratorModel.getPassword().isEmpty()
                ||administratorModel.getPhoneNumber().isEmpty()){
            return ResponseEntity.badRequest().body("invalid Data");
        }
        String email = administratorModel.getOrganizationName()+"@spring.lms";
        Administrator administrator=new Administrator();
        administrator.setPhone(administratorModel.getPhoneNumber());
        administrator.setOrganizationName(administrator.getOrganizationName());
        administrator.setEmail(email);
        administrator.setPassword(passwordEncoder.encode(administratorModel.getPassword()));
        administrator.setRole(Role.Administrator);
        administratorRepo.save(administrator);

        return ResponseEntity.ok("Administrator Added with email:"+email);
    }
    public Long generateStudentId(){
        YearMonth currantYear=YearMonth.now();
        Long maxId=studentRepo.getMaxStudentId();
        if (currantYear.getYear()* 10000L > maxId) {
            return (long) currantYear.getYear()*10000;
        }
        return maxId+1;
    }
}
