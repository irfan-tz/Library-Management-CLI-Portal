package com.example;
import java.util.*;  

public class Main {
    public static void main(String[] args) {
        while (true){
            System.out.println("\n******************WELCOME TO THE LIBRARY, STRANGER!******************");
            System.out.println("\nEnter \n1 if you are a librarian \n2 if you are a student\n3 Exit\n");
            Scanner sc = new Scanner(System.in);
            int user = sc.nextInt();

            Library library = new Library();

            if (user == 1){                                 //FOR LIBRARIAN
                while (true) {
                    System.out.print("\nWelcome, librarian.\n");
                    System.out.println("Enter \n 1 - Register a member \n 2 - Remove a member \n 3 - Add a book\n 4 - Remove a book\n 5 - View all member alongwith issues books and due fines\n 6 - View all books\n 7 - Back");
                    Scanner sc0 = new Scanner(System.in);
                    user = sc0.nextInt();

                    if (user == 1) {                                //Register member
                        Scanner sc1 = new Scanner(System.in);
                        System.out.printf("Enter the name: ");
                        String name = sc1.nextLine();
                        System.out.printf("Enter the age: ");
                        int age = sc1.nextInt();
                        System.out.printf("Enter phone no.: ");
                        long phone = sc1.nextLong();
                        library.addStudent(name,age,phone);
                        
                    }

                    else if (user == 2) {                            //Remove member
                        
                        System.out.printf("Enter the name: ");
                        String name = sc.nextLine();
                        System.out.printf("Enter phone no.: ");
                        long phone = sc.nextLong();
                        sc.nextLine();
                        library.removeStudent(name,phone);
                        System.out.println(name + " deleted.");
                    }

                    else if (user == 3) {                            //Add book
                        
                        Scanner sc1 = new Scanner(System.in);
                        System.out.printf("Enter title.: ");
                        String title = sc1.nextLine();
                        System.out.println("title: "+title);
                        System.out.printf("Enter the author: ");
                        String author = sc1.nextLine();

                        System.out.printf("Enter number of copies: ");
                        int copies = sc1.nextInt();

                        library.addBook(title,author,copies);
                        System.out.println(title + " added to the catalogue.");

                    }

                    else if (user == 4) {                            //Remove book
                        
                        library.viewBooks();
                        System.out.printf("Enter the Title: ");
                        String title = sc.nextLine();

                        int removed = library.removeBook(title);
                        if (removed==1) {
                            System.out.println("book " + title + " removed from the catalogue.");}
                        else {
                            System.out.println("This book does not exist.");
                        }
                    }

                    else if (user == 5) {                            //View members with books and fines
                        System.out.print("The members with their issued books and due fines are:\n");
                        library.viewMembers();
                    }

                    else if (user == 6) {                            //View all books
                        System.out.print("Books in our catalogue are: \n");
                        library.viewBooks();
                    }

                    else if (user == 7) { 
                        break;                                       
                    }

                    else {
                        System.out.println("Incorrect input.");
                    } 
                }
            }

            else if (user == 2){                              //FOR STUDENT LOGIN

                    Scanner sc0 = new Scanner(System.in);
                    System.out.print("\n**************WELCOME TO LOGIN PAGE**************\n");
                    System.out.print("Enter name: ");
                    String name = sc0.nextLine();
                    System.out.print("Enter phone no :");
                    long phone = sc0.nextLong();
                    Student result = library.login_student(name,phone);

                    if (result==null){                                
                        return;
                    }
                    while (true){
                    System.out.println("Enter \n 1 - to view all book \n 2 - to list my books \n 3 - to issue a book\n 4 - to return a book\n 5 - to pay your fine \n 6 - to logout\n");
                    Scanner sc3 = new Scanner(System.in);
                    user = sc3.nextInt();

                    if (user == 1) {                                        //View books
                        
                        System.out.print("List of all books in the library.\n");
                        library.viewBooks();
                    }

                    else if (user == 2) {                                    //User issued books
                        
                        System.out.println("1. " + result.issued_books[0] + "\n" + "2. " + result.issued_books[1]);
                    }

                    else if (user == 3) {                                    //Issue book
                        
                        List <Book> all_books = Library.books;  
                        for (int i = 0; i < all_books.size(); i++){
                            Book book_reference = all_books.get(i);
                            System.out.print("Serial   |   Title    |     Author    |     Copies\n");
                            System.out.printf(" %s  |  %s  |  %s  |  %d\n",i+1,book_reference.title,book_reference.author,book_reference.copies);
                        } 
                        System.out.print("Press -1 to go back");

                        int serial = sc.nextInt();

                        if (serial!=-1){
                        library.issue(serial,result);   }
                    }

                    else if (user == 4) {                                    //Return book

                        String book0 = result.issued_books[0]==null ? null : Library.books.get(Character.getNumericValue((result.issued_books[0]).charAt(0))).title;
                        String book1 = result.issued_books[1]==null ? null : Library.books.get(Character.getNumericValue((result.issued_books[1]).charAt(0))).title;
                        
                        System.out.println("BookID   |   Title"+"\n");
                        System.out.println(result.issued_books[0]+ "  |  " + book0 + "\n" +result.issued_books[1]+ "  |  " + book1 + "\n");
                        System.out.print("Press -1 to go back");

                        String id = sc.nextLine();

                        if (id!=-1){
                        library.returnBook(id,result);  }
                    }
                    
                    else if (user == 5) {                                    //Pay fine
                        library.payFine(result);
                    }

                    else if (user == 6) {                                     //Go back 
                        break;         
                    }

                    else {
                        System.out.println("Incorrect input.");
                    }
                }    
            }
            
            else if (user == 3) {                                   //EXIT PROGRAM
                sc.close(); 
                return; 
            }

            else {
                System.out.print("Incorrect input.");
            }

        }
    } 
}

