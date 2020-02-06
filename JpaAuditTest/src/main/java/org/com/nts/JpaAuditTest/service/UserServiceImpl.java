/**
 * 
 */
package org.com.nts.JpaAuditTest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.com.nts.JpaAuditTest.entity.EmployeeAuditEntity;
import org.com.nts.JpaAuditTest.entity.EmployeeAuditJsonEntity;
import org.com.nts.JpaAuditTest.entity.EmployeeEntity;
import org.com.nts.JpaAuditTest.entity.UserAudit;
import org.com.nts.JpaAuditTest.entity.UserEntity;
import org.com.nts.JpaAuditTest.entity1.Patient;
import org.com.nts.JpaAuditTest.model.EmployeeModel;
import org.com.nts.JpaAuditTest.repository.EmployeeAuditJsonRepository;
import org.com.nts.JpaAuditTest.repository.EmployeeAuditRepository;
import org.com.nts.JpaAuditTest.repository.EmployeeRepository;
import org.com.nts.JpaAuditTest.repository.UserAuditRepository;
import org.com.nts.JpaAuditTest.repository.UserRepository;
import org.com.nts.JpaAuditTest.repository1.PatientRepository;
import org.com.nts.JpaAuditTest.util.EmpConstant;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

/**
 * @author chetan
 *
 */
