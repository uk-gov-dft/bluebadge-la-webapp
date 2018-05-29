package uk.gov.service.bluebadge.test.acceptance.util;

import java.time.DayOfWeek;
import java.time.Year;
import java.util.Random;

import static java.time.OffsetDateTime.now;

public class NameGenerator {

    private static String first_name;

    private static String last_name;

    private static String[] Beginning = {"Ada", "Albert", "Alexandra", "Alfredo", "Allen", "Andre", "Angelica", "Anna",
            "Anthony", "Antonio", "Ashley", "Audrey", "Beatrice", "Benjamin", "Billy", "Bobby", "Bradley", "Bryant",
            "Candace", "Carole", "Carrie", "Claire", "Clifford", "Clint", "Clyde", "Cory", "Dale", "Danielle", "Daryl",
            "Delia", "Devin", "Douglas", "Eddie", "Ella", "Erica", "Erika", "Eva", "Frank", "Gayle", "George",
            "Georgia", "Geraldine", "Gina", "Gwen", "Hector", "Homer", "Irene", "John","James", "Jamie", "Jeremiah", "Joann",
            "Josefina", "Juan", "Karen", "Kenneth", "Laurie", "Lee", "Leland", "Leroy", "Levi", "Lewis", "Lillian",
            "Lillie", "Lorenzo", "Louise", "Lucas", "Lynn", "Marc", "Marcella", "Marlon", "Marvin", "Micheal",
            "Miranda", "Miriam", "Misty", "Naomi", "Natasha", "Nelson", "Oliver", "Pete", "Rafael", "Randall", "Raul",
            "Rebecca", "Reginald", "Roger", "Ruby", "Rufus","Sneha","Swati","Sabrina", "Sean", "Steven", "Stuart", "Terence", "Terry",
            "Van", "Velma", "Vincent", "Wanda", "Willard", "Winifred", "Sam", "Dave", "Paul", "Jim", "Ali", "Miguel", "John", "Matt"};

    private static String[] End = {"Rao", "Kumar", "Chowdary", "Reddy", "Naidu", "Raju", "Varma", "Achari", "Sharma", "Chari",
            "Sharma", "Hegde", "Shenoy", "Shett", "Shetty", "Mallya", "Kini", "Kheny", "Pai", "Kamath", "Prabhu", "Nayak",
            "Rai", "Kudva", "Bhandary", "Baliga", "Padukone", "Mogaveera", "Adiga", "Alva", "Payyade", "Kothari", "Kadamba",
            "Bajpe", "Ullal", "Jagath", "Jayakody", "Jayaratne", "Jayasinghe",  "Devapriya", "Dinuk", "Dissanayake", "Rajasekara","Nair",
            "Ramanayake", "Ranaweera", "Ratnayake", "Abdul","Khan","Syed","Reynolds", "Richardson", "Rios", "Ross", "Russell", "Saunders",
            "Sharp", "Simon", "Smith", "Steele", "Stephens", "Stokes", "Summers", "Thomas", "Thompson", "Tyler", "Wagner",
            "Ward", "Washington", "Watkins", "Watson", "Weber", "West", "Willis", "Young", "Zimmerman"};

    public NameGenerator() {

        Random rand = new Random();

        first_name = Beginning[rand.nextInt(Beginning.length)];

        last_name = End[rand.nextInt(End.length)];

    }

    public String get_first_name() {


        return first_name;

    }

    public String get_last_name() {


        return last_name;

    }

}