class Library{

     private static List<Student> students = new ArrayList<>();
     private List<Student> students_modified = new ArrayList<>();

     public static List<Book> books = new ArrayList<>();

    public Student login_student(String Name, long phone){
        for (int i = 0; i < students.size(); i++) {
            Student student_reference = students.get(i);
            if (student_reference.name.equals(Name) && student_reference.phone_no == phone){
                System.out.printf("WELCOME! %s.\n",Name);
                return student_reference; 
            }
        }
        System.out.println("YOU HAVE ENTERED WRONG NAME OR PHONE NO.");
        System.out.printf("Member with Name: %s and Phone No: %d is not in our records.\n",Name,phone);
        return null;
    }

    public void addStudent(String name, int age, long phone_no) {
        Student student0 = new Student(students.size(), name, age, phone_no);
        students.add(student0);
        
        System.out.println("Student added: " + name +"\n");
    }

    public void removeStudent(String Name, long phone) {
        
        for (int i = 0; i < students.size(); i++) {
            Student student_reference = students.get(i);
        //System.out.println("Book added: " + title);
            if (student_reference.name.equals(Name) && student_reference.phone_no == phone){
            }
            else{
                this.students_modified.add(student_reference);
             }
        }   
        students = students_modified;
    }
    
    public void viewMembers() {
        System.out.println("Student ID " + " | " + "Student Name"  + " | " + "First Issued Book" + " | " + "Second Issued Book" + " | " + "Fine");
        for (int i = 0; i < students.size(); i++) {
            Student student_reference = students.get(i);
            System.out.println(student_reference.id + " | "  + student_reference.name + " | " + student_reference.issued_books[0] + " | " + student_reference.issued_books[1] + " | " + student_reference.fine);
        }
    }


    public int calculateFine(Student std) {
        long time = System.currentTimeMillis();

        //System.out.print("time: " + time+ "\n");
        //System.out.print("fine: " + std.fine+ "\n"); 
        if (std.time[0] != 0 & time  - std.time[0] > 10*1000) {
            std.fine += (int) (3*(int)((time - std.time[0] - 10000)/1000));
            //System.out.print("\nCurrent time: "+System.currentTimeMillis()+"\n");
            //System.out.print("\nLast Issued Book time: "+std.time[0] + "\n");
        } 
        if (std.time[1] != 0 & time-std.time[1] > 10*1000) {
            //System.out.print("\n"+std.time[1]+"It goes! \n");
            std.fine += (int) (3*(int)((time - std.time[1] - 10000)/1000));
        }

        return std.fine; 
    }


