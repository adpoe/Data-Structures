import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author - Tony (Anthony) Poerio - adp59@pitt.edu
 * University of Pittsburgh - CS445 - Data Structures
 * Fall 2015, Professor William Garrison III
 * Due:  Nov 11, 2015
 *
 * This class is used to assign coupons to users of a theoretical social network such that:
 *     1)  NO two (2) users who are friends may have the SAME coupon
 *     2)  The EXACT number of coupons specifed by the end user MUST be assigned
 *     3)  If the specified coupon assignment is impossible, the program will output a message stating that.
 *
 *
 * How To:
 * --------
 * To run this program, compile the class and type:   "java FriendsCoupon 'input_data_file.txt' num_coupons_to_assign"
                                             i.e. -   "java FriendsCoupon 'medium.txt' 4"
 *  - There are two command line args:
 *           1)  The input data file;
 *           2)  The number of coupons to assign
 *
 *  To run the test suite, type:     "java FriendsCoupon -t"
 *   - The program will output the results of a test suite to the Std Err output stream and then exit.
 *
 * Input Data File - Location:
 * ---------------------------
 * Any input data files (corresponding to the first command line argument) MUST be the SAME FOLDER as the
 * compiled Java .class file being executed.
 *
 *
 * Output Format:
 * ---------------
 * A successful run of the program will output the list coupons and their user assignments.
 * Coupons are given alphabetical letters, such that:  Coupon #1 = A, Coupon #2 = B, etc.
 *
 * The program increments on ASCII values, starting at 64 (one below Capital A).
 * So if it were possible assign 70 coupons, the highest result would be the
 * ASCII character corresponding to 64 + 70.
 *
 * I'm also outputting the final character array itself, just as a confirmation.
 *
 *
 * Notes:
 * -------
 * If desired, a user may uncomment certain statements in the program to watch the recursion as it happens.
 * Notes on these locations are left in the body of the program itself, in case anyone needs to view this.
 *
 */
public class FriendsCoupon {

    private static int numCoupons;
    private static int numUsers;
    private static int[][] friendTable;
    private static boolean isTestSession = false;


    //////////////////
    //// METHODS /////
    //////////////////
    /**
     * Accepts a partial solution, in the form of a character array.
     * Returns a boolean telling the user whether the array passed in
     * represents a full solution.
     * @return  True:   The object passed is a complete, valid solution
     *          False:  If the object passed in is NOT (complete AND valid)
     */
    public static boolean isFullSolution(char[] partial, int numCoupons) {
        int arrLength = partial.length;
        int highestCouponValue = 0;

        // Check if any assignments are null
        for (int i=0; i < arrLength; i++) {
            if (partial[i] == 0) {
                return false;
            }

            // Store the highest coupon value while we traverse the array
            if (partial[i] > highestCouponValue) {
                highestCouponValue = partial[i];
            }
        }

        /* Uncomment this to confirm output values during the recursive call
        * System.out.println("Highest coupon value: " + highestCouponValue);
        * System.out.println("Number of coupons: " + (numCoupons + 64));
        */

        // If highest coupon value does NOT equal numCoupons we need
        // this CANNOT be a full solution
        if (highestCouponValue == numCoupons + 64) {
            return !reject(partial);
        } else if (!isTestSession) {
            System.out.println("Not possible to assign.");
            System.exit(0);
        } else {
            return false;
        }

        // if we reach this far, return true if we DON'T reject
        return !reject(partial);
    }

    /**
     * Accepts a partial solution (character array) and returns TRUE if the
     * partial should be rejected because it can NEVER be extended into
     * a complete solution.
     * @return True:   if the solution SHOULD be rejected;
     *         False:  if the solution is valid.
     */
    public static boolean reject(char[] partial) {
        int arrLength = partial.length;

        // Check if any two users with same coupon are friends
        for (int i=0;i<arrLength;i++) {
            for (int j=0; j<arrLength;j++) {
                // there's a null, we can't reject
                if (partial[i] == 0) {
                    return false;
                }
                // if any element of array matches any other
                if (partial[i] == partial[j]) {
                    // check if those users are friends
                    if (friendTable[i][j] == 1) return true; // reject if so
                } // end if
            } // end inner for
        } // end outer for

        return false;
    }

