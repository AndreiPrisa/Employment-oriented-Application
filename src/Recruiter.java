import java.util.ArrayList;

public class Recruiter extends Employee {
    private double rating;

    public Recruiter(String companyName, double salary) {
        super(companyName, salary);
        rating = 5;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // de ce e de tip int? :question
    public int evaluate(Job job, User user) {
            Request<Job, Consumer> request = new Request<>(job, user, this,
                    rating * user.getTotalScore());

            Application application = Application.getInstance();
            Company company = application.getCompany(getCompanyName());
            company.getManager().addHiringRequest(request);
            job.addCandidate(user);
        rating += 0.1;
        return (int) (rating * user.getTotalScore());
    }
}

class Request<K, V> {
    private K key;
    private V value1, value2;
    private Double score;

    public Request(K key, V value1, V value2, Double score) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.score = score;
    }

    public K getKey() {
        return key;
    }

    public V getValue1() {
        return value1;
    }

    public V getValue2() {
        return value2;
    }

    public Double getScore() {
        return score;
    }

    public String toString() {
        return key.getClass().getName() + ": " + key + " ; " + value1.getClass().getName() + ": " + value1 + " ; " + value2.getClass().getName() + ": " + value2 +
                " ; Score: " + score;
    }
}