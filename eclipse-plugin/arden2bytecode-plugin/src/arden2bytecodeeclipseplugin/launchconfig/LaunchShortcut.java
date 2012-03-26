package arden2bytecodeeclipseplugin.launchconfig;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class LaunchShortcut implements ILaunchShortcut2 {

	public final static String CONFIG_TYPE = "Arden2ByteCodeEclipsePlugin.launchMLM";
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object o : structuredSelection.toArray()) {
				if (o instanceof IResource) {
					launch((IResource) o, mode);
				}
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		if (editor.isDirty()) {
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
					"MLM Launch", 
					"Save the file first");
		} else {
			IFileEditorInput input = (IFileEditorInput) editor.getEditorInput();
			IFile file = input.getFile();
			if (file.exists() && Arden2ByteCodeLaunchConfiguration.MLM_EXTENSION.
					equalsIgnoreCase(file.getFileExtension())) {
				launch(file, mode);
			}
		}
	}

	public void launch(IResource resource, String mode) {
		if (resource instanceof IFile) {		
			try {
				ILaunchConfiguration config = findExistingConfig(resource);
				if (config == null) {
					config = createMlmLaunchConfiguration(resource);
				}
				if (config != null) {
					config.launch(mode, null);
				}
			} catch (CoreException e) {
				MessageDialog.openError(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"MLM Launch", "Error launching MLM: " + e.getStatus().getMessage());
			}
		}
	}
	
	private ILaunchConfiguration createMlmLaunchConfiguration(IResource resource) throws CoreException {
		ILaunchConfigurationType configType = DebugPlugin.
				getDefault().
				getLaunchManager().
				getLaunchConfigurationType(CONFIG_TYPE);
		
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, 
			DebugPlugin.
			getDefault().getLaunchManager().generateLaunchConfigurationName(resource.getName()));
		new MainTab().setDefaults(wc);
		wc.setAttribute(MainTab.PROJECT, resource.getProject().getName());
		wc.setAttribute(MainTab.MLM, resource.getProjectRelativePath().toString());
		return wc.doSave();
	}

	private ILaunchConfiguration findExistingConfig(IResource resource) throws CoreException {
		String project = resource.getProject().getName();
		String filename = resource.getProjectRelativePath().toString();
		ILaunchConfigurationType configType = DebugPlugin.
				getDefault().
				getLaunchManager().
				getLaunchConfigurationType(CONFIG_TYPE);
		ILaunchConfiguration[] configs = DebugPlugin.
				getDefault().
				getLaunchManager().
				getLaunchConfigurations(configType);
		List<ILaunchConfiguration> candidates = new LinkedList<ILaunchConfiguration>();
		for (ILaunchConfiguration config : configs) {
			if (config.getAttribute(MainTab.PROJECT, MainTab.PROJECT_DEFAULT).equals(project)
					&& config.getAttribute(MainTab.MLM, MainTab.MLM_DEFAULT).equals(filename)) {
				candidates.add(config);
			}
		}
		if (candidates.size() == 1) {
			return candidates.get(0);
		} else if (candidates.size() > 1) {
			return chooseConfig(candidates);
		}
		return null;
	}
	
	private ILaunchConfiguration chooseConfig(
			List<ILaunchConfiguration> candidates) {
	    IDebugModelPresentation labelProvider = DebugUITools.newDebugModelPresentation();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), labelProvider);
		dialog.setTitle("Choose a launch configuration");
		dialog.setMessage("Choose a launch configuration to run for the selected item");
		dialog.setElements(candidates.toArray());
		dialog.setMultipleSelection(false);
		if (dialog.open() == Window.OK) {
			return (ILaunchConfiguration) dialog.getFirstResult();
		}
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return null;
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return null;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Object first = structuredSelection.getFirstElement();
		if (first instanceof IResource) {
			return (IResource) first;
		}
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		IFileEditorInput input = (IFileEditorInput) editorpart.getEditorInput();
		return input.getFile();
	}
}
