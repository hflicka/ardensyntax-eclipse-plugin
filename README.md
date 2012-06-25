# Arden Syntax Eclipse Editor

## Build Instructions

To build this, import all projects into an Eclipse version with 
xText Plugin, then generate the language artifacts of arden.xtext. 

For the arden2bytecode-compiler plugin, you need to place a 
compiled version of JewelCli 0.6 into the root directory of the 
project.  
Details are mentioned in:  
eclipse-plugin/arden2bytecode-compiler/README.md

Finally build the plugins, the feature and the update site.