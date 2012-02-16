
package arden.xtext;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ArdenSyntaxStandaloneSetup extends ArdenSyntaxStandaloneSetupGenerated{

	public static void doSetup() {
		new ArdenSyntaxStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

