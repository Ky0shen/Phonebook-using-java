import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PhoneBook {
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Event> events = new LinkedList<Event>();
    public static LinkedList<Contact> contacts = new LinkedList<Contact>();
    
    // Method to add a contact to the phone book
    public static void AddContact() {
        Contact c = new Contact(); 
        System.out.println("Enter the contact's name: ");
        c.name = input.nextLine(); // Read and set the contact's name
        c.name = input.nextLine(); 

        if (!contacts.empty() && contacts.search(c)) {
            System.out.println("Contact found!");
            return;
        }
        System.out.print("Enter the contact's phone number:");
        c.phonenumber = input.nextLine(); // Read and set the contact's phone number

        if (!contacts.empty()) {
            contacts.findFirst();
            for (int i = 0; i < contacts.size; i++) {
                if (contacts.retrieve().phonenumber.compareTo(c.phonenumber) == 0) {
                    System.out.println("Contact found!");
                    return;
                }
                contacts.findNext();
            }
        }
        System.out.print("Enter the contact's email address: ");
        c.emailaddress = input.nextLine(); // Read and set the contact's email address

        System.out.print("Enter the contact's address: ");
        c.address = input.nextLine(); // Read and set the contact's address

        System.out.print("Enter the contact's birthday: ");
        c.birthday = new Date(input.nextLine()); // Read and set the contact's birthday as a Date

        System.out.print("Enter any notes for the contact: ");
        c.notes = input.nextLine(); // Read and set any notes for the contact

        if (contacts.insertSort(c)) {
            System.out.println("Contact added successfully!");
        }
    }

    // Method to search for a contact in the phone book
    public static void SearchContact() {
        System.out.println("Enter search criteria:");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. Email Address");
        System.out.println("4. Address");
        System.out.println("5. Birthday");
        System.out.print("\nEnter your choice: ");
        int choice = input.nextInt();
        
            if (contacts.empty()) {
                System.out.println("Contact not found!");
            } else {
                contacts.findFirst();

                if (choice == 1) {
                    System.out.print("Enter the contact's name: ");
                    String name = input.nextLine();
                    name = input.nextLine();

                    for (int i = 0; i < contacts.size; i++) {
                        if (contacts.retrieve().name.compareTo(name) == 0) {
                            System.out.println("Contact found!");
                            System.out.println(contacts.retrieve());
                            break;
                        }
                        contacts.findNext();
                    }
                } else if (choice == 2) {
                    System.out.print("Enter the contact's phone number:");
                    input.nextLine();
                    String phonenumber = input.nextLine();

                    for (int i = 0; i < contacts.size; i++) {
                        if (contacts.retrieve().phonenumber.compareTo(phonenumber) == 0) {
                            System.out.println("Contact found!");
                            System.out.println(contacts.retrieve());
                            break;
                        }
                        contacts.findNext();
                    }
                } else if (choice == 3) {
                    System.out.print("Enter the contact's email address: ");
                    input.nextLine();
                    String emailaddress = input.nextLine();

                    for (int i = 0; i < contacts.size; i++) {
                        if (contacts.retrieve().emailaddress.compareTo(emailaddress) == 0) {
                            System.out.println("Contact found!");
                            System.out.println(contacts.retrieve());
                        }
                        contacts.findNext();
                    }
                } else if (choice == 4) {
                    System.out.print("Enter the contact's address: ");
                    input.nextLine();
                    String address = input.nextLine();

                    for (int i = 0; i < contacts.size; i++) {
                        if (contacts.retrieve().address.compareTo(address) == 0) {
                            System.out.println("Contact found!");
                            System.out.println(contacts.retrieve());
                        }
                        contacts.findNext();
                    }
                } else if (choice == 5) {
                    System.out.print("Enter the contact's Birthday: ");
                    Date birthday = new Date(input.next());

                    for (int i = 0; i < contacts.size; i++) {
                        if (contacts.retrieve().birthday.compareTo(birthday) == 0) {
                            System.out.println("Contact found!");
                            System.out.println(contacts.retrieve());
                        }
                        contacts.findNext();
                    }
                }
            }
        }
    

    // Method to delete a contact from the phone book
    public static void DeleteContact() {
        // asks the user for the contact's name to be deleted
        Contact contactToRemove = new Contact();
        System.out.print("Enter the contact's name: ");
        input.nextLine();
        contactToRemove.name = input.nextLine();

        // Check if the contacts list is empty
        if (contacts.empty()) {
            System.out.println("Contact not found!");
            return;
        }

        // Try to find and remove the contact with the given name
        Contact removedContact = contacts.remove(contactToRemove);

        // Check if the contact was found and removed
        if (removedContact == null) {
            System.out.println("Contact not found!");
            return;
        }

        // Remove the contact from events and update events if needed
        if (!removedContact.events.empty()) {
            removedContact.events.findFirst();
            for (int i = 0; i < removedContact.events.size; i++) {
                Event event = removedContact.events.retrieve();
                if (events.search(event)) {
                    Event updatedEvent = events.retrieve();
                    updatedEvent.removeContact(contactToRemove.name);

                    if (updatedEvent.contacts_names.empty()) {
                        events.remove(updatedEvent);
                        System.out.println("Deleted event - No contact participates");
                    } else {
                        events.update(updatedEvent);
                    }
                }
                removedContact.events.findNext();
            }
        }

        System.out.println("Contact Deleted Successfully!");
        System.out.println(removedContact);
    }

    // Method to schedule an event
    public static void ScheduleEvent() {
        Event e = new Event();
        Contact c = new Contact();

        System.out.print("Enter event title: ");
        input.nextLine();
        e.title = input.nextLine();
        System.out.print("Enter contact name: ");
        c.name = input.nextLine();

        if (!contacts.empty() && contacts.search(c)) {
            System.out.print("Enter event date and time (MM/DD/YYYY HH:MM): ");
            String dateTimeStr = input.nextLine();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            dateFormat.setLenient(false); // Disable lenient parsing
            try {
                e.date = dateFormat.parse(dateTimeStr);
            } catch (Exception ex) {
                System.out.println("Invalid date and time format!");
            }
            System.out.print("Enter event location: ");
            e.location = input.nextLine();

            c = contacts.retrieve();
            boolean addedEventToContact = c.addEvent(e);
            if (addedEventToContact) {
                if (!events.empty() && events.search(e)) {
                    Event eventFound = events.retrieve();
                    eventFound.addContact(c.name);
                    events.update(eventFound);
                } else {
                    e.addContact(c.name);
                    events.insertSort(e);
                }
                System.out.println("Event scheduled successfully!");
            } else {
                System.out.println("Contact has a conflicting event!");
            }
        } else {
            System.out.println("Contact not found!");
        }
    }

    // Method to print event details for a contact or by event title
    public static void PrintEvent() {
        Contact c = new Contact();
        System.out.println("Enter search criteria:");
        System.out.println("1. Contact name");
        System.out.println("2. Event title");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        if (choice == 1) {
            System.out.print("Enter the contact name: ");
            input.nextLine();
            c.name = input.nextLine();

            if (!contacts.empty() && contacts.search(c)) {
                c = contacts.retrieve();
                if (!c.events.empty()) {
                    c.events.findFirst();
                    for (int i = 0; i < c.events.size; i++) {
                        Event e = c.events.retrieve();
                        if (!events.empty() && events.search(e)) {
                            System.out.println(events.retrieve());
                        }
                        c.events.findNext();
                    }       
                } else {
                    System.out.println("No events found for this contact.");
                }
            } else {
                System.out.println("Contact not found!");
            }
        } else if (choice == 2) {
            System.out.print("Enter the event title: ");
            input.nextLine();
            String eventTitle = input.nextLine();

            Event searchEvent = new Event();
            searchEvent.title = eventTitle;

            if (!events.empty() && events.search(searchEvent)) {
                System.out.println("Event found!");
                System.out.println(events.retrieve());
            } else {
                System.out.println("Event not found!");
            }
        }
    }

    // Method to print contacts by first name
    public static void PrintContactsFirstName() {
        System.out.print("Enter the first name:");
        input.nextLine();
        String fname = input.nextLine();

        if (contacts.empty())
            System.out.println("No Contacts found!");

        contacts.findFirst();
        for (int i = 0; i < contacts.size; i++) {
            String name = contacts.retrieve().name;
            String[] FirstLast = name.split(" ");

            if (FirstLast.length > 0 && FirstLast[0].compareToIgnoreCase(fname) == 0) {
                System.out.println(contacts.retrieve() + "\n");
            }
            contacts.findNext();
        }
    }

    // Method to print all events alphabetically
    public static void PrintAllEvents() {
        if (!events.empty()) {
            events.findFirst();
            for (int i = 0; i < events.size; i++) {
                System.out.println((i + 1) + ". Event : " + events.retrieve().title);
                events.findNext();
            }
        } else
            System.out.println("No events found!");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Linked List Phonebook!");
        int choice;

        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Add a contact");
            System.out.println("2. Search for a contact");
            System.out.println("3. Delete a contact");
            System.out.println("4. Schedule an event");
            System.out.println("5. Print event details");
            System.out.println("6. Print contacts by first name");
            System.out.println("7. Print all events alphabetically");
            System.out.println("8. Exit");
            System.out.print("\nEnter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1: {
                    AddContact();
                }
                break;

                case 2: {
                    SearchContact();
                }
                break;

                case 3: {
                    DeleteContact();
                }
                break;

                case 4: {
                    ScheduleEvent();
                }
                break;

                case 5: {
                    PrintEvent();
                }
                break;

                case 6: {
                    PrintContactsFirstName();
                }
                break;

                case 7: {
                    PrintAllEvents();
                }
                break;
                case 8:
                System.out.println("Goodbye!");
                break;
                default:
                System.out.println("Wrong choice! Try again");
            }
            System.out.println("\n\n");
        } while (choice != 8);
    }
}
