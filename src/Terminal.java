import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream("Users.txt",true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream("Users.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        List<User> list = new ArrayList<>();

        printLogo();
        System.out.println("Welcome to my Terminal!!!\n");
        System.out.println("Please enter 'r' for registration, 'l' for login or 'out' for logout");
        File curDir = new File(new File("").getAbsolutePath());

        while (true) {
            System.out.print(curDir.getAbsolutePath() + " > ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String command = input.split(" ")[0];
            if(command.equals("exit")){
                break;
            }

            switch (command) {
                case "ls":
                    listDirectories(curDir);
                    break;

                case "mkdir":
                    String folderName = input.split(" ")[1];
                    (new File(curDir, folderName)).mkdir();
                    break;

                case "cd":
                    String targetDir = input.split(" ")[1];
                    if (targetDir.equals("..")) {
                        curDir = curDir.getParentFile();
                    } else {
                        File newFile = new File(curDir, targetDir);
                        if (newFile.exists()) {
                            curDir = newFile;
                        } else {
                            System.out.println(targetDir + " does not exist!");
                        }
                    }
                    break;

                case "r":
                    while (true) {
                        System.out.println("If you want to add users type 'yes':");
                        String addUser = scanner.nextLine();
                        if (addUser.equals("yes")) {
                            System.out.println("Starting registration...");
                            System.out.println("Enter username:");
                            String regUsername = scanner.nextLine();
                            System.out.println("Enter password:");
                            String regPassword = scanner.nextLine();
                            List<User> usersLiist = (List<User>) objectInputStream.readObject();
                            objectInputStream.close();
                            int i = 0;
                            boolean isEqual = true;
                            while (i < usersLiist.size()) {
                                if (!regUsername.equals(usersLiist.get(i).username)) {
                                    isEqual = false;
                                    i++;
                                    if (i == usersLiist.size() - 1) {
                                        break;
                                    }
                                }else{
                                    isEqual = true;
                                    break;
                                }
                            }
                            if (isEqual) {
                                System.out.println("User like this already exist!");
                                break;
                            }else{
                                User user = new User();
                                user.setUsername(regUsername);
                                user.setPassword(regPassword);
                                list.add(user);
                                objectOutputStream.writeObject(list);
                                objectOutputStream.flush();
                                objectOutputStream.close();
                                System.out.println("User registered successfully!");
                                System.out.println("User " + regUsername + " is logged in!");
                            }
                        }else if (addUser.equals("no")){
                            System.out.println("Type 'list' and see registered users:");
                            String listUsers = scanner.nextLine();
                            if(listUsers.equals("list")) {
                                List<User> usersLiiist = (List<User>) objectInputStream.readObject();
                                objectInputStream.close();
                                System.out.println(usersLiiist + "\n");
                                break;
                            } else {
                                System.out.println("Command not found!\n");
                                break;
                            }
                        }
                    }
                    break;

                case "l":
                    List<User> usersList = (List<User>) objectInputStream.readObject();
                    objectInputStream.close();
                    while (true){
                        System.out.println("Enter username:");
                        String logUsername = scanner.nextLine();
                        System.out.println("Enter password:");
                        String logPassword = scanner.nextLine();
                        int i = 0;
                        boolean isEqual = false;
                        while (i < usersList.size()) {
                            if (logUsername.equals(usersList.get(i).username) && logPassword.equals(usersList.get(i).password)) {
                                isEqual = true;
                                break;
                            }else{
                                i++;
                                if (i == usersList.size() - 1) {
                                    break;
                                }
                            }
                        }
                        if (isEqual) {
                            System.out.println("Hi, " + logUsername + " =) You are logged in!");
                            break;
                        }else{
                            System.out.println("Something went wrong, try again later!");
                        }
                        break;
                    }
                    String logOut = scanner.nextLine();
                    if(logOut.equals("out")){
                        System.out.println("Log out successful!");
                        break;
                    }
                    break;

                default:
                    System.out.println("Command not found!");
                    break;
            }
        }
        System.out.println("Terminal is finished!");
    }

    public static void listDirectories(File file) {
        if (!file.isDirectory()) {
            System.out.println(file.getName() + " is not a directory!");
            return;
        }

        File[] childDirs = file.listFiles();
        for (File childDir : childDirs) {
            System.out.println(childDir.getName());
        }
    }

    public static void printLogo() {
        System.out.println(
                " ██████╗████████╗█████╗ █████╗     ███╗   ███╗█████╗████████╗████████╗  ██╗█████╗     █████████████████████╗███╗   ███ ███╗    ██╗█████╗██╗     \n" +
                "██╔═══██╚══██╔══██╔══██ █╔══██╗    ████╗ ██████╔══██╚══██╔══██╔════██║  ████╔══██╗    ╚══██╔══██╔════██╔══█████╗  ████ ████╗   ████╔══████║     \n" +
                "██║   ██║  ██║  ██████ ██████╔╝    ██╔████╔██ ██████║  ██║  ██║    █████████████║        ██║  █████╗ █████ ╔██╔████╔██ ███╔██╗ ███████████║     \n" +
                "██║   ██║  ██║  ██╔══████╔══██╗    ██║╚██╔╝██ █╔══██║  ██║  ██║    ██╔══████╔══██║       ██║  ██╔══╝ ██╔══████║╚██╔╝██ ███║╚██╗████╔══████║     \n" +
                "╚██████╔╝  ██║  ██║  ████║  ██║    ██║ ╚═╝ ████║  ██║  ██║  ╚████████║  ████║  ██║       ██║  █████████║  ████║ ╚═╝ ██ ███║ ╚██████║  █████████╗\n" +
                " ╚═════╝   ╚═╝  ╚═╝  ╚═╚═╝  ╚═╝    ╚═╝     ╚═╚═╝  ╚═╝  ╚═╝   ╚═════╚═╝  ╚═╚═╝  ╚═╝       ╚═╝  ╚══════╚═╝  ╚═╚═╝     ╚═╚═╚═╝  ╚═══╚═╝  ╚═╚══════╝\n");
    }
}
