Arden2ByteCode Compiler Eclipse Plugin
======================================

Overview
--------

This Eclipse plugin bundles the binaries and dependecies of the
Arden2ByteCode compiler as a bundle suited for the Eclipse
platform.  
It is a straightforward adaptation of the original Arden2ByteCode
binary JAR file.

Build Instructions
------------------

The Eclipse project contained in the directory containing this
readme links to the root folder of the [Arden2ByteCode source
code repository](http://github.com/hflicka/arden2bytecode) 
via the **arden2bytecode** variable.  
To build this project, first set the **arden2bytecode** variable
in Window -> Preferences -> General -> Workspace -> Linked Resources
such that it points to the root folder of the Arden2ByteCode 
source code.

Having the Arden2ByteCode source code linked as described above 
and having the JewelCli binary imported as stated below, you 
should be able to build this plugin with the Eclipse PDE.

JewelCli dependency
-------------------

Note that you should place a compiled version of 
[JewelCli (Version 0.6)](http://sourceforge.net/projects/jewelcli/files/jewelcli/0.6/) 
into the directory jewelcli.

After doing that, the following files should be present 
(relative to the directory containing this readme):

* jewelcli/uk/co/flamingpenguin/jewel/cli/Argument.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentCollection.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentCollectionImpl$1.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentCollectionImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentInvocationHandler.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentMethodSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentParser.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentPresenter.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentPresenterImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentSpecificationImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentTyper.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentTyperImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$1.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$1.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$10.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$2.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$3.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$4.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$5.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$6.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$7.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$8.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType$9.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError$ErrorType.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationError.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException$ValidationErrorImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidationException.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidator.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ArgumentValidatorImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/Cli.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/CliFactory.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/CliImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/CliSpecificationImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/CommandLineInterface.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/HelpValidationErrorImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/Messages.properties
* jewelcli/uk/co/flamingpenguin/jewel/cli/Option.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionArgumentsSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionMethodSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionNotPresentException.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionSpecificationImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionsSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionsSpecificationImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/OptionUtils.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ParsedArgumentsBuilder$1.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ParsedArgumentsBuilder$2.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ParsedArgumentsBuilder$ParsingState.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ParsedArgumentsBuilder.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/TypedArguments.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/TypedArgumentsImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/UnexpectedOptionSpecification.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/Unparsed.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/UnparsedSpecificationImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ValidationErrorBuilder.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ValidationErrorBuilderImpl.class
* jewelcli/uk/co/flamingpenguin/jewel/cli/ValueFormatException.class
* jewelcli/uk/co/flamingpenguin/jewel/JewelException.class
* jewelcli/uk/co/flamingpenguin/jewel/JewelRuntimeException.class
