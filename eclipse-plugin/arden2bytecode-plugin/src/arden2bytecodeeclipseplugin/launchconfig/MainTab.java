package arden2bytecodeeclipseplugin.launchconfig;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.xtext.ui.XtextProjectHelper;

public class MainTab extends AbstractLaunchConfigurationTab {
	
	public final static String COMPILER_ARGS = "compilerArguments"; //$NON-NLS-1$
	public final static String COMPILER_ARGS_DEFAULT = "";  //$NON-NLS-1$
	
	public final static String PROJECT = "project"; //$NON-NLS-1$
	public final static String PROJECT_DEFAULT = "";  //$NON-NLS-1$
	
	public final static String MLM = "mlm"; //$NON-NLS-1$
	public final static String MLM_DEFAULT = "";  //$NON-NLS-1$
	
	public final static String LOGO = "displayLogo"; //$NON-NLS-1$
	public final static boolean LOGO_DEFAULT = false;  //$NON-NLS-1$
	
	MyModifyListener listener = new MyModifyListener();
	Text argumentsText = null;
	Text projectText = null;
	Text mlmText = null;
	Button logoCheckbox = null;
	
	private IProject getProject(IProject[] projects, String name) {
		for (IProject p : projects) {
			if (name.equals(p.getName())) {
				return p;
			}
		}
		return null;
	}
	
	private IProject[] filterProjects(IProject[] projects) {
		List<IProject> result = new LinkedList<IProject>();
		for (IProject p : projects) {
			if (XtextProjectHelper.hasNature(p)) {
				result.add(p);
			}
		}
		return result.toArray(new IProject[result.size()]);
	}
	
	private IProject chooseXTextProject() {
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(), labelProvider);
		dialog.setTitle("Select Arden Syntax Project"); 
		dialog.setMessage("Select the Project containing the file you want to run.");
		dialog.setMultipleSelection(false);
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		projects = filterProjects(projects);
		dialog.setElements(projects);
		IProject project = getProject(projects, projectText.getText().trim());
		if (project != null) {
			dialog.setInitialSelections(new Object[] { project });
		}
		if (dialog.open() == Window.OK) {			
			return (IProject) dialog.getFirstResult();
		}		
		return null;		
	}
	
	private class MLMFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IFile) {
				IFile f = (IFile) element;
				String ext = f.getFileExtension();
				if ("mlm".equalsIgnoreCase(ext) && f.exists()) {
					return true;
				}
			} else if (element instanceof IFolder) {
				return true;
			}
			return false;
		}
	}
	
	private IResource chooseMlm() {
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				getShell(), 
				labelProvider, 
				new BaseWorkbenchContentProvider());
		dialog.addFilter(new MLMFilter());
		dialog.setTitle("Select MLM to run");
		dialog.setMessage("Select an Medical Logic Module (MLM) to run:");
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectText.getText().trim()));
		if (dialog.open() == Window.OK) {
			return (IResource) dialog.getFirstResult();
		}
		return null;
	}
	
	@Override
	public void createControl(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		setControl(group);
		group.setText("General Options");
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		group.setLayout(gridLayout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label = new Label(group, SWT.NONE);
		label.setText("Project:");
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		
		projectText = new Text(group, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 14;
		gridData.widthHint = 100;
		projectText.setLayoutData(gridData);
		projectText.addModifyListener(listener);
		
		Button browseButton = new Button(group, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IProject res = chooseXTextProject();
				if (res != null) {
					projectText.setText(res.getName());
				}
			}
		});
		
		Label label2 = new Label(group, SWT.NONE);
		label2.setText("MLM to run:");
		label2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		
		mlmText = new Text(group, SWT.BORDER);
		GridData gridData2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData2.heightHint = 14;
		gridData2.widthHint = 100;
		mlmText.setLayoutData(gridData2);
		mlmText.addModifyListener(listener);
		
		Button browseMlmButton = new Button(group, SWT.NONE);
		browseMlmButton.setText("Browse MLM...");
		browseMlmButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		browseMlmButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IResource res = chooseMlm();
				if (res != null) {
					mlmText.setText(res.getProjectRelativePath().toString());
				}
			}
		});
		
		Label label3 = new Label(group, SWT.NONE);
		label3.setText("Additional Compiler Arguments:");
		label3.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		
		argumentsText = new Text(group, 
				SWT.MULTI | 
				SWT.WRAP | 
				SWT.BORDER | 
				SWT.V_SCROLL);
		GridData gridData3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gridData3.heightHint = 40;
		gridData3.widthHint = 100;
		argumentsText.setLayoutData(gridData3);
		
		argumentsText.addModifyListener(listener);
		
		logoCheckbox = new Button(group, SWT.CHECK);
		logoCheckbox.setText("Display compiler logo");
		logoCheckbox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		logoCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				scheduleUpdateJob();
			}
		});
	}	
	
	class MyModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			scheduleUpdateJob();
		}
		
	}
	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(COMPILER_ARGS, COMPILER_ARGS_DEFAULT);
		configuration.setAttribute(PROJECT, PROJECT_DEFAULT);
		configuration.setAttribute(MLM, MLM_DEFAULT);
		configuration.setAttribute(LOGO, LOGO_DEFAULT);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {		
		try {
			argumentsText.setText(configuration.getAttribute(COMPILER_ARGS, COMPILER_ARGS_DEFAULT));
			projectText.setText(configuration.getAttribute(PROJECT, PROJECT_DEFAULT));
			mlmText.setText(configuration.getAttribute(MLM, MLM_DEFAULT));
			logoCheckbox.setSelection(configuration.getAttribute(LOGO, LOGO_DEFAULT));
		} catch (CoreException e) {
			IStatus status = e.getStatus();
			ResourcesPlugin.getPlugin().getLog().log(status);
		}		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(COMPILER_ARGS, argumentsText.getText());
		configuration.setAttribute(PROJECT, projectText.getText());
		configuration.setAttribute(MLM, mlmText.getText());
		configuration.setAttribute(LOGO, logoCheckbox.getSelection());
	}

	@Override
	public String getName() {
		return "Main";
	}

}
