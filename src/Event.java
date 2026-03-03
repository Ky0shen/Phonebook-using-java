import java.util.Date;  
  
public class Event implements Comparable<Event> {  
    String title;  
    Date date; 
    String time;  
    String location;  
    LinkedList<String> contacts_names;  
  
    public Event() {  
        this.title = "";  
        this.date = null;  
        this.time = "";  
        this.location = "";  
        this.contacts_names = new LinkedList<String>();  
    }  
      
    public Event(String title, String date, String time, String location, String contact) {  
        this.title = title;  
        this.date = new Date(date);  
        this.time = time;  
        this.location = location;  
        this.contacts_names = new LinkedList<String>();  
        contacts_names.insertSort(contact);  
    }  
  
    public boolean addContact(String contact) {  
        // Time complexity: O(n)
        return contacts_names.insertSort(contact);  
    }  
      
    public boolean removeContact(String contact) {  
        // Removes a contact from the list of contact names associated with the event.
        // Returns true if the contact was found and removed, false otherwise.
        // Time complexity: O(n)
        String name = contacts_names.remove(contact);  
        if (name != null)  
            return true;   
        return false;  
    }  
  
    @Override  
    public String toString() {  
    	String str = "\nEvent title: " + title + "\nContacts name: ";

        contacts_names.findFirst();
        for (int i = 0; i < contacts_names.size; i++) {
            str += contacts_names.retrieve() + "\t";
            contacts_names.findNext();
        }
        str += "\nEvent date and time (MM/DD/YYYY HH:MM): " + date + time + "\nEvent location: " + location;
        return str;
    }  
  
    @Override  
    public int compareTo(Event obj) {  
            return this.title.compareToIgnoreCase(obj.title);  
    }    
}