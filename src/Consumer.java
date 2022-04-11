import java.util.*;

public abstract class Consumer {
    private Resume resume;
    private ArrayList<Consumer> friends;

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public ArrayList<Consumer> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Consumer> friends) {
        this.friends = friends;
    }

    public void  changeFirstName(String firstName) {
        resume.information.setFirstName(firstName);
    }

    public void  changeLastName(String lastName) {
        resume.information.setLastName(lastName);
    }

    public void  changeEmail(String email) {
        resume.information.setEmail(email);
    }

    public void  changePhone(String phone) {
        resume.information.setPhone(phone);
    }

    public void  changeGenre(String genre) {
        resume.information.setGenre(genre);
    }

    public void  changeDateOfBirth(String dateOfBirth) {
        resume.information.setDateOfBirth(dateOfBirth);
    }

    public void addLanguage(Language language) {
        resume.information.addLanguage(language);
    }

    public void removeLanguage(Language language) {
        resume.information.removeLanguage(language);
    }


    public void add(Education education) {
        resume.educationSortedSet.add(education);
    }

    public void add(Experience experience) {
        resume.experienceSortedSet.add(experience);
    }

    public void add(Consumer consumer) {
        if (friends == null)
            friends = new ArrayList<>();
        friends.add(consumer);
    }

    public int getDegreeInFriendship(Consumer consumer) {
        int depth = 0;
        ConsumerNode currentConsumerNode;
        LinkedList<ConsumerNode> consumerLinkedList = new LinkedList<>();
        LinkedList<Consumer> visited = new LinkedList<>();
        consumerLinkedList.addLast(new ConsumerNode(this, depth));

        while(!consumerLinkedList.isEmpty()) {
            currentConsumerNode = consumerLinkedList.poll();
            depth = currentConsumerNode.depth;
            if (!visited.contains(currentConsumerNode.consumer)) {
                visited.add(currentConsumerNode.consumer);
                for (Consumer c : currentConsumerNode.consumer.friends) {
                    if (c.equals(consumer))
                        return depth + 1;
                    consumerLinkedList.addLast(new ConsumerNode(c, depth + 1));
                }
            }
        }
        return 0;
    }

    public String getName() {
        return resume.getInformation().getFullName();
    }

    public void remove(Consumer consumer) {
        friends.remove(consumer);
    }

    public Integer getGraduationYear() {
        for (Education e : resume.educationSortedSet) {
            if (e.getLevelOfEducation().compareTo("college") == 0)
                if (e.getEndDate() == null)
                    return null;
                else
                    return e.getEndDate().year;
        }
        return null;
    }

    // metoda pentru a calcula numarul anilor de experienta
    public int getNumberOfYearsOfExperience() {
        int numberOfYearsOfExperience = 0, currentExperience;

        if (resume.getExperienceSortedSet() == null)
            return 0;

        for (Experience e : resume.experienceSortedSet) {
            currentExperience = e.getEndDate().year * 12 +
                    e.getEndDate().month - (e.getStartDate().year * 12 + e.getStartDate().month);
            if (currentExperience % 12 >= 3)
                numberOfYearsOfExperience++;
            numberOfYearsOfExperience += currentExperience / 12;
        }

        return  numberOfYearsOfExperience;
    }

    public Double meanGPA() {
        double total = 0;
        for (Education e : resume.educationSortedSet) {
            total += e.getGrade();
        }
        return total / resume.educationSortedSet.size();
    }

    static class Resume {
        private final Information information;
        private final SortedSet<Education> educationSortedSet;
        private final SortedSet<Experience> experienceSortedSet;

        private Resume(ResumeBuilder builder) throws ResumeIncompleteException {
            if (builder.information == null || builder.educationTreeSet == null
                    || builder.educationTreeSet.isEmpty())
                throw new ResumeIncompleteException();
            this.information = builder.information;
            this.educationSortedSet = builder.educationTreeSet;
            this.experienceSortedSet = builder.experienceSortedSet;
        }

        public Information getInformation() {
            return information;
        }

        public SortedSet<Education> getEducationSortedSet() {
            return educationSortedSet;
        }

        public SortedSet<Experience> getExperienceSortedSet() {
            return experienceSortedSet;
        }

        @Override
        public String toString() {
            return information.toString() + "\nEducation:\n" + educationSortedSet + "\nExperience:\n" + experienceSortedSet;
        }

        public static class ResumeBuilder {
            private final Information information;
            private final TreeSet<Education> educationTreeSet;
            private SortedSet<Experience> experienceSortedSet;

            public ResumeBuilder(Information information, TreeSet<Education> educationSortedSet) {
                this.information = information;
                this.educationTreeSet = educationSortedSet;
            }

            public ResumeBuilder(Information information, Education education) {
                this.information = information;
                this.educationTreeSet = new TreeSet<>();
                this.educationTreeSet.add(education);
            }

            public ResumeBuilder add(Education education) throws ResumeIncompleteException {
                if (educationTreeSet == null)
                    throw new ResumeIncompleteException();
                educationTreeSet.add(education);
                return this;
            }

            public ResumeBuilder add(Experience experience) {
                if (experienceSortedSet == null)
                    experienceSortedSet = new TreeSet<>();
                experienceSortedSet.add(experience);
                return this;
            }

            public ResumeBuilder experienceSortedSet(SortedSet<Experience> experienceSortedSet) {
                this.experienceSortedSet = experienceSortedSet;
                return this;
            }

            public Resume build() {
                try {
                    return new Resume(this);
                } catch (ResumeIncompleteException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    class ConsumerNode {
        Consumer consumer;
        int depth;

        public ConsumerNode(Consumer consumer, int depth) {
            this.consumer = consumer;
            this.depth = depth;
        }
    }

    @Override
    public String toString() {
        return resume.information.getFullName();
    }

    static class ResumeIncompleteException extends Exception {
        public ResumeIncompleteException() {
            System.out.println("Resume-ul nu a fost completat corespunzator!");
        }
    }
}

