import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class Manager extends Employee {
    private ArrayList<Request <Job, Consumer>> hiringRequests;

    public ArrayList<Request<Job, Consumer>> getHiringRequests() {
        updateHiringRequests();
        return hiringRequests;
    }

    public void updateHiringRequests() {
        Iterator<Request<Job, Consumer>> it = hiringRequests.iterator();
        Application application = Application.getInstance();

        while(it.hasNext()) {
            Request<Job, Consumer> request = it.next();
            if (!application.getUsers().contains((User) request.getValue1())) {
                System.out.println((User) request.getValue1());
                request.getKey().getCandidates().remove((User) request.getValue1());
                it.remove();
            }

            if(!request.getKey().isFlag())
                it.remove();
        }
    }

    public void setHiringRequests(ArrayList<Request<Job, Consumer>> hiringRequests) {
        this.hiringRequests = hiringRequests;
    }

    public Manager(String companyName, double salary) {
        super(companyName, salary);
        hiringRequests = new ArrayList<>();
    }

    public void addHiringRequest(Request<Job, Consumer> request) {
        hiringRequests.add(request);
    }

    public void hire(User candidate, Job job) {
        Application application = Application.getInstance();
        application.removeObservers(candidate);
        Employee newEmployee = candidate.convert();
        application.remove(candidate);

        Company company = application.getCompany(job.getCompanyName());
        Department department = company.getDepartment(job.getDepartmentName());

        newEmployee.setCompanyName(getCompanyName());
        newEmployee.setSalary(job.getSalary());
        department.add(newEmployee);
        job.getCandidates().remove(candidate);
        job.setNoOfNeededCandidates(job.getNoOfNeededCandidates() - 1);

        for (String observerCompanyString : candidate.getCompanies()) {
            Company observerCompany =  application.getCompany(observerCompanyString);
            observerCompany.removeObserver(candidate);
        }
    }

    // accepta un request individual
    public void accept(Request<Job, Consumer> request) {


        if (getHiringRequests().contains(request)) {
            Job job = request.getKey();
            if (job.isFlag()) {
                hire((User) request.getValue1(), request.getKey());
                if (job.getNoOfNeededCandidates() == 0) {
                    for (User u : job.getCandidates())
                        u.update(new JobRejected(job));
                    Application application = Application.getInstance();
                    application.getCompany(job.getCompanyName()).notifyAllObservers(new JobClosed(job));
                    job.setFlag(false);
                    job.setCandidates(null);
                }
                hiringRequests.remove(request);
                updateHiringRequests();
            }
        }
    }

    // respinge un request individual
    public void reject(Request<Job, Consumer> request) {

            if (getHiringRequests().contains(request)) {
                    Job job = request.getKey();
                    User user = (User) request.getValue1();
                    if (job.isFlag()) {
                        hiringRequests.remove(request);
                        job.getCandidates().remove(user);
                        user.update(new JobRejected(job));
                        updateHiringRequests();
                    }
            }
    }

    public void process(Job job) {

        // job invalid
        if (!job.isFlag())
            return;

        Application application = Application.getInstance();
        ArrayList<Request<Job, Consumer>> jobRequests = new ArrayList<>();
        Company company = application.getCompany(getCompanyName());

        // luam requesturile jobului dat
        for (Request<Job, Consumer> r : getHiringRequests()) {
            if (r.getKey() == job) {
                jobRequests.add(r);
            }
        }

        // eliminam din cererile de angajare
        for (Request<Job, Consumer> r : jobRequests) {
                getHiringRequests().remove(r);
        }

        jobRequests.sort(new Comparator<Request<Job, Consumer>>() {
            @Override
            public int compare(Request<Job, Consumer> o1, Request<Job, Consumer> o2) {
                if (o2.getScore() > o1.getScore())
                    return 1;
                else
                    if (o2.getScore().equals(o1.getScore()))
                        return 0;
                return -1;
            }
        });

        while(!jobRequests.isEmpty()) {
            User candidate = (User) jobRequests.get(0).getValue1();

            if (application.remove(candidate)) {
                hire(candidate, job);
                if (job.getNoOfNeededCandidates() == 0) {
                    for (User u : job.getCandidates())
                        u.update(new JobRejected(job));
                    company.notifyAllObservers(new JobClosed(job));
                    job.setCandidates(null);
                    job.setFlag(false);
                    break;
                }
            }
            else
                job.getCandidates().remove(candidate);
            jobRequests.remove(0);
        }

    }

    @Override
    public String toString() {
        return getName();
    }
}