

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @Author Tony Poerio (adp59@pitt.edu)
 * Client class for:   University of Pitt - CS 445 - Fall 2015
 * Instructor:  Bill Garrison
 *
 * This class can be used to interact with **Master List** of Profile objects, stored in a Stack.
 * It allows the user to List and Display objects, Add and Edit objects,
 * Follow and Unfollow Objects, Suggest a follower, and Quit/Save changes.
 *
 * The class will ALWAYS attempt to restore previous information from
 * a file. A default file storage file is specified for the current directory,
 * but the user may also enter specify his/her own storage file on initialization (by passing a file path
 * to the constructor) and on save (by passing a file path to the #quit function).
 *
 */


public class Client  {
    private SimpleStack<Profile> allProfiles = new SimpleStack<Profile>();
    private String filename = "defaultClientSaveFile.ser";


    public static void main(String[] args) {
        boolean quit = false;
        Client myClient = new Client();
        System.out.println("Welcome to CS445 Assignment 1.");
        System.out.println("\tThrough this interface, you may edit the master list of Profiles for our" +
                "Simple Social network.");

        while (!quit) {

            boolean hasInput = false;
            int userSelection = 0;
            Scanner kb = new Scanner(System.in);
            System.out.println("MAIN MENU:  Please enter a number from the list below to perform" +
                    " a corresponding action.");
            System.out.println("\t1.  LIST all current Profiles");
            System.out.println("\t2.  CREATE a new Profile");
            System.out.println("\t3.  SHOW an existing Profile");
            System.out.println("\t4.  EDIT an existing Profile");
            System.out.println("\t5.  ADD A FOLLOWER to an existing Profile's list of followers");
            System.out.println("\t6.  REMOVE A FOLLOWER from an existing Profile's list of followers");
            System.out.println("\t7.  FIND A SUGGESTED FOLLOWER for an existing Profile");
            System.out.println("\t8.  SAVE CHANGES and QUIT");
            System.out.println("\t9.  QUIT (and don't save).");
            System.out.print("My Selection -> ");

            while (!hasInput)
                try {
                    userSelection = kb.nextInt();
                    hasInput = true;
                } catch (InputMismatchException ime) {
                    System.out.println("Please enter an Integer.\n\n");
                    hasInput = true;
                } catch (Exception e) {
                    System.out.println("There was an error, please enter an integer.\n\n");
                    hasInput = true;
                } // end try

                switch (userSelection) {
                    case 1:
                        // Call List Profiles
                        System.out.println(myClient.listProfiles());
                        break;
                    case 2:
                        // Call Create Profile
                        myClient.createProfile();
                        break;
                    case 3:
                        System.out.println("List of current profiles is:  ");
                        System.out.println(myClient.listProfiles());
                        System.out.println("\tPlease enter the ID NUMBER of the Profile you would like to SHOW. ");
                        Scanner getID = new Scanner(System.in);
                        int selectedID = 0;

                        try {
                            selectedID = getID.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                        } // end try

                        // Call Show
                        System.out.println(myClient.showProfile(selectedID));
                        break;
                    case 4:
                        System.out.println("List of current profiles is:  ");
                        System.out.println(myClient.listProfiles());
                        System.out.println("\tPlease enter the ID NUMBER of the Profile you would like to EDIT. ");
                        Scanner getEdit = new Scanner(System.in);
                        int selectedEdit = 0;
                        try {
                            selectedEdit = getEdit.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                        } // end try

                        // Call Edit Profile
                        myClient.editProfile(selectedEdit);
                        break;
                    case 5:
                        System.out.println("List of current profiles is:  ");
                        System.out.println(myClient.listProfiles());
                        System.out.println("\tPlease enter TWO ID NUMBERS.");
                        System.out.print("\t\t1st - The Profile ID# _to which_ a FOLLOWER should be ADDED:  ");
                        Scanner getBaseProfile = new Scanner(System.in);
                        int selectedBase = 0;
                        try {
                            selectedBase = getBaseProfile.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                            break;
                        } // end try

                        System.out.print("\t\t2nd - The Profile ID# of the FOLLOWER to add:  ");
                        Scanner getFollower = new Scanner(System.in);
                        int selectedFollower = 0;
                        try {
                            selectedFollower = getFollower.nextInt();
                            myClient.follow(selectedBase, selectedFollower);
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                            break;
                        } // end try

                        // Call Follow Profile
                        myClient.follow(selectedBase,selectedFollower);
                        break;
                    case 6:
                        System.out.println("List of current profiles is:  ");
                        System.out.println(myClient.listProfiles());
                        System.out.println("\tPlease enter the Profile ID# on which to call UNFOLLOW.");
                        System.out.print("\t\tNOTE: The MOST RECENTLY ADDED follower will be removed from " +
                                "this Profile's 'followers' stack. -> ");
                        Scanner getUnfollow = new Scanner(System.in);
                        int selectedUnfollow = 0;
                        try {
                            selectedUnfollow = getUnfollow.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                            break;
                        } // end try

                        // Call Unfollow
                        myClient.unfollow(selectedUnfollow);
                        break;
                    case 7:
                        System.out.println("List of current profiles is:  ");
                        System.out.println(myClient.listProfiles());
                        System.out.println("\tPlease enter the Profile ID# for which to SUGGEST a follower.");
                        Scanner getSuggestion = new Scanner(System.in);
                        int selectedSuggestion = 0;
                        try {
                            selectedUnfollow = getSuggestion.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Returning to MAIN MENU.");
                            break;
                        } // end try

                        // Call Suggest a Follower
                        myClient.suggestFollow(selectedSuggestion);
                        break;
                    case 8:
                        myClient.quit();
                        quit = true;
                        break;
                    case 9:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Please enter a number from 1 - 9.\n\n");
                        break;
                } // end switch

        } // end while

    }

    /**
     * Constructor override for when no filename is specified for previous data.
     * Attempt to restore data from the default storage file.
     * Also displays a confirmation message for the user.
     */
    public Client() {
        // If no filename is passed, use the default.
        // restore(filename);
        restore(filename);
        System.out.println("***New instance of Client created successfully***");
    }

    /**
     * Constructor for when a previous filename IS specified
     * Attempt to restore data from the specified file.
     * Also displays a confirmation message for the user.
     * @param newFileName
     */
    public Client(String newFileName) {
        // If a fileName is specified, update it and restore
        filename = newFileName;
        restore(filename);
        System.out.println("***New instance of Client created successfully***");
    }

    /**
     * List all profiles that currently exist.
     * Show names, must be a list form
     * @return a String containing a numbered list of all current profiles
     */
    public String listProfiles() {
        // Get array of profiles from topItems Stack method
        int numProfiles = allProfiles.size();
        Object[] profileList = new Object[numProfiles];
        profileList = allProfiles.topItems(numProfiles);
        // Use for-loop to concatenate a return string with information on each profile
        String returnData = "";
        for (int i=0; i<numProfiles; i++) {
            returnData = returnData +("ID#"+(numProfiles - (i+1)) +". "+profileList[i]+"\n\n");
        }
        return returnData;
    }

    /**
     * Add a profile to the allProfiles stack
     * Accept an existing profile, and add it to the Client class's profile stack
     * @param newProfile - an existing Profile Object
     * @return true, if profile creation was successful. False if not.
     */
    public boolean createProfile(Profile newProfile) {
        // Add a profile using the SimpleStack add function
        boolean result = allProfiles.add(newProfile);
        return result;
    }

    /**
     * Add a profile to the allProfiles stack
     * Create a new profile, and ask user to set its name and “about me” blurb.
     * @return true, if profile creation was successful. False if not.
     */
    public boolean createProfile() {
        // Add a profile using the SimpleStack add function
        boolean result = false;
        String userName = "";
        String userBio = "";

        // Get UserName
        Scanner getName = new Scanner(System.in);
        try {
            System.out.print("Enter User's Name:  ");
            userName = getName.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Input invalid. Please try calling this method again.");
        } catch (Exception e) {
            System.out.println("There was an error. Please try calling this method again.");
        }

        // Get About Me
        Scanner getBio = new Scanner(System.in);
        try {
            System.out.print("Enter User's 'About Me':  ");
            userBio = getBio.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Input invalid. Please try calling this method again.");
        } catch (Exception e) {
            System.out.println("There was an error. Please try calling this method again.");
        }

        result = allProfiles.add(new Profile(userName, userBio));
        return result;
    }

    /**
     * Choose a profile from the list, and show its name, “about me” blurb,
     and recently-followed profiles.
     * @param profileListNumber the desired profile number, obtained from calling the
     *                          #listProfiles method on your instance
     * @return a String containing the profile's name, about me, and recent followers
     */
    public String showProfile(int profileListNumber) {
        // Setup variables
        String profileData = "";
        Profile targetProfile = null;

        // Get profile object
        targetProfile = getProfile(profileListNumber);

        // Create return String
        profileData = profileData + targetProfile.toString();
        profileData = profileData + "\nUser's 10 - Most-recently followed profiles: \n";
        Object[] recentlyFollowed = targetProfile.following(10);
        for (int i = 0; i < 10; i++) {
            profileData = profileData + (Profile)recentlyFollowed[i] + "\n";
        }
        return profileData;
    }


    /**
     *
     * @param profileListNumber Choose a profile from the list, and edit its name and “about me”
     *  blurb.
     * @return a boolean identifying whether the operation was successful
     */
    public boolean editProfile(int profileListNumber) {
        // Setup variables
        boolean success = true;
        Profile targetProfile = null;

        // Get profile object
        targetProfile = getProfile(profileListNumber);

        // Get user input, add to target profile, save.
        // Get New UserName
        String userName = "";
        Scanner getName = new Scanner(System.in);
        try {
            System.out.print("Enter User's Name:  ");
            userName = getName.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Input invalid. Please try calling this method again.");
            success = false;
        } catch (Exception e) {
            System.out.println("There was an error. Please try calling this method again.");
            success = false;
        }

        // Get New "About Me"
        String userBio = "";
        Scanner getBio = new Scanner(System.in);
        try {
            System.out.print("Enter User's 'About Me':  ");
            userBio = getBio.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Input invalid. Please try calling this method again.");
            success = false;
        } catch (Exception e) {
            System.out.println("There was an error. Please try calling this method again.");
            success = false;
        }

        // Save data to the profile
        targetProfile.setName(userName);
        targetProfile.setAbout(userBio);

        return success;
    }

    /**
     * Choose two profiles from the list, and set the first to be following the
     * second.
     * @param profNumOne The ID number of the FIRST profile, obtained from the #listProfiles method
     * @param profNumTwo The ID number of the SECOND profile, obtained from the #listProfiles method
     * @return boolean detailing whether the operation was successful
     */
    public boolean follow(int profNumOne, int profNumTwo) {
        boolean success = false;

        // Get Profile Objects Selected by User
        Profile targetProfile = getProfile(profNumOne);
        Profile profileToFollow = getProfile(profNumTwo);
        // Call the following method with our Profile Objects.
        // If successful, return value is TRUE
        if (targetProfile.follow(profileToFollow)) {
            success = true;
        }

        return success;
    }

    /**
     * Choose a profile from the list, and unfollow its most recently-followed
     * profile
     * @param profNum the ID number of the profile on which to call the unfollow method
     * @return the unfollowed profile.
     */
    public Profile unfollow(int profNum) {
        Profile targetProfile = null;
        // Get a Profile Object and call #unfollow on it
        targetProfile = getProfile(profNum);
        Profile unfollowedProfile = (Profile)targetProfile.unfollow();
        return unfollowedProfile;
    }

    /**
     * Choose a profile from the list, and request a suggestion for a
     * new profile to follow (use the suggest method from Profile). If a profile is returned,
     * choose whether or not to follow this profile.
     * @param listNumber The ID number of a profile FOR which to receive a suggested follower,
     *                   obtained from the #listProfiles method
     */
    public void suggestFollow(int listNumber) {
        Profile suggestedFollow = null;
        // Get selected Profile Object
        Profile targetUser = getProfile(listNumber);
        // Call Profile class's #recommend method on it
        suggestedFollow = (Profile)targetUser.recommend();
        // Return the Suggested follower
        if (suggestedFollow != null) {
            System.out.println("Should '"+targetUser.getName()+"' follow '"+suggestedFollow.getName()+"' ?");
            System.out.println("Enter Y for 'YES'.");
            System.out.print("Y/N: ");

            // Get User Decision
            Scanner kb = new Scanner(System.in);
            String decision = "N";
            try {
                decision = kb.next();
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input, user NOT followed.");
            } catch (Exception e) {
                System.out.println("Invalid input, user NOT followed.");
            }

            if (decision.equalsIgnoreCase("Y")) {
                targetUser.follow(suggestedFollow);
                System.out.println(suggestedFollow.getName() + " has been successfully followed by "+
                    targetUser.getName()+".");
            } else {
                System.out.println("Suggested user NOT followed");
            }
        }

    }

    /**
     * Attempt to save the list of profiles to a file, then quit.
     * This method will be called if NO FILE is specified.
     * Save will be "defaultClientSaveFile.ser" in the current directory
     */
    public void quit() {
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            saveStream.writeObject(allProfiles);
            System.out.println("Data saved successfully. Goodbye.");
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
        }
    }
    /**
     * Attempt to save the list of profiles to a file, then quit
     * This method will be called if a user-selected file *IS* specified
     */
    public void quit(String filename) {
        try {
            ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(filename));
            saveStream.writeObject(allProfiles);
            System.out.println("Data saved successfully. Goodbye.");
        }
        catch(IOException e) {
            System.err.println("Something went wrong saving to " + filename);
            e.printStackTrace();
        }
    }

    ////////////////////////////////////
    /////     UTILITY METHODS      /////
    ///////////////////////////////////

    /**
     * After listing a profile, enter its associated number into getProfile,
     * and the Profile object it relates to will be returned
     * @param profileListNumber an Integer obtained form using the @listProfiles() method
     * @return A Profile Object, the full profile of the targetUser
     */
    public Profile getProfile(int profileListNumber) {
        Profile targetProfile = null;
        int stackSize = (allProfiles.size());
        // StackSize - 1 because top is always null when we call it... unless --> IS FULL!
        // Need if-else for when isFull.
        int stackNumber = stackSize - profileListNumber; // -1, because we start counting at 0
        Object[] viewProfiles = allProfiles.topItems(stackNumber);
        int lastProfileInArray = viewProfiles.length;
        int arrayIndex = (lastProfileInArray-1)*-1;
        targetProfile = (Profile)viewProfiles[lastProfileInArray -1]; // -1 again, because we start counting at 0
        //(lastProfileInArray-1) *-1
        return targetProfile;
    }

    ////////////////////////////////////
    ///// SAVE AND RESTORE METHODS /////
    ///////////////////////////////////

    /**
     * Attempts to restore from a previous save
     * @param filename The filename of the save
     * @return true on success, false on failure
     */
    boolean restore(String filename) {
        try {
            ObjectInputStream restoreStream = new ObjectInputStream(new FileInputStream(filename));
            allProfiles = (SimpleStack<Profile>) restoreStream.readObject(); // Unchecked cast
            System.out.println("Usage Data from a previous session has been successfully restored.");
        }
        catch(FileNotFoundException e) {
            System.err.println("Attempted to restore previous entries, but the source data file was not found.");
            System.err.println("Error is: ");
            System.err.println("\t filename: "+System.getProperty("user.dir")+"/"+filename+" does not exist.");
            return false;
        }
        catch(IOException e) {
            System.err.println("Error resuming from " + filename);
            return false;
        }
        catch(ClassNotFoundException e) {
            System.err.println("Error resuming from " + filename);
            return false;
        }
        return true;
    }

}