    /**
     * Accepts a partial solution and returns another partial solution that includes
     * ONE additional step added on.
     *
     * @param partial A character array (char[]) which has at least one value that is null.
     * @return The same character array with one change:  the NEXT available null value
     *         in the array will be changed to 'A'.
     *
     *         If NO values in the array are null, the method returns the EXACT SAME array
     *         which was passed in.
     */
    public static char[] extend(char[] partial) {
        // Initialize the new partial solution
        char[] temp = new char[friendTable.length];
        for (int i=0;i<friendTable.length;i++) {
            if (partial[i] != 0) {
                // copy each each coupon that has been assigned
                temp[i] = partial[i];
            } else {
                // Assign coupon 'A' to the first user without a coupon
                temp[i] = 'A';
                return temp;
            }
        }
        // Nothing users left to assign a first coupon to, if we reach this far
        return temp;

    }

    /**
     * Accepts a partial solution and returns another partial solution in which
     * the MOST RECENT step to be added has been changed to its NEXT option.
     *
     * The LAST character in the array will always be the character incremented.
     *
     * And because we are using character arrays, we will increment using ASCII values,
     * Char's ASCII value + 1.
     *
     * Method uses the parameter CouponNum to enforce the specification that we cannot assign
     * MORE coupons than client has allowed. (The second parameter passed in via command line
     * at program start)
     *
     * @param partial A character array, representing a partial solution
     * @param couponNum The total number of coupons we can assign, specificed by user at runtime
     * @return A character array with the last character (ASCII Value) incremented +1, UNLESS
     *         that incrementation would result in a coupon value higher than client has allowed.
     *         If so -> we return the exact same array which was passed in.
     */
    public static char[] next(char[] partial, int couponNum) {
        int numUsers = friendTable.length;
        char[] temp = new char[numUsers];
        int i = 0;
        // Uncomment these values to check and confirm values being written during recursion
        // System.out.println("Attempt length: " + attempt.length);
        // System.out.println("Partial length: " + partial.length );
        while (i < numUsers) {
            if (i == (numUsers-1) || partial[i+1] == 0) {
                // Ensure that coupon number cannot be higher than specified by user
                if (partial[i] >= (couponNum + 64)) {
                    // We're out of options, so we cannot try anything else
                    // System.out.println();
                    // System.out.println("The assignment specified is not possible.");
                    // System.out.println("Please try again with a different coupon number specified.");
                    // System.exit(0);
                    return null;
                } else {
                    // Increment ASCII value by +1
                    temp[i] = (char)(partial[i] + 1);
                    break;
                }
            } else {
                // This isn't the last queen, so just copy it
                temp[i] = partial[i];
            }
            i++;
        }
        return temp;
    }


    ///////////////////////////////
    /////// INPUT VALIDATION //////
    ///////////////////////////////
    /**
     * Upon startup, we build a 2D array by parsing the first file the user has supplied.
     * This method is to check that the newly built input array has valid attributes.
     * This array will be reference by all other methods, so need to ensure it is build properly and
     * contains valid data.
     * @return True:   if input is valid;
     *         False:  if input is NOT valid.
     */
    public static boolean inputValid(int[][] friendTable) {
        // Check that table is valid, before we do anything else
        boolean valid = false;
        int numCoupons = friendTable.length;

        for (int i=0; i < numCoupons; i++) {
            for (int j=0; j < numCoupons; j++) {
                // Ensure that int[i][j] == int[j][i]
                if  (friendTable[i][j] != friendTable[j][i]) {
                    valid = true;
                } // end if

                // Ensure that int[i][i] == 0
                if (friendTable[i][i] != 0) {
                    System.out.println(friendTable[i][i] + " " + friendTable[i][i]);
                    valid = true;
                } // end if

            } // end for
        } // end for

        return valid;
    }

