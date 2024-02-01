package com.wittypuppy.backend.admin.service;

import com.wittypuppy.backend.admin.dto.CareerDTO;
import com.wittypuppy.backend.admin.dto.EducationDTO;
import com.wittypuppy.backend.admin.dto.EmployeeDTO;
import com.wittypuppy.backend.admin.entity.Career;
import com.wittypuppy.backend.admin.entity.Education;
import com.wittypuppy.backend.admin.entity.Employee;
import com.wittypuppy.backend.admin.repository.AdminCareerRepository;
import com.wittypuppy.backend.admin.repository.AdminEducationRepository;
import com.wittypuppy.backend.admin.repository.AdminEmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminService {
    private final AdminEmployeeRepository employeeRepository;
    private final AdminCareerRepository careerRepository;
    private final AdminEducationRepository educationRepository;
    private final ModelMapper modelMapper;

    public AdminService(AdminEmployeeRepository repository, AdminCareerRepository careerRepository, AdminEducationRepository educationRepository, ModelMapper modelMapper) {
        this.employeeRepository = repository;
        this.careerRepository = careerRepository;
        this.educationRepository = educationRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getUserInfo(EmployeeDTO employeeDTO) {

        return modelMapper.map(employeeRepository.findById(employeeDTO.getEmployeeCode()),EmployeeDTO.class);
    }
    @Transactional
    public EmployeeDTO updateUserInfo(EmployeeDTO employeeDTO) {
        //엔티티로 변환 후 save
        Employee employee = employeeRepository.save(modelMapper.map(employeeDTO,Employee.class));
        //DTO로 변환 후 return
        return modelMapper.map(employeeRepository.save(employee),EmployeeDTO.class);
    }
    @Transactional
    public EmployeeDTO createUser(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(modelMapper.map(employeeDTO,Employee.class));
        return modelMapper.map(employee,EmployeeDTO.class);
    }
    @Transactional
    public EducationDTO createUserEducation(EducationDTO educationDTO) {
        Education education = educationRepository.save(modelMapper.map(educationDTO, Education.class));
        return modelMapper.map(education,EducationDTO.class);
    }

    @Transactional
    public CareerDTO createUserCareer(CareerDTO careerDTO) {
        Career career = careerRepository.save(modelMapper.map(careerDTO,Career.class));
        return modelMapper.map(career, CareerDTO.class);
    }
}
