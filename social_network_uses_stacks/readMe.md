#Overview
- This is the submission of *CS445-Project01* for **Tony Poerio (adp59)**. A very simple Social Network profile system, implementing using a Stack Interface.
- All classes are currently compiling and functioning successfully, on my tests.
### Compilation Instructions
- The CLIENT class is throwing me 1 unchecked Cast warning, which stems from the de-serialization function. Serialization works, but please compile CLIENT using:  *"javac -Xlint:unchecked Client.java"*, to avoid any error messages. (Also to note: On my system, compiling the CLIENT class will compile all others as well, since they are all USED by the Client.)
### Usage Instructions
- After compilation, please run *"java Client"*, and you will be presented with a selection prompt to begin adding and editing Profiles. =)
### Saving/Restoring
- Saving and restoring via Serialization is functional according to my own tests. 
- Please note that the Client class will ALWAYS attempt to restore previous data, even on your first run through. 
- The Client class' no-args constructor looks for a file called *"defaultClientSaveFile.ser"* in the current directory, and if it doesn't exist, it will output an error upon first initialization. 
    * This does NOT mean the Client cannot be used. It just means that no previous data file was found. Please proceed with testing, and when finshed quit with **option number 8**. Your data will then be saved in the default location, and re-loaded upon your next initialization via the Client class's no-args constructor. 


Thanks for your time.
t