Eclipse Plugin "arden2bytecodeeclipseplugin"
============================================

Overview
--------

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
* **arden2bytecodeeclipseplugin.core.LaunchableTester.java** -
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
  
Roadmap
-------

By now, the arden2bytecodeeclipseplugin is able to launch 
an MLM out of the Editor or out of a file in a project.
It is also possible to stop a running MLM "by hand" and
see in which line of code the according stackframe is in.

What is missing is the capability to debug a running MLM
with features such as setting breakpoints and stepping 
through the code. I (Hannes Flicka) have not yet been able
to find out how to set the line breakpoints such that the
Debug Target will stop when the line is executed.
Yet, the Bytecode produced by Arden2ByteCode contains line
numbers and symbol information which can be viewed when
halting the JVM in the Eclipse debugger and highlighting 
the according stack frame when the MLM is e.g. waiting for 
user input on a read statement.

Also it is possible to set line breakpoint markers which 
can be seen in the "Breakpoints" view of Eclipse. Yet they
are not displayed in the breakpoint ruler but this is 
generally possible and has been implemented in the 
[mitra 2](http://jpilgrim.github.com/mitra2/) 
xText-based Eclipse plugin by 
[Jens Pilgrim](http://jevopisdeveloperblog.blogspot.de/).

The missing functionality as far as I have understood the
Eclipse debug model, is to pass the breakpoints to the
running Java VM. Still the Eclipse debugging framework is
quite complex and I've seen better documentation than that
available. Therefore I checked out the eclipse.jdt.debug
and eclipse.jdt.core source code in order to understand
how breakpoints are handled in the JDT.