    public void addBook(String title, String author, int copies) {
        Book book0 = new Book(books.size(), title, author, copies);
        books.add(book0);
        //System.out.println("Book added: " + title);
    }


    public int removeBook(String title) {
    for (int i = 0; i < books.size(); i++) {
        Book book = books.get(i);
        if (title.equals(book.title)) {
            if (book.copies > 0) {
                
                String[] bookID_new = new String[book.copies - 1];
                for (int j = 0; j < book.copies - 1; j++) {
                    bookID_new[j] = book.bookID[j];
                }

                book.bookID = bookID_new;
                book.copies--;
                System.out.println("Book removed: " + title);
            } else {
                System.out.println("Book has already been removed.");
            }
            return 1; 
        }
    }
    return 0;
}

    public void viewBooks() {
        System.out.println("Book Title " + " | " + "Author"  + " | " + "No. of copies");
        for (int i = 0; i < books.size(); i++) 
        {       Book Book_reference = books.get(i);
                System.out.println( Book_reference.title + " | " + Book_reference.author + " | " + Book_reference.copies+"\n");
        } 
    }


    public void issue(int serial, Student std) {

        calculateFine(std);

        if (std.fine != 0) {
            System.out.print("You have unpaid dues to fill. \nReturning...");

            try {
            Thread.sleep(5 * 1000);
            }  catch (InterruptedException e){}

            return;
        }

        Book book_reference = books.get(serial - 1);

        String book_issued = book_reference.bookID[book_reference.copies-1]; 

        for (int i=0; i<book_reference.copies -1; i++){
            book_reference.bookID[i] = book_reference.bookID[i];
        }

        book_reference.copies -= 1;

        if (std.issued_books[0] == null) {
            std.issued_books[0] = book_issued;
            std.time[0] = System.currentTimeMillis();
        }

        else if (std.issued_books[1] == null) {
            std.issued_books[1] = book_issued;
            std.time[1] = System.currentTimeMillis();
        } 
        else {
            System.out.println("You already possess two books of ours. You cannot issue another.");
            return;
        }
        System.out.print("The book has now been issued to you.\n");
    }

    public void returnBook(String id, Student std) {

        int i;
        for (i = 0; i < 2; i++) {
            if (id.equals(std.issued_books[i])) {
                break;
            }
        }

        if (i == 2) {
            System.out.print("This book has not been issued to you.");
            return;
        }
        
        int issued_book_index = Character.getNumericValue(std.issued_books[i].charAt(0)) ;

        std.issued_books[i] = null;

        books.get(issued_book_index).copies += 1;

        System.out.println(books.get(issued_book_index).title + " has returned to its library.");

        int fines = calculateFine(std);
        std.time[i] = 0;

        if (fines != 0) {
            System.out.print("You have a total fine of Rs. " + fines);
        }
    }

    public void payFine(Student std) {

        int fines = calculateFine(std);

        std.fine = 0;

        System.out.printf("Your dues of %d has been settled by our Institute of higher order.\n",fines);
        System.out.print("Your total fine now is Rs. " + std.fine + "\n");

    }
}

class Book{
    public int ID;
    public String bookID[] ;
    public String title;
    public String author;
    public int copies;

    public void idMaker() {                                                 //adds the first number as the serial number of the book when added,
                                                                            //rest of the number as the serial of the copy number
        for (int i=0; i<this.copies;i++){
            bookID[i] = Integer.toString((ID)) + Integer.toString((i));
        }                                                                    
    }

    public Book(int id, String title, String author, int copies){
        this.ID = id;
        this.title = title;
        this.author = author;
        this.copies = copies;
        this.bookID = new String[copies];
        idMaker();
    }
}

class Student{
    public int id=-1;
    public String name;
    public int age;
    public long phone_no;
    public int fine=0;
    public String issued_books[] = {null, null};
    public long time[]={0,0};                                                //days the book been with a student

    public Student (int id, String name, int age, long phone_no){
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone_no = phone_no;
    }
}