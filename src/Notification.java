import javax.swing.*;

public abstract class Notification {
    // datele care specifica mesajul
    private final Job job;

    public Notification(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public String generateJobString() {
        return getJob().getJobName() + " din departamentul " +
                getJob().getDepartmentName() + " din compania " + getJob().getCompanyName();
    }


    @Override
    abstract public String toString();
}

class JobAdded extends Notification {

    public JobAdded(Job job) {
        super(job);
    }

    @Override
    public String toString() {
        return generateJobString() + " a fost adaugat";
    }
}

class JobRejected extends Notification {


    public JobRejected(Job job) {
        super(job);
    }

    @Override
    public String toString() {
        return "Ai fost respins de la job-ul " + generateJobString();
    }
}

class JobClosed extends Notification {


    public JobClosed(Job job) {
        super(job);
    }

    @Override
    public String toString() {
        return generateJobString() + " a fost inchis";
    }
}