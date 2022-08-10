import models.City;
import models.Client;
import repositories.CityRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class CityApplication {
    protected static ResourceBundle userResourceBundle;
    private static final String[] locales = new String[]{"en_US", "pl_PL", "uk_UA"};
    private static final CityRepository repo = new CityRepository();

    public static void main(String[] args) {
        // Get user's default locale
        // Load ResourceBundle for the locale
        Locale locale = Locale.getDefault();
        userResourceBundle = ResourceBundle.getBundle("resources/MessageBundle", locale);

        Scanner scanner = new Scanner(System.in);
        System.out.println(userResourceBundle.getString("message.welcome"));
        System.out.print(userResourceBundle.getString("message.enterYourName") + ' ');

        String name = scanner.nextLine();
        Client client = new Client(locale, name);

        System.out.println(userResourceBundle.getString("message.hello") + ' ' + client.getUsername() + "!");

        menu();
    }

    private static void changeLocale() {
        System.out.println(userResourceBundle.getString("message.chooseLocale"));
        System.out.println(Arrays.toString(locales));
        Scanner scanner = new Scanner(System.in);
        String newLocale = scanner.nextLine();
        try {
            userResourceBundle = ResourceBundle.getBundle("resources/MessageBundle", new Locale(newLocale));
        } catch (MissingResourceException e) {
            System.out.println(userResourceBundle.getString("message.tryAgain"));
            changeLocale();
        }
    }

    private static void menu() {
        Scanner scanner = new Scanner(System.in);

        // Print out the menu
        System.out.println(userResourceBundle.getString("menu.chooseAction"));
        System.out.println("0 - " + userResourceBundle.getString("menu.printAll"));
        System.out.println("1 - " + userResourceBundle.getString("menu.addCity"));
        System.out.println("2 - " + userResourceBundle.getString("menu.removeCity"));
        System.out.println("3 - " + userResourceBundle.getString("menu.editCity"));
        System.out.println("4 - " + userResourceBundle.getString("menu.changeLocale"));
        System.out.println("5 - " + userResourceBundle.getString("menu.exit"));

        String answer = scanner.nextLine();

        // If Menu Action is "Print All"
        if (answer.equals("0")) {
            try{
                printAll();
            } catch (Exception ex) {
                System.out.println(userResourceBundle.getString("message.noChanges"));
                System.out.println(userResourceBundle.getString("message.tryAgain"));
            }
            menu();
        }

        // If Menu Action is "Add City"
        if (answer.equals("1")) {
            try{
                addCity();
            } catch (Exception ex) {
                System.out.println(userResourceBundle.getString("message.noChanges"));
                System.out.println(userResourceBundle.getString("message.tryAgain"));
            }
            menu();
        }

        // If Menu Action is "Remove City"
        if (answer.equals("2")) {
            try{
                removeCity();
            } catch (Exception ex) {
                System.out.println(ex.toString());
                System.out.println(userResourceBundle.getString("message.noChanges"));
                System.out.println(userResourceBundle.getString("message.tryAgain"));
            }
            menu();
        }

        // If Menu Action is "Edit City"
        if (answer.equals("3")) {
            try{
                editCity();
            } catch (Exception ex) {
                System.out.println(userResourceBundle.getString("message.noChanges"));
                System.out.println(userResourceBundle.getString("message.tryAgain"));
            }
            menu();
        }

        // If Menu Action is "Change Locale"
        if (answer.equals("4")) {
            try{
                changeLocale();
            } catch (Exception ex) {
                System.out.println(userResourceBundle.getString("message.noChanges"));
                System.out.println(userResourceBundle.getString("message.tryAgain"));
            }
            menu();
        }

        if (answer.equals("5")) {
            System.exit(0);
        }
    }

    private static void printAll() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, userResourceBundle.getLocale());
        try {
            for (City city: repo.getAll()) {
                // We get the city visited Date and format it according to the user's Locale
                System.out.println(city.getCityName() + ':' + ' ' + df.format(city.getVisitedDate()));
            }
        } catch (Exception ex) {
            System.out.println(userResourceBundle.getString("message.noCities"));
        }
    }

    private static void editCity() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, userResourceBundle.getLocale());
        City city = new City();
        City newCity = new City();

        // Print all cities so we can choose from them
        printAll();
        System.out.println(userResourceBundle.getString("message.chooseEdit"));

        // Set chosen city name
        System.out.print(userResourceBundle.getString("word.city") + ':' + ' ');
        city.setCityName(scanner.nextLine());

        // Set chosen city visited date
        System.out.print(userResourceBundle.getString("word.date") + ':' + ' ');
        Date date = df.parse(scanner.nextLine());
        city.setVisitedDate(date);

        for (City repoCity: repo.getAll()) {
            if (repoCity.equals(city)) {
                // Set new city name
                System.out.print(userResourceBundle.getString("message.newCityName") + ':' + ' ');
                repoCity.setCityName(scanner.nextLine());

                // Set chosen city visited date
                System.out.print(userResourceBundle.getString("message.newCityDate") + ':' + ' ');
                Date newDate = df.parse(scanner.nextLine());
                repoCity.setVisitedDate(date);

                System.out.println(userResourceBundle.getString("message.cityEdited"));
                return;
            }
        }

        System.out.println(userResourceBundle.getString("message.noChanges"));
        System.out.println(userResourceBundle.getString("message.tryAgain"));
    }

    private static void removeCity() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, userResourceBundle.getLocale());
        City city = new City();

        // Print all cities so we can choose from them
        printAll();
        System.out.println(userResourceBundle.getString("message.chooseDelete"));

        // Set chosen city name
        System.out.print(userResourceBundle.getString("word.city") + ':' + ' ');
        city.setCityName(scanner.nextLine());

        // Set chosen city visited date
        System.out.print(userResourceBundle.getString("word.date") + ':' + ' ');
        Date date = df.parse(scanner.nextLine());
        city.setVisitedDate(date);

        for (City repoCity: repo.getAll()) {
            if (repoCity.equals(city)) {
                repo.deleteCity(repoCity);
                System.out.println(userResourceBundle.getString("message.cityDeleted"));
                return;
            }
        }

        System.out.println(userResourceBundle.getString("message.noChanges"));
        System.out.println(userResourceBundle.getString("message.tryAgain"));
    }

    private static void addCity() throws ParseException {
        // Take user's input
        // Create a new City instance to fill it in
        Scanner scanner = new Scanner(System.in);
        City newCity = new City();

        // Add the user's locale to the City instance
        newCity.setCityLocale(userResourceBundle.getLocale());

        // Print out the city name prompt and set the new name
        System.out.println(userResourceBundle.getString("message.addCity"));
        System.out.print(userResourceBundle.getString("message.addCityName"));
        newCity.setCityName(scanner.nextLine());

        // Print out the date prompt and set the new date
        System.out.print(userResourceBundle.getString("message.addCityDate") + ' ');

        // Here we transform the input date according to the user's locale
        // and save it as usual Date class
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, userResourceBundle.getLocale());
        System.out.println('(' + userResourceBundle.getString("message.forExample") + ' ' + df.format(new Date()) + ')');
        Date newDate = df.parse(scanner.nextLine());
        newCity.setVisitedDate(newDate);

        repo.addCity(newCity);

        // Tell user we saved a new city!
        System.out.println(userResourceBundle.getString("message.saved"));
    }
}
