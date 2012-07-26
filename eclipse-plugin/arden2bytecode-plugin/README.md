# Eclipse Plugin "arden2bytecodeeclipseplugin"

### Overview

This Plugin contains classes that implement starting MLMs 
(Medical Logic Modules) via the Run/Debug buttons of Eclipse.

In order to make this feature work, the following classes are
supplied:

* **arden2bytecodeeclipseplugin.launchconfig.MainTab** - 
  This class defines the GUI of the only tab that is visible
  when editing settings of a MLM run configuration in the 
  "Run Configurations" dialog.
* **arden2bytecodeeclipseplugin.launchconfig.Arden2ByteCodeLaunchConfigurationTabGroup** - 
  This class defines a tab group for Arden2ByteCode launch 
  configurations containing the above MainTab as the only 
  tab.
* **arden2bytecodeeclipseplugin.launchconfig.Arden2ByteCodeLaunchConfiguration** -
  This class provides the starting mechanism for a MLM.
* **arden2bytecodeeclipseplugin.launchconfig.LaunchShortcut** - 
  This class creates or finds a launch configuration 
  for a MLM when the Run command/button is started.  
  It also provides a selection mechanism if multiple MLMs
  are found.
* **arden2bytecodeeclipseplugin.launchconfig.LaunchableTester.java** -
  This class tests if a given file or an editor content is
  a launchable MLM. The decision is made according to the
  file extension.
* **arden2bytecodeeclipseplugin.debug.SourceLocator** - 
  This class locates the MLM source code for a stack
  frame when debugging the execution of an MLM.

There are also some files which try to implement the setting
of breakpoints but this functionality is not fully 
implemented yet. Details on the status and goal of this
feature are given below.
  
## Roadmap

TODO: write this chapter