    //////////////////////////////
    ////// RECURSIVE METHOD //////
    //////////////////////////////
    /**
     * This method chains all of the above helper methods together into a single recursive function
     * which runs until we either:  1)  Find a valid Full Solution, or 2) determine that NO solution is possible.
     *
     * Note:  This method itself does not print negative results (that is, if no solution is found), that is done
     * elsewhere. However, if we do find a valid solution, we print that information within this function.
     * @param partial An initialized character array. It can be in any state, as long as it contains characters
     *                and has an equal length to that of our FriendTable, which is built by parsing the
     *                first user-supplied command line argument. The method will run recursively, calling itself
     *                until we are able to determine a definite outcome.
     */
    public static void solve(char[] partial, int numCoupons) {
        // run methods to solve and print the result

        /* Uncomment this to watch the values change during recursive call
        * for (int i=0; i<partial.length;i++) {
        *    System.out.println("Partial "+i+":" + partial[i]);
        * }
        * System.out.println();
        */

        if (reject(partial)) return;

        // if full solution, print it out
        if (isFullSolution(partial, numCoupons)) {
            System.out.println("Coupon Assignments are:");
            for (int i=0; i < partial.length; i++) {
                System.out.println("\tUser "+(i+1)+": "+partial[i]);
            }
            System.out.println("\nArray itself is: ");
            System.out.println(Arrays.toString(partial));
            System.out.println();
            System.exit(0);
        }

        char[] attempt = extend(partial);

        // Recursion occurs here
        while (attempt != null) {
            solve(attempt, numCoupons);
            attempt = next(attempt, numCoupons);
        }


    }

    //////////////////
    ///// TESTS //////
    //////////////////



    /**
     * This method accepts PARTIAL SOLUTIONS, as a char[] array.
     * Use it to ensure that isFullSolution() correctly ID's full solutions.
     * The method is void, but the test result is printed to Std Err.
     * @param test A partial solution, in the form of character array
     */
    static void testIsFullSolutionUnit(char[] test) {
        if (isFullSolution(test, 4)) {
            System.err.println("\tFULL SOL'N:\t" + Arrays.toString(test));
        } else {
            System.err.println("\tNOT FULL SOL'N:\t" + Arrays.toString(test));
        }

    }

    /**
     * Void method, calls TestIsFullSolutionUnit, to print the result of a series of tests.
     * Tests:  isFullSolution()
     */
    public static void testIsFullSolution() {
        System.err.println("***Testing isFullSolution()***");

        // Full solutions:
        System.err.println("Confirm:  Next two should be FULL SOLUTIONS.");
        testIsFullSolutionUnit(new char[]{'A', 'B', 'B', 'A'});
        System.err.println("\t\tAuthor manually confirmed that this should be a full solution.");
        testIsFullSolutionUnit(new char[]{'B', 'C', 'C', 'B'});
        System.err.println("\t\tAuthor manually confirmed that this should be a full solution." +
                "It is an equivalent combination to first, with different coupon values above.\n");


        // Not full solutions:
        System.err.println("Confirm:   Should be NOT full solutions below here.");
        testIsFullSolutionUnit(new char[]{'A', 'B', 'B', 0});
        System.err.println("\t\tNOT full because we have a null.");
        testIsFullSolutionUnit(new char[]{'A', 'A', 'B', 'A'});
        System.err.println("\t\tNOT full because users 1 and 2 are friends, but have same coupon.");
        testIsFullSolutionUnit(new char[]{'B', 'C', 'C', 'C'});
        System.err.println("\t\tNOT full because users 3 and 4 are friends, but have same coupon.");
        System.err.println("\nNote: 'small.txt' was used as the test data set, with __4-COUPONS__ specified");

    }



    /**
     * This method accepts PARTIAL SOLUTIONS, as a char[] array.
     * Use it to ensure that reject() correctly rejects invalid solutions.
     * The method is void, but the test result is printed to Std Err.
     * @param test A partial solution, in the form of character array
     */
    static void testRejectUnit(char[] test) {
        if (reject(test)) {
            System.err.println("\tREJECTED:\t" + Arrays.toString(test));
        } else {
            System.err.println("\tNOT REJECTED:\t" + Arrays.toString(test));
        }
    }

    /**
     * Void method, calls TestIsRejectUnit, to print the result of a series of tests.
     * Tests:  reject()
     */
    public static void testReject() {
        System.err.println("***Testing reject()***");

        // Should be rejected:
        System.err.println("Confirm:  The following two array should be REJECTED");
        testRejectUnit(new char[]{'A', 'A', 'B', 'A'});
        System.err.println("\t    Here, users 1 and 2 are friends and have the same coupon.");
        testRejectUnit(new char[]{'A', 'B', 'B', 'B'});
        System.err.println("\t    Here, users 3 and 4 are friends, and have the same coupon\n");

        // Should NOT be rejected:
        System.err.println("Confirm:  The following arrays should NOT be REJECTED.");
        testRejectUnit(new char[]{'A', 'B', 'B', 0});
        System.err.println("\t   Should never reject an item with a null. It still needs to be extended.");
        testRejectUnit(new char[]{'A', 'B', 'B', 'A'});
        System.err.println("\t   Should NOT reject this, because author has manually confirmed" +
                "that this a is correct answer for the test input.");
        testRejectUnit(new char[]{'B', 'C', 'C', 'B'});
        System.err.println("\t   Should NOT reject this, it is another permutation of the correct answer.");
    }