@Service
@Scope("prototype")
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuditRepository auditRepository;

	@Autowired

	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeAuditRepository employeeAuditRepository;

	@Autowired
	private EmployeeAuditJsonRepository empJsonAuditRepository;

	@Autowired
	private PatientRepository patientRepository;

	/**
	 * 
	 * @param users
	 * @return
	 */

	public ResponseEntity<?> offerUser(UserEntity users) {
		try {
			userRepository.save(users);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	public ResponseEntity<?> updateUser(UserEntity user) {

		try {

			Optional<UserEntity> userEntity = userRepository.findById(user.getId());

			Javers javers = JaversBuilder.javers().withNewObjectsSnapshot(false).build();

			Diff diff = javers.compare(userEntity.get(), user);

			Pattern pattern = Pattern.compile("'(.*?)'");
			List<UserAudit> userAudits = new ArrayList<UserAudit>();

			diff.getChanges().stream().forEach(x -> {
//				x.getAffectedGlobalId().value();

				UserAudit userAudit = new UserAudit();
				int count = 0;
				userAudit.setEntity(user.getClass().getSimpleName());
				Matcher matcher = pattern.matcher(x.toString());

				Map<String, String> propertiesMap = new LinkedHashMap<String, String>();

				while (matcher.find()) {
					if (count == 0) {
						userAudit.setColumnName(matcher.group());
					} else if (count == 1) {
						userAudit.setOldValue(matcher.group());
					} else if (count == 2) {
						userAudit.setNewValue(matcher.group());
					}

					count++;
				}

				userAudit.setUser(userEntity.get());
				userAudits.add(userAudit);
				System.out.println("propertiesMap::" + propertiesMap);

			});
			userAudits.removeIf(x -> x.getColumnName() == null);

			if (userEntity.isPresent()) {

				userEntity.get().setCity(user.getCity());
				userEntity.get().setEmail(user.getEmail());
				userEntity.get().setName(user.getName());

				userRepository.save(userEntity.get());
			}

			if (!userAudits.isEmpty()) {
				auditRepository.saveAll(userAudits);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public ResponseEntity<?> offerEmployee(EmployeeEntity entity) {
		System.out.println("offerEmployee() invoked. ");
		try {
			employeeRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.CREATED);
	}

	/**
	 * <p>
	 * method to update {@link EmployeeEntity} and maintain audit logs as newJson
	 * and oldJson into {@link EmployeeAuditJsonEntity}
	 * 
	 * @param employee
	 * @return
	 */
	public ResponseEntity<?> updateEmployee(EmployeeEntity employee) {
		System.out.println("updateEmployee ");
		ObjectMapper mapper = new ObjectMapper();
		EmployeeAuditJsonEntity auditJsonEntity = new EmployeeAuditJsonEntity();
		try {
			Optional<EmployeeEntity> empEntity = employeeRepository.findById(employee.getId());
			auditJsonEntity.setEmployeeEntity(empEntity.get());

//			SimpleBeanPropertyFilter beanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("auditEntities",
//					"auditJsonEntities", "createdDate", "lastUpdateDate");

			// simple filter provider to skipped particular property
			SimpleFilterProvider filterProvider = new SimpleFilterProvider();

			filterProvider.addFilter("employeeFilter", SimpleBeanPropertyFilter.serializeAllExcept("auditEntities",
					"auditJsonEntities", "createdDate", "lastUpdateDate"));

//			mapper.addMixIn(new ArrayList<EmployeeAuditEntity>().getClass(), EmployeeMixInIgnoreType.class);
//			mapper.addMixIn(new ArrayList<EmployeeAuditJsonEntity>().getClass(), EmployeeMixInIgnoreType.class);

			mapper.setFilterProvider(filterProvider);
			String oldJson = mapper.writeValueAsString(empEntity.get());
			String newJson = mapper.writeValueAsString(employee);

			System.out.println("old Json format" + oldJson);

			/*
			 * obtaining full generics type information by sub-classing
			 */
			TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {
			};

//			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);

			Map<String, Object> leftMap = mapper.readValue(oldJson, type);
			Map<String, Object> rightMap = mapper.readValue(newJson, type);

			// return difference between two map
			MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);

			// creating new map to hold new json as key value
			Map<String, Object> newJsonMap = new LinkedHashMap<String, Object>();

			difference.entriesDiffering().entrySet().forEach(x -> {

				if (x.getValue().rightValue() != null)
					newJsonMap.put(x.getKey(), x.getValue().rightValue());
				System.out.println(x.getKey() + "--" + x.getValue().rightValue());
			});
			// convert map into json string
			String newJsonString = mapper.writeValueAsString(newJsonMap);

			System.out.println("new Json format" + newJsonString);
			if (empEntity.isPresent()) {

				empEntity.get().setCity(employee.getCity());
				empEntity.get().setEmail(employee.getEmail());
				empEntity.get().setName(employee.getName());
			} else {
				return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.NOT_FOUND);
			}

			auditJsonEntity.setNewJson(newJsonString);
			auditJsonEntity.setOldJson(oldJson);
			auditJsonEntity.setOperation(EmpConstant.UPDATE_OP);
			employeeRepository.save(empEntity.get());
			empJsonAuditRepository.save(auditJsonEntity);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

	}

	/**
	 * <p>
	 * method to update {@link EmployeeEntity} store each column old and new value
	 * into {@link EmployeeAuditEntity}
	 * 
	 * @param employee
	 * @return
	 */
	public ResponseEntity<?> reviseEmployee(EmployeeEntity employee) {
		System.out.println("reviseEmployee() invoked.  ");
		ObjectMapper mapper = new ObjectMapper();
		List<EmployeeAuditEntity> employeeAuditEnity = new ArrayList<EmployeeAuditEntity>();
		try {
			Optional<EmployeeEntity> empEntity = employeeRepository.findById(employee.getId());
			// employeeAuditEnity.setEmployee(empEntity.get());

			SimpleFilterProvider filterProvider = new SimpleFilterProvider();

			filterProvider.addFilter("employeeFilter", SimpleBeanPropertyFilter.serializeAllExcept("auditEntities",
					"auditJsonEntities", "createdDate", "lastUpdateDate"));

			mapper.setFilterProvider(filterProvider);
			String oldJson = mapper.writeValueAsString(empEntity.get());
			String newJson = mapper.writeValueAsString(employee);

			// for obtaining full generics type information
			TypeReference<HashMap<String, Object>> type = new TypeReference<HashMap<String, Object>>() {
			};

//			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);

			Map<String, Object> leftMap = mapper.readValue(oldJson, type);
			Map<String, Object> rightMap = mapper.readValue(newJson, type);

			// return difference between two map
			MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);

			Map<String, Object> oldJsonMap = new LinkedHashMap<String, Object>();
			Map<String, Object> newJsonMap = new LinkedHashMap<String, Object>();

			difference.entriesDiffering().entrySet().forEach(x -> {

				oldJsonMap.put(x.getKey(), x.getValue().leftValue());
				newJsonMap.put(x.getKey(), x.getValue().rightValue());
			});

			oldJsonMap.entrySet().stream().forEach(
					x -> newJsonMap.entrySet().stream().filter(y -> x.getKey().equals(y.getKey())).forEach(z -> {

						EmployeeAuditEntity empAudit = new EmployeeAuditEntity();

						empAudit.setEmployee(empEntity.get());
						System.out.println("column:" + x.getKey() + " old value " + x.getValue());
						System.out.println("column:" + z.getKey() + " new value " + z.getValue());

						empAudit.setColumnName(x.getKey());
						empAudit.setOldValue((String) x.getValue());
						empAudit.setNewValue((String) z.getValue());
						empAudit.setEntity(employee.getClass().getSimpleName());
						employeeAuditEnity.add(empAudit);
					}));

			System.out.println("audit entity list" + employeeAuditEnity.toString());

			employeeAuditRepository.saveAll(employeeAuditEnity);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

	/**
	 * <p>
	 * method to return employee
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<EmployeeModel> findEmployee(Long id) {
		ModelMapper mapper = new ModelMapper();
		EmployeeModel employeeModel = new EmployeeModel();
		try {

			Optional<EmployeeEntity> optionalEmpEntity = employeeRepository.findById(id);

			if (!optionalEmpEntity.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			// entity = optionalEmpEntity.get();
			employeeModel = mapper.map(optionalEmpEntity.get(), EmployeeModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(employeeModel, HttpStatus.OK);

	}

	/**
	 * <p>
	 * method to poll the existing {@link EmployeeEntity}
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> deleteEmployee(Long id) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			EmployeeAuditJsonEntity auditJsonEntity = new EmployeeAuditJsonEntity();
			Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);
			if (optionalEmployee.isPresent()) {
				SimpleFilterProvider filterProvider = new SimpleFilterProvider();

				filterProvider.addFilter("employeeFilter",
						SimpleBeanPropertyFilter.serializeAllExcept("auditEntities", "auditJsonEntities"));

				mapper.setFilterProvider(filterProvider);

				String actulJsonBeforeDelete = mapper.writeValueAsString(optionalEmployee.get());

				auditJsonEntity.setJsonBeforePoll(actulJsonBeforeDelete);

				employeeRepository.deleteById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);

	}

/////////////////////////////Push patient into diffrent databse/////////////////
	/**
	 * <p>
	 * method to insert patient information into {@link Patient} belongs to dummyDb
	 * database
	 * 
	 * @param patient
	 * @return
	 */
	public ResponseEntity<?> pushPatient(Patient patient) {

		try {

			Patient serilizablePatientEntity = patientRepository.save(patient);
			System.out.println("serilizablePatientEntity::-" + serilizablePatientEntity.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param id
	 * @return {@link Patient}
	 */
	public ResponseEntity<Patient> getPatient(Long id) {
//		Patient patient = null;
		try {
			System.out.println("getPatient");
			Optional<Patient> patientOptionalEntity = patientRepository.findById(id);
			if (patientOptionalEntity.isPresent()) {

				return new ResponseEntity<Patient>(patientOptionalEntity.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
