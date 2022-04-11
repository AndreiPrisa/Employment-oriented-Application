import java.util.ArrayList;
import java.util.StringTokenizer;

public class Information {
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String genre;
    private ArrayList<Language> languages;
    
    public Information(String firstName, String lastName, String email,
                       String phone, String dateOfBirth, String genre, ArrayList<Language> languages) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.genre = genre;
        this.languages = languages;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public void removeLanguage(Language language) {languages.remove(language);}

    // metoda pentru a obtine prenumele + numele
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Name: " + getFullName() + "\nEmail: " + getEmail() +
                "\nPhone: " + getPhone() + "\nDate of birth: " + getDateOfBirth() + "\nGenre: " + getGenre() + "\nLanguages: " + getLanguages().toString();
    }
}

class InvalidDatesException extends Exception {
    public InvalidDatesException() {
        System.out.println("Datele sunt introduse gresit!");
    }
}

class Language {
    private String lastName;
    private String level;

    public Language(String lastName, String level) {
        this.lastName = lastName;
         this.level = level;
    }

    public String getName() {
        return lastName;
    }

    public void setName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return lastName + " - " + level;
    }
}

// clasa folosita pentru reprezentarea datelor calendaristice
class DateFormat implements Comparable<DateFormat> {
    int year;
    int month;
    int day;

    public DateFormat(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateFormat(String format) {
        if (format == null)
            return;

        StringTokenizer stringTokenizer = new StringTokenizer(format, ".");
        this.day = Integer.parseInt(stringTokenizer.nextToken());
        this.month = Integer.parseInt(stringTokenizer.nextToken());
        this.year = Integer.parseInt(stringTokenizer.nextToken());
    }

    @Override
    public int compareTo(DateFormat o) {
        if (this.year == o.year) {
            if (this.month == o.month) {
                return this.day - o.day;
            } else
                return this.month - o.month;
        }
        else
            return this.year - o.year;

    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%d", day, month, year);
    }
}