    /**
     * This method accepts PARTIAL SOLUTIONS, as a char[] array.
     * Use it to ensure that extend() correctly extends partial solutions.
     * The method is void, but the test result is printed to Std Err.
     * @param test A partial solution, in the form of character array
     */
    static void testExtendUnit(char[] test) {
        System.err.println("\tExtended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
    }

    /**
     * Void method, calls TestExtendUnit, to print the result of a series of tests.
     * Tests:  extend()
     */
    public static void testExtend() {
        System.err.println("***Testing extend()***");
        // Cannot be extended:
        System.err.println("Confirm: Next two (2) CANNOT be extended. Should return __EXACT_SAME__ array");
        testExtendUnit(new char[]{'A', 'A', 'A', 'A',});
        testExtendUnit(new char[]{'A', 'B', 'A', 'B'});
        System.out.println();

        // Can be extended:
        System.err.println("Confirm: Next three (3) CAN be extended. Should " +
                "add an 'A' into the __LAST__ open slot in the array." );
        testExtendUnit(new char[]{'A', 0, 0, 0});
        testExtendUnit(new char[]{'A', 'B', 0, 0});
        testExtendUnit(new char[]{'A', 'B', 'C', 0});
    }


    /**
     *
     */

    /**
     * This method accepts PARTIAL SOLUTIONS, and a NUMBER OF COUPONS to assign.
     * Use it to ensure that next() correctly increments a given partial solution.
     * The method is void, but the test result is printed to Std Err.
     * @param test A partial solution, in the form of character array
     * @param couponNum An int, specifying the exact number of coupons which must be assigned
     */
    static void testNextUnit(char[] test, int couponNum) {
        System.err.println("\tNexted " + Arrays.toString(test) + " to " +
                Arrays.toString(next(test, couponNum)));
    }

    /**
     * Void method, calls TestNextUnit, to print the result of a series of tests.
     * Tests:  extend()
     */
    public static void testNext() {
        System.err.println("***Testing next()***");

        System.err.println("Confirm: Next three (3) CAN be next'd. Should increment " +
                "last non-null item.");
        // Can be next'd:
        testNextUnit(new char[] {'A', 0, 0, 0},      2);
        testNextUnit(new char[] {'A', 'B', 0, 0},    3);
        testNextUnit(new char[]{'A', 'B', 'C', 0}, 4);


        // Cannot be next'd
        System.err.println("Confirm: Next two (2) CANNOT be next'd. Should return null.");
        testNextUnit(new char[]{'A', 'A', 'A', 'A',}, 1);
        testNextUnit(new char[] {'A', 'B', 'A', 'B'},  2);
        System.out.println();


    }



    /////////////////////////////
    ///// MAIN ENTRY POINT //////
    /////////////////////////////
    /**
     * Get arguments from command line, set values, run program accordingly
     * @param args - Either: "-t" to initiate test suite
     *                           Example: java FriendsCoupon -t
     *
     *               Or:   args[0] = data file for input
     *                     args[1] = number of coupons
     *                            Example:  java FriendsCoupon "small.txt" 2
     */
    public static void main(String[] args) {

        // If first argument is "-t", specify we want to test
        if (args.length == 1 && args[0].equals("-t")) {
            isTestSession = true;
            // Parse a matrix from input, store as "FriendTable"
            ParseMatrix pm = new ParseMatrix();
            try {
                friendTable = pm.readFile("small.txt");
            } catch (IOException ioe) {
                 System.out.println("Sorry, couldn't open file. Please ensure it is in the SAME directory a the " +
                        "compiled .class file you are running.");
            }

            // numUsers = number of columns in friendTable
            numUsers = friendTable.length;

            /* Uncomment this to display FriendTable values to console
            * for (int i = 0; i < numUsers; i++) {
            *    for (int j = 0; j < numUsers; j++) {
            *        System.out.println("FriendTable: [" + i + "]" + "[" + j + "]" + ": " + friendTable[i][j]);
            *    }
            * }
            */

            // If input file contains invalid data, exit
            if (inputValid(friendTable)) {
                System.out.println("The input file contained invalid data.");
                System.exit(0);
            }


            System.err.println("BEGIN UNIT TESTS\n---------------------------------\n");
            testIsFullSolution();

            System.err.println("\n---------------------------------\n");
            testReject();
            System.err.println("\n---------------------------------\n");
            testExtend();

            System.err.println("\n---------------------------------\n");
            testNext();
            System.err.println("\n---------------------------------");
            System.err.println("END UNIT TESTS");
            System.exit(0);
        }

        // If "-t" NOT specified as first argument, we are running the program normally
        if (!args[0].equals("-t")) {
            // Get args from the command line, to run in this program
            // input format:  "/java FriendsCoupon table_file.txt number_of_coupons"
            String inputFile = args[0];
            int numCoupons = Integer.parseInt(args[1]);

            // Parse a matrix from input, store as "FriendTable"
            ParseMatrix pm = new ParseMatrix();
            try {
                friendTable = pm.readFile(inputFile);
            } catch (IOException ioe) {
                System.out.println("Sorry, couldn't open file.");
            }

            // numUsers = number of columns in friendTable
            numUsers = friendTable.length;

            /* Uncomment this to display contents of FriendTable to console
            * for (int i = 0; i < numUsers; i++) {
            *     for (int j = 0; j < numUsers; j++) {
            *        System.out.println("FriendTable: [" + i + "]" + "[" + j + "]" + ": " + friendTable[i][j]);
            *    }
            * }
            */


            // If input file contains invalid data, exit
            if (inputValid(friendTable)) {
                System.out.println("The input file contained invalid data.");
                System.exit(0);
            }

            char[] coupAssign = new char[numUsers];

            // Call the solve method on the empty coupAssign array
            solve(coupAssign, numCoupons);

            // If we get here, we couldn't find a match
            System.out.println("Not possible to perform the assignment specified.");
        }
    }



    ////////////////////////////////////////////////////
    //// PARSER - USE TO READ INPUT FROM DATA FILE /////
    ////////////////////////////////////////////////////
    /**
     * Class used to parse the input data file, in the form of an adjacency matrix.
     */
    private static class ParseMatrix {

        // Setup variables needed to parse the parse info from the data file
        private int[][] myMatrix;
        private int size = -1;

        /**
         * Read data from an input file in the form of an adjacency matrix.
         *
         * Pre-condition:  It is expected that input file contains data in the form of an adjacency matrix,
         * with space between each item separated by a space. It is also expected that line endings (\n) indicate the
         * end of a row.
         *
         * Post-condition: This program will parse the input file, and if valid:  build an adjacency matrix in the form
         * of a 2D Java array, which can be used by this program.
         *
         * @param filename The input file to read from, must be in same directory as the compiled Java .class file
         * @return Returns a 2D - array (matrix) in the form:  " int[][] "
         * @throws IOException if file contains data which cannot be read: I.e., it is not in the form of an adjacency
         * matrix, satisfying the pre and post conditions outlined above.
         */
        public int[][] readFile(String filename) throws IOException {
            BufferedReader buffer = new BufferedReader(new FileReader(filename));

            // setup variables for our readFile loop
            String line;
            int row = 0;

            // Gather data from file until we reach end (null)
            while ((line = buffer.readLine()) != null) {


                // split values on each line when any whitespace is encountered
                // store everything on the line in an array
                String[] vals = line.trim().split("\\s+");


                // Create an adjacency matrix with dimensions equal to number of items in first row
                // can do this because an adjacency matrix must be symmetric. (# Rows) = (# Columns)
                if (myMatrix == null) {
                    size = vals.length;
                    myMatrix = new int[size][size];
                } // end if


                // Take the value for each item in a given row,
                // and put it into our adjacency matrix array
                for (int item = 0; item < size; item++) {
                    myMatrix[row][item] = Integer.parseInt(vals[item]);
                } // end for

                // increment the row, and repeat the loop until we reach end of file
                row++;


            } // end while-loop

            // return the matrix we've just built
            return myMatrix;


        } // end _readFile function

    } // end inner ParseMatrix class

} // end file
