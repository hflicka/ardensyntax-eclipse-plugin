package arden2bytecodeeclipseplugin.launchconfig;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IFileEditorInput;

public class LaunchableTester extends PropertyTester {

	private final static String PROPERTY_LAUNCHABLEMLM = "isLaunchableMlm";
	
	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (PROPERTY_LAUNCHABLEMLM.equals(property)) {
			if (receiver instanceof IFile) {
				IFile f = (IFile) receiver;
				if (f.exists() && Arden2ByteCodeLaunchConfiguration.MLM_EXTENSION.
						equalsIgnoreCase(f.getFileExtension())) {
					return true;
				}
			} else if (receiver instanceof IFileEditorInput) {
				IFileEditorInput input = (IFileEditorInput) receiver;
				IFile f = input.getFile();
				if (Arden2ByteCodeLaunchConfiguration.MLM_EXTENSION.
						equalsIgnoreCase(f.getFileExtension())) {
					return true;
				}
			}
		}
		return false;
	}

}