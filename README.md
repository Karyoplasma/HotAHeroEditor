# HotAHeroEditor #

A GUI-driven editor for heroes' traits, specialty and starting army.
Usable but feature-incomplete. It will be receiving updates based on feedback.

## Features ##

+ **GUI-driven** edits
	+ Does not require you to know how to hex-edit
	+ Can change secondary skills, skill levels, specialty, starting spell and army
		+ *Note to specialty editing:* The ingame icon and description will not change, but the effect will be as you set it in the editor. This information is not part of the executable. At a later point, I will include a tutorial on how to change that as well.
	+ Select a hero from the box, make some changes and press **Change** to stage the change for future writing
	+ Click **Unlock** and then **Write** to apply your changes to the game files
		+ Applying an empty list of changes will ask if you wish to reset your executable to the original values
	+ Click **Discard All** to discard all changes you have staged so far (in case you want to start anew)
+ **Exporting and loading**
	+ You can export the changes you staged and load them again after an update
	+ The program will check if the change you made in the past can be applied to the new version (or vice versa)
	+ Exports will be in the "mods" folder, named after a timestamped scheme. You are free to change the name to something more memorable.
+ **Shortcuts to important folders**:
	+ Use the Shortcuts menu to open your current game or mod directory
	+ **Planned:** Use the Tool menu to open other modding tools like MMArchive
+ Supports the **Complete Edition of HoMM 3** as well as **HotA**
	+ Written for 1.7.0.0, but older HotA versions should be working too, but no guarantees because I don't have an old version to test
	+ Future HotA updates should be supported without an update as long as the HotA.dat file structure does not change
+ **Automatic Backups**
	+ Creates backups before attempting to modify the game files
		+ *Note:* The backup folder is never cleared, so it's up to you to clean it out from time to time

### Still a beta release ###

Please report any problems you encounter by opening a new issue on here on github and attaching the HotAHeroEditor.log file from the programs directory.