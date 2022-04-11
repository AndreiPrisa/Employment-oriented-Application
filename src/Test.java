import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.json.*;

public class Test {
    public static void main(String[] args) throws IOException, InvalidDatesException, Consumer.ResumeIncompleteException {
        Application application = Application.getInstance();
        String path = "src//";

        String pathConsumers = (new File(path + args[0])).getAbsolutePath();
        String dataConsumers = new String(Files.readAllBytes(Paths.get(pathConsumers)));
        ArrayList<Consumer> consumerArrayList = new ArrayList<>();

        JSONObject jsonObjectConsumers = new JSONObject(dataConsumers);
        String[] consumerTypes = {"employees", "recruiters", "users", "managers"};
        for (String consumerType : consumerTypes) {
            JSONArray consumers = jsonObjectConsumers.getJSONArray(consumerType);
            for (int i = 0; i < consumers.length(); i++) {
                JSONObject consumer = consumers.getJSONObject(i);
                StringTokenizer stringTokenizer = new StringTokenizer(consumer.getString("name"));
                String firstName = stringTokenizer.nextToken();
                String lastName = stringTokenizer.nextToken();
                String email = consumer.getString("email");
                String phone = consumer.getString("phone");
                String date_of_birth = consumer.getString("date_of_birth");
                String genre = consumer.getString("genre");

                JSONArray jsonArrayLanguages = consumer.getJSONArray("languages");
                JSONArray jsonArrayLevel = consumer.getJSONArray("languages_level");

                ArrayList<Language> languages = new ArrayList<>();

                for (int j = 0; j < jsonArrayLanguages.length(); j++) {
                    languages.add(new Language(jsonArrayLanguages.getString(j), jsonArrayLevel.getString(j)));
                }

                // education reader
                JSONArray jsonArrayEducationCycles = consumer.getJSONArray("education");
                TreeSet<Education> educationCycles = new TreeSet<>();
                for (int j = 0; j < jsonArrayEducationCycles.length(); j++) {
                    JSONObject education = jsonArrayEducationCycles.getJSONObject(j);
                    String level = education.getString("level");
                    String institutionName = education.getString("name");
                    DateFormat startDate = new DateFormat(education.getString("start_date"));
                    DateFormat endDate = null;
                    if (!education.optString("end_date").isEmpty())
                        endDate = new DateFormat(education.getString("end_date"));

                    double grade = education.getDouble("grade");
                    educationCycles.add(new Education(startDate, endDate, institutionName, level, grade));
                }

                // experience reader
                JSONArray jsonArrayExperiences = consumer.getJSONArray("experience");
                TreeSet<Experience> experiences = new TreeSet<>();

                for (int j = 0; j < jsonArrayExperiences.length(); j++) {
                    JSONObject experience = jsonArrayExperiences.getJSONObject(j);

                    String companyName = experience.getString("company");
                    String position = experience.getString("position");
                    DateFormat startDate = new DateFormat(experience.getString("start_date"));
                    DateFormat endDate = null;
                    if (!experience.optString("end_date").isEmpty())
                        endDate = new DateFormat(experience.getString("end_date"));
                    experiences.add(new Experience(startDate, endDate, position, companyName));
                }
                Information information = new Information(firstName, lastName, email, phone, date_of_birth, genre, languages);

                Consumer.Resume resume = new Consumer.Resume.ResumeBuilder(information, educationCycles)
                                            .experienceSortedSet(experiences)
                                            .build();

                double salary;
                if (consumerType.compareTo("users") != 0) {
                    salary = consumer.getDouble("salary");
                    if (consumerType.compareTo("employees") == 0) {
                        Employee newEmployee = new Employee(null, salary);
                        newEmployee.setResume(resume);
                        consumerArrayList.add(newEmployee);
                    }
                    else
                        if (consumerType.compareTo("managers") == 0) {
                            Manager newManager = new Manager(null, salary);
                            newManager.setResume(resume);
                            consumerArrayList.add(newManager);
                        }
                        else
                        {
                            Recruiter newRecruiter = new Recruiter(null, salary);
                            newRecruiter.setResume(resume);
                            consumerArrayList.add(newRecruiter);
                        }
                }
                else
                {
                    User newUser = new User();
                    newUser.setResume(resume);
                    JSONArray jsonArrayInterestedCompanies = consumer.getJSONArray("interested_companies");
                    for (int j = 0; j < jsonArrayInterestedCompanies.length(); j++) {
                        newUser.addCompany(jsonArrayInterestedCompanies.getString(j));
                    }
                    application.add(newUser);
                    consumerArrayList.add(newUser);
                }
            }
        }
        String pathCompanies = (new File(path + args[1])).getAbsolutePath();
        String dataCompanies = new String(Files.readAllBytes(Paths.get(pathCompanies)));
        JSONObject jsonObjectCompanies = new JSONObject(dataCompanies);

        String pathFriends = (new File(path + args[2])).getAbsolutePath();
        String dataFriends = new String(Files.readAllBytes(Paths.get(pathFriends)));
        JSONObject jsonObjectFriends = new JSONObject(dataFriends.trim());
        for (Iterator<String> it = jsonObjectFriends.keys(); it.hasNext(); ) {
            String s = it.next();
            Consumer consumer;

            JSONArray jsonArrayFriends = jsonObjectFriends.getJSONArray(s);
            consumer = getConsumer(consumerArrayList, s);
            if (consumer == null) {
                System.out.println("nu exista consumerul");
                return;
            }
            for (int j = 0; j < jsonArrayFriends.length(); j++) {
                consumer.add(getConsumer(consumerArrayList, jsonArrayFriends.getString(j)));
            }
        }

        JSONArray jsonArrayCompanies = jsonObjectCompanies.getJSONArray("companies");
        for (int i = 0; i < jsonArrayCompanies.length(); i++) {
            JSONObject jsonObjectCompany = new JSONObject(jsonArrayCompanies.get(i).toString());
            Company newCompany = new Company();
            String companyName = jsonObjectCompany.getString("name");

            newCompany.setName(companyName);
            Manager manager = (Manager) getConsumer(consumerArrayList, jsonObjectCompany.getString("manager"));
            if (manager == null) {
                System.out.println("nu exista managerul");
                return;
            }
            newCompany.setManager(manager);
            manager.setCompanyName(companyName);
            DepartmentFactory departmentFactory = new DepartmentFactory();

            JSONArray jsonArrayDepartments = jsonObjectCompany.getJSONArray("departments");
            for (int j = 0; j < jsonArrayDepartments.length(); j++) {
                JSONObject jsonObjectDepartment = jsonArrayDepartments.getJSONObject(j);

                String departmentName = jsonObjectDepartment.getString("name");
                Department newDepartment = departmentFactory.getDepartment(departmentName);

                JSONArray jsonArrayEmployees = jsonObjectDepartment.getJSONArray("employees");
                for (int k = 0; k < jsonArrayEmployees.length(); k++) {
                    Employee employee = (Employee) getConsumer(consumerArrayList, jsonArrayEmployees.getString(k));
                    if (employee == null) {
                        System.out.println("nu exista employee-ul");
                        return;
                    }

                    newDepartment.add(employee);
                    employee.setCompanyName(companyName);
                }
                if (!jsonObjectDepartment.optString("jobs").isEmpty()) {
                    JSONArray jsonArrayJobs = jsonObjectDepartment.getJSONArray("jobs");
                    for (int k = 0; k < jsonArrayJobs.length(); k++) {
                        JSONObject jsonObjectJob = jsonArrayJobs.getJSONObject(k);
                        String jobName = jsonObjectJob.getString("name");
                        int nOOfNeededCandidates = jsonObjectJob.getInt("available_positions");
                        double salary = jsonObjectJob.getDouble("salary");

                        Job newJob = new Job(jobName,departmentName, companyName, nOOfNeededCandidates, salary);
                        if (nOOfNeededCandidates == 0)
                            newJob.setFlag(false);

                        JSONObject constraintObj = jsonObjectJob.getJSONObject("graduation_limit");
                        Double inferiorLimit, superiorLimit;

                        inferiorLimit = constraintObj.optDouble("inferior_limit", 0);
                        if (inferiorLimit == 0)
                            inferiorLimit = null;
                        superiorLimit = constraintObj.optDouble("superior_limit", 0);
                        if (superiorLimit == 0)
                           superiorLimit = null;
                        Constraint dateOfGraduationConstraint =
                                new Constraint(inferiorLimit, superiorLimit);
                        newJob.setDateOfGraduationConstraint(dateOfGraduationConstraint);
                        constraintObj = jsonObjectJob.getJSONObject("experience_limit");
                        inferiorLimit = constraintObj.optDouble("inferior_limit", 0);
                        if (inferiorLimit == 0)
                            inferiorLimit = null;
                        superiorLimit = constraintObj.optDouble("superior_limit", 0);
                        if (superiorLimit == 0)
                            superiorLimit = null;
                        Constraint YearsOfExperienceConstraint =
                                new Constraint(inferiorLimit, superiorLimit);
                        newJob.setYearsOfExperienceConstraint(YearsOfExperienceConstraint);

                        constraintObj = jsonObjectJob.getJSONObject("average_limit");
                        inferiorLimit = constraintObj.optDouble("inferior_limit", 0);
                        if (inferiorLimit == 0)
                            inferiorLimit = null;
                        superiorLimit = constraintObj.optDouble("superior_limit", 0);
                        if (superiorLimit == 0)
                            superiorLimit = null;
                        Constraint GPAConstraint =
                                new Constraint(inferiorLimit, superiorLimit);
                        newJob.setGPAConstraint(GPAConstraint);
                        newDepartment.add(newJob);
                    }
                }
                newCompany.add(newDepartment);
            }
            JSONArray jsonArrayRecruiters = jsonObjectCompany.getJSONArray("recruiters");
            for (int j = 0; j < jsonArrayRecruiters.length(); j++)
                newCompany.add((Recruiter) getConsumer(consumerArrayList, jsonArrayRecruiters.getString(j)));
            application.add(newCompany);
        }

        ArrayList<User> users = application.getUsers();

        for (User user : users) {
            ArrayList<Job> jobs = application.getJobs(user.getCompanies());
            for (Job job : jobs)
                job.apply(user);
        }

        ArrayList<String> companiesTest = new ArrayList<>();
        companiesTest.add("Google");
        ArrayList<Job> jobsList = application.getJobs(companiesTest);
        System.out.println(jobsList);
        new LoginPage();


       /*  System.out.println();
        for (Company company : application.getCompanies()) {
            Manager manager = company.getManager();
            ArrayList<String> companyString = new ArrayList<>();
            companyString.add(manager.getCompanyName());
            ArrayList<Job> jobs = application.getJobs(companyString);
            for (Job job : jobs) {
                manager.process(job);
            }
        }

        // inchide job-ului
        for (Company company : application.getCompanies()) {
           for (Department department : company.getDepartments())
               if (department.getJobs() != null)
                    for (Job job : department.getJobs()) {
                        job.setFlag(false);
                        company.notifyAllObservers(new JobClosed(job));
            }
        }
        System.out.println(application.getUsers());
        jobsList = application.getJobs(companiesTest);
        System.out.println(jobsList);
        
        */




    }

    public static Consumer getConsumer(ArrayList<Consumer> consumers, String fullName) {
        for (Consumer c : consumers) {
            if (c.getResume().getInformation().getFullName().compareTo(fullName) == 0)
                return c;
        }
        return null;
    }
}
