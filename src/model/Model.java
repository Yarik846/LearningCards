package model;

import model.basicClasses.Card;
import model.basicClasses.Dictionary;
import model.basicClasses.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Model {
    /**
     * dividerKey must be used for dived Card's sizes, when write it in a file and for mapping when read it from a file.
     */
    private static final String DIVIDER_KEY = "151-De.V,i,D.eR-546";
    private final ArrayList<User> availableUsersList;
    private final Set<User> loadedUsersHash = new HashSet<>();

    private final String rootPath = new File("").getAbsolutePath();
    private final String allUsersListPath = rootPath + "/data/AllUsersList.txt";
    private final String usersFolderPath = rootPath + "/data/Users";

    public Model() {
        availableUsersList = new ArrayList<>();
        loadUsersList();
    }

    protected ArrayList<User> getAvailableUsersList() {
        return availableUsersList;
    }

    protected void loadUsersList() {
        availableUsersList.clear();
        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new FileReader(allUsersListPath));

            while ((line = reader.readLine()) != null) {
                availableUsersList.add(new User(line));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void createUser(User user) {
        File newUser = new File(usersFolderPath + "/" + user.getName());
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(allUsersListPath, true));
            writer.write(newUser.getName() + "\n");
            writer.close();

            newUser.mkdir();
            availableUsersList.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected User getUser(String userName) {

        for (User user : loadedUsersHash) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }

        User user = loadUser(userName);
        loadedUsersHash.add(user);
        return user;
    }

    private User loadUser(String userName) {
        User user = new User(userName);
        File usersDir = new File(usersFolderPath);
        File dictionariesDir = new File(usersFolderPath + "/" + userName + "/");
        File[] allUsers = usersDir.listFiles();

        if (allUsers != null) {
            for (File file : allUsers) {
                if (file.getName().equals(userName)) {
                    File[] userDictionariesTXT = dictionariesDir.listFiles();

                    if (userDictionariesTXT != null) {
                        for (File dictionaryTXT : userDictionariesTXT) {
                            Dictionary currentDictionary = new Dictionary(dictionaryTXT.getName());
                            readTxtCardsToDic(dictionaryTXT, currentDictionary);
                            user.addDictionary(currentDictionary);
                        }
                    }

                }
            }
        }

        return user;
    }

    private void readTxtCardsToDic(File dictionaryTXT, Dictionary dictionary) {
        BufferedReader reader;
        Card currentCard;
        String frontSize;
        String backSize;

        try {
            reader = new BufferedReader(new FileReader(dictionaryTXT));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(DIVIDER_KEY);
                frontSize = arr[0];
                backSize = arr[1];
                currentCard = new Card(frontSize, backSize);
                dictionary.addCard(currentCard);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean deleteUser(User user) {
        boolean successful = false;
        File userToDelete = new File(usersFolderPath + "/" + user.getName());
        File[] dictionaries = userToDelete.listFiles();

        if (dictionaries != null) {
            if (dictionaries.length != 0) {
                for (File dictionary : dictionaries) {
                    dictionary.delete();
                }
            }

            successful = userToDelete.delete();

            if (successful) {

                availableUsersList.remove(user);

                if (availableUsersList.size() != 0) {
                    BufferedWriter writer;
                    try {
                        writer = new BufferedWriter(new FileWriter(allUsersListPath, false));

                        for (User u : availableUsersList) {
                            writer.write(u.getName() + "\n");
                        }

                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    recreateAvailableUsersListFile();
                }
            }
        }

        return successful;
    }

    protected boolean createDictionary(User user, String dictionaryName) {
        boolean success = false;
        Dictionary dictionary = new Dictionary(dictionaryName + ".txt");
        user.addDictionary(dictionary);

        File newDictionaryTXT = new File(usersFolderPath + "/" + user.getName() + "/" + dictionaryName +
                ".txt");

        try {
            newDictionaryTXT.createNewFile();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    protected boolean deleteDictionary(User user, Dictionary dictionary) {
        File removingDictionary = new File(usersFolderPath + "/" + user.getName() + "/" +
                dictionary.getName());
        ArrayList<Dictionary> dictionaries = user.getDictionaries();

        for (Dictionary dic : dictionaries) {
            if (dic.getName().equals(dictionary.getName())) {
                dictionaries.remove(dic);
                break;
            }
        }

        return removingDictionary.delete();
    }

    private void recreateAvailableUsersListFile() {
        File toDelete = new File(allUsersListPath);
        toDelete.delete();
        File newUserList = new File(allUsersListPath);

        try {
            newUserList.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean createCard(User user, Dictionary dictionary, String frontSide, String backSide) {
        File dicTXT = new File(usersFolderPath + "/" + user.getName() + "/" + dictionary.getName());
        BufferedWriter writer;
        boolean success = false;

        if (!dicTXT.exists()) {
            try {
                dicTXT.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Card card = new Card(frontSide, backSide);
        dictionary.addCard(card);

        try {
            writer = new BufferedWriter(new FileWriter(dicTXT, true));
            writer.write(frontSide + DIVIDER_KEY + backSide + "\n");
            success = true;
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    protected boolean deleteCard(File dicTXT, Card card) {
        boolean success = false;
        ArrayList<String> strCards = new ArrayList<>();
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(dicTXT));
            String line;
            while ((line = reader.readLine()) != null) {

                String[] arr = line.split(DIVIDER_KEY);

                if (!arr[0].equals(card.getFront())) {
                    strCards.add(line);
                }
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter(dicTXT, false));
            for (String str : strCards) {
                writer.write(str + "\n");
            }
            writer.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    protected boolean editCard(File dictionaryTXT, String front, String back) {
        boolean success = false;
        ArrayList<String> cardsStr = new ArrayList<>();
        BufferedReader reader;
        BufferedWriter writer;
        String line;

        try {
            reader = new BufferedReader(new FileReader(dictionaryTXT));

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(DIVIDER_KEY);

                if (arr[0].equals(front)) {
                    line = front + DIVIDER_KEY + back;
                }

                cardsStr.add(line);
            }

            reader.close();

            writer = new BufferedWriter(new FileWriter(dictionaryTXT, false));

            for (String data : cardsStr) {
                writer.write(data + "\n");
            }

            writer.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    protected boolean addToTheFailsList(File failsListTXT, Card card) {
        BufferedWriter writer;
        boolean success = false;

        try {
            writer = new BufferedWriter(new FileWriter(failsListTXT, true));
            writer.write(card.getFront() + DIVIDER_KEY + card.getBack() + "\n");
            writer.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    protected Dictionary loadFailsDictionary(File failsTXT) {
        Dictionary failsDictionary = new Dictionary("Fails Dictionary");

        BufferedReader reader;
        String data;
        Card failCard;

        try {
            reader = new BufferedReader(new FileReader(failsTXT));

            while ((data = reader.readLine()) != null) {
                String[] arr = data.split(DIVIDER_KEY);
                failCard = new Card(arr[0], arr[1]);
                failsDictionary.addCard(failCard);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return failsDictionary;
    }

    protected String deleteFromFailsList(File failsListTXT, Card card) {
        String message = "Operation failed";
        ArrayList<String> data = new ArrayList<>();
        String line;
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(failsListTXT));

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(DIVIDER_KEY);
                if (!arr[0].equals(card.getFront())) {
                    data.add(line);
                }
            }

            reader.close();
            writer = new BufferedWriter(new FileWriter(failsListTXT, false));

            for (String s : data) {
                writer.write(s + "\n");
            }

            writer.close();
            message = "Operation was successfully completed";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    protected String getUsersFolderPath() {
        return usersFolderPath;
    }

    protected boolean setDictionaryName(User user, Dictionary dictionary, String newDictionaryName) {
        boolean success = false;

        Path source = Paths.get(usersFolderPath + "/" + user.getName() + "/" + dictionary.getName());
        try {
            Files.move(source, source.resolveSibling(newDictionaryName));
            dictionary.setName(newDictionaryName);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }
}
