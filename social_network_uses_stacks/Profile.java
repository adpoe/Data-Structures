
import java.io.Serializable;

/**
 * **
 * @Author Tony Poerio (adp59@pitt.edu)
 * Client class for:   University of Pitt - CS 445 - Fall 2015
 * Instructor:  Bill Garrison
 *
 * A very basic social media profile
 *
 */

public class Profile implements ProfileInterface, Serializable {
    // Composition with SimpleStack<T> to store the "following" list
    SimpleStack<Profile> following = new SimpleStack<Profile>(25);
    private String profileName = null;
    private String aboutMe = null;

    //////////////////////////
    ////// CONSTRUCTORS //////
    /////////////////////////
    /** Creates an default Profile, instructing the user to add their "name"
     * and their "about me" blurb.
     * . */
    public Profile() {
        this.setName("Add your name here.");
        this.setAbout("Add about me blurb here.");
    } // end default constructor

    /**
     * Creates a profile given input data.
     * @param userName a String for the user's "name"
     * @param userAbout a String for the user's "about me" blurb
     */
    public Profile(String userName, String userAbout) {
        this.setName(userName);
        this.setAbout(userAbout);
    } // end constructor


    ////////////////////////////////////////
    ////// INTERFACE REQUIRED METHODS //////
    ////////////////////////////////////////
    /**
     * Sets the profile's name
     * @param name The new name
     */
    public void setName(String name) {
        this.profileName = name;
    }

    /**
     * Gets the profile's name
     * @return The name
     */
    public String getName() {
        return this.profileName;
    }

    /**
     * Sets the profile's "about me" blurb
     * @param about The new blurb
     */
    public void setAbout(String about) {
        this.aboutMe = about;
    }

    /**
     * Gets the profile's "about me" blurb
     * @return The blurb
     */
    public String getAbout() {
        return this.aboutMe;
    }

    /**
     * Adds another profile to this profile's following stack. The most obvious failure is when this
     * profile already follows the maximum number of other profiles. Although the stack may be
     * capable of holding duplicate items, this method should also return false if this profile is
     * already following other.
     * @param other The profile to follow
     * @return True if successful, false otherwise
     * */
    public boolean follow(ProfileInterface other) {
        boolean success = false;

        // check if user is already being followed
        if (following.contains((Profile) other)) {
            success =  false;
        // if add function returns true, result is true
        } else {
            success = following.add((Profile) other);
        } //Otherwise, success is false

        return success;
    }

    /**
     * Removes the most recently-followed profile from this profile's following stack
     * @return The profile that was unfollowed
     */
    public ProfileInterface unfollow() {
        return this.following.remove();
    }

    /**
     * Returns this profile's most recent follows
     * @param howMany The number of profiles to return
     * @return An array of size howMany, containing the most recently-followed profiles
     */
    public ProfileInterface[] following(int howMany) {
        Object[] recentFollowers = this.following.topItems(howMany);
        Profile[] returnArray = new Profile[howMany];
        for (int i = 0; i < howMany; i++) {
            Profile castObject = (Profile)recentFollowers[i];
            returnArray[i] = castObject;

        }

        // Find what's in the Object array -> WE KNOW that we are getting proper objects
        // Into object array
        // Question:  How to properly cast and return them as a ProfileInterface array?

        /**
        for (int i = 0; i < howMany; i++) {
            System.out.println("******");
            System.out.println("Object array number " + i + " is " + recentFollowers[i]);
            System.out.println("******");
        }
        */

        return returnArray;
    }

    /**
     * Recommend a profile for this profile to follow. For now, this can return any arbitrary
     * profile followed by one of this profile's followed profiles. For example, if this profile is
     * Alex, and Alex follows Bart, and Bart follows Crissy, this method might return Crissy. You
     * may implement more intelligent selection (e.g., ensuring that the selection is not already
     * followed), but it is not required.
     * @return The profile to suggest, or null if no suitable profile is found.
     */
    public ProfileInterface recommend() {

        // Get one of current user's friends
        Profile[] myFriends = (Profile[]) this.following(1);
        Profile firstDegreeFriend = myFriends[0];

        // Get a "friend of a friend"
        Profile[] friendsFriends = (Profile[]) firstDegreeFriend.following(1);
        Profile secondDegreeFriend = friendsFriends[0];

        // Get a "third degree friend"
        // Profile[] thirdDegreeFriends = (Profile[]) secondDegreeFriend.following(1);
        // Profile recommendation = thirdDegreeFriends[0];

        // First element in secondDegreeFriend Array is the recommendation
        return secondDegreeFriend;
    }

    /**
     * Returns a String representation of an the Profile attributes
     * @return a string containing the Name and About
     */
    public String toString() {
        return "\nName: " + this.getName() + "\n" +
               "About: " + this.getAbout();

    }

}


