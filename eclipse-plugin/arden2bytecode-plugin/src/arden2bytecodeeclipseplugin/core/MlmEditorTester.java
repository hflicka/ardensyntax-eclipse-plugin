package arden2bytecodeeclipseplugin.core;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import arden2bytecodeeclipseplugin.Activator;

public class MlmEditorTester extends PropertyTester {

	private final static String PROPERTY_MLMEDITOR = "isMlmEditor";
	
	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (PROPERTY_MLMEDITOR.equals(property)) {
			if (receiver instanceof ITextEditor) {
				ITextEditor editor = (ITextEditor) receiver;
				IResource res = (IResource) editor.getEditorInput().getAdapter(IResource.class);
				if (res != null) {
					String ext = res.getFileExtension();
					if (ext != null && Activator.MLM_EXTENSION.equalsIgnoreCase(ext)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
