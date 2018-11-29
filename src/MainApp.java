
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws IOException {
        //создаем объект через который будем добавлять и удалять элемент, печатать список элементов и т.д.
        UniLibraryManager uniLibraryManager = new UniLibraryManager();
        int choice;
        Scanner scan = new Scanner(System.in);
        printMenu();//печать меню программы
        System.out.print("\n");
        do {
            System.out.print("Enter the command for Main menu: ");
            choice = scan.nextShort();
            // Обработка комманд
            switch (choice) {
                case 1:
                    printMenu();
                    break;
                case 2:
                    uniLibraryManager = addItem(uniLibraryManager);
                    break;
                case 3:
                    uniLibraryManager = delItem(uniLibraryManager);
                    break;
                case 4:
                    uniLibraryManager.printItemList();
                    break;
                case 5:
                    uniLibraryManager = borrowItem(uniLibraryManager);
                    break;
                case 6:
                    uniLibraryManager = retItem(uniLibraryManager);
                    break;
                case 7:
                    uniLibraryManager.generateReport();
                    break;
                case 8:
                    ViewItems.main(new String[]{}, uniLibraryManager);
                    break;
                case 0:
                    System.exit(0);
                    System.out.print("The program is completed");
                    break;
                default:
                    System.out.print("Invalid command entered!\n");
            }
            System.out.print("\n");
        } while (choice != 0);
    }

    // Метод вывода опций меню
    public static void printMenu() {
        System.out.print("Program menu:\n");
        System.out.print("1 Repeat menu\n");
        System.out.print("2 Add a new item\n");
        System.out.print("3 Delete an item\n");
        System.out.print("4 Print the list of the items\n");
        System.out.print("5 Borrow an item\n");
        System.out.print("6 Return an item\n");
        System.out.print("7 Generate a report\n");
        System.out.print("8 Run GUI\n");
        System.out.print("0 Exit program\n");
    }

    //метод добавления книги или двд
    //имеется меню для выбора типа добавляемого элемента
    //далее пользователь вводит необходимую информацию (ISBN, Title и т.д.)
    public static UniLibraryManager addItem(UniLibraryManager uniLibraryManager) throws IOException {
        System.out.println("Would like to add a book or a DVD?");
        System.out.print("1 Book\n2 DVD\n0 Exit");
        String ISBN = "", title = "", sector = "", authors = "", publisher = "";
        String[] pubDate;
        int choice, day, month, year, pages = 0;
        DateTime publicationDate;
//        Scanner scan = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\n");
        do {
            System.out.print("Enter the command for \"Add new item\" menu: ");
            choice = Short.parseShort(reader.readLine());
            // Обработка комманд меню 2
            switch (choice) {
                case 1:
                    System.out.print("Enter ISBN: ");
                    ISBN = reader.readLine();
                    System.out.print("Enter title: ");
                    title = reader.readLine();
                    System.out.print("Enter sector: ");
                    sector = reader.readLine();
                    System.out.print("Enter authors: ");
                    authors = reader.readLine();
                    System.out.print("Enter publisher: ");
                    publisher = reader.readLine();
                    System.out.print("Enter pages: ");
                    pages = Integer.parseInt(reader.readLine());
                    System.out.print("Enter publication date (dd/mm/yyyy): ");
                    pubDate = reader.readLine().split("/");
                    day = Integer.parseInt(pubDate[0]);
                    month = Integer.parseInt(pubDate[1]);
                    year = Integer.parseInt(pubDate[2]);
                    publicationDate = new DateTime(day, month, year);
                    Book book = new Book(ISBN, title, sector, publicationDate, null, null, authors, publisher, pages);
                    uniLibraryManager.addNewItem(book);
                    break;
                case 2:
                    System.out.print("Enter ISBN: ");
                    ISBN = reader.readLine();
                    System.out.print("Enter title: ");
                    title = reader.readLine();
                    System.out.print("Enter sector: ");
                    sector = reader.readLine();
                    System.out.print("Enter publication date (dd/mm/yyy): ");
                    pubDate = reader.readLine().split("/");
                    day = Integer.parseInt(pubDate[0]);
                    month = Integer.parseInt(pubDate[1]);
                    year = Integer.parseInt(pubDate[2]);
                    publicationDate = new DateTime(day, month, year);
                    System.out.print("Enter languages: ");
                    String languages = reader.readLine();
                    System.out.print("Enter available subtitles (Yes/No): ");
                    String availableSubtitles = reader.readLine();
                    System.out.print("Enter producer: ");
                    String producer = reader.readLine();
                    System.out.print("Enter actors: ");
                    String actors = reader.readLine();
                    DVD dvd = new DVD(ISBN, title, sector, publicationDate, null, null, languages, availableSubtitles, producer, actors);
                    uniLibraryManager.addNewItem(dvd);
                    break;
                case 0:
                    System.out.print("Exiting Add Item menu");
                    break;
                default:
                    System.out.print("Invalid command entered!\n");
            }
            System.out.print("\n");
        } while (choice != 0);

        return uniLibraryManager;
    }

    //метод удаляет элемент из списка, после ввода пользователем ISBN
    public static UniLibraryManager delItem(UniLibraryManager uniLibraryManager){
        System.out.print("To delete an item, enter ISBN: ");
        Scanner scan = new Scanner(System.in);
        String ISBN = scan.nextLine();
        boolean flag = false; // флаг для выода сообщения, что элемент не найден в списке (false - найден, true - не найден)
        for (LibraryItem libItem : uniLibraryManager.listItem) {
            if(libItem.getISBN().equals(ISBN)){ //ищем элемент в списке по ISBN
                uniLibraryManager.deleteItem(libItem); //здесь вызывем метод deleteItem объекта uniLibraryManager
                flag = false;
                break;
            }else
                flag = true;
        }
        if(flag) //выводим сообщение, если элемент не найден
            System.out.println("The Item list does not contain the item by this ISBN");
        return uniLibraryManager;
    }

    //метод позволяет брать почитать/посмотреть книгу/DVD
    public static UniLibraryManager borrowItem(UniLibraryManager uniLibraryManager){
        System.out.print("To borrow an item, enter ISBN: ");
        Scanner scan = new Scanner(System.in);
        String ISBN = scan.nextLine();
        boolean flag = false;// флаг для выода сообщения, что элемент не найден в списке (false - найден, true - не найден)
        for (LibraryItem libItem : uniLibraryManager.listItem) {
            if(libItem.getISBN().equals(ISBN)){ //ищем элемент в списке по ISBN
                uniLibraryManager.borrowItem(libItem); //если находим - вызывем метод borrowItem объекта uniLibraryManager
                flag = false;
                break;
            }else
                flag = true;
        }

        if(flag){//выводим сообщение, если элемент не найден
            System.out.println("The Item list does not contain the item by this ISBN");
        }
        return uniLibraryManager;
    }

    //метод позволяет возвращать книгу или DVD
    public static UniLibraryManager retItem(UniLibraryManager uniLibraryManager){
        System.out.print("To return an item, enter ISBN: ");
        Scanner scan = new Scanner(System.in);
        String ISBN = scan.nextLine();
        boolean flag = false;// флаг для выода сообщения, что элемент не найден в списке (false - найден, true - не найден)
        for (LibraryItem libItem : uniLibraryManager.listItem) {
            if(libItem.getISBN().equals(ISBN)){//ищем элемент в списке по ISBN
                uniLibraryManager.returnItem(libItem);//если находим - вызывем метод returnItem объекта uniLibraryManager
                flag = false;
                break;
            }else
                flag = true;
        }

        if(flag){//выводим сообщение, если элемент не найден
            System.out.println("The Item list does not contain the item by this ISBN");
        }
        return uniLibraryManager;
    }
